package es.rchavarria.raccount.bussines;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import es.rchavarria.raccount.bussines.util.ConceptUtils;
import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.dao.AccountDAO;
import es.rchavarria.raccount.db.dao.DAOException;
import es.rchavarria.raccount.db.dao.MovementDAO;
import es.rchavarria.raccount.model.Account;
import es.rchavarria.raccount.model.Concept;
import es.rchavarria.raccount.model.DoubleMovement;
import es.rchavarria.raccount.model.Movement;

public class MovementBussiness {

	private Session session;

	public MovementBussiness(Session session){
		this.session = session;
	}
	
	public void insertDoubleMovement(DoubleMovement dblMovement) throws BussinessException{
		try {
			insertMovement(createMovementFrom(dblMovement));
			insertMovement(createMovementTo(dblMovement));
			if(neededAThirdMovementInATransfer(dblMovement)){
			    insertMovement(createThirdMovementInATransfer(dblMovement));
			}
			
			//fin de la transaccion
			session.commit();
			
		} catch (SQLException e) {
			try { session.rollback(); } catch (Throwable t) { }
			String msg = "Error creating database session";
			throw new BussinessException(msg, e);
			
		} catch (DAOException e) {
			try { session.rollback(); } catch (Throwable t) { }
			String msg = "Error accesing database";
			throw new BussinessException(msg, e);
		}
	}

    private Movement createMovementFrom(DoubleMovement dblMovement) {
	    Movement move = new Movement();
	    move.setDescription(dblMovement.getDescription());
	    move.setMovementDate(dblMovement.getMovementDate());
	    move.setConcept(dblMovement.getConcept());
	    move.setAmount(dblMovement.getAmount());
	    move.setAccount(dblMovement.getAccount());
	    return move;
    }
    
    /**
     * Crea un movimiento con la cuenta 'a'. Si no hay cuenta 'a', o no es una transferencia, dicha
     * cuenta será siempre la '1', la cuenta global.
     * 
     * @param dblMovement
     * @return
     * @throws DAOException
     */
    private Movement createMovementTo(DoubleMovement dblMovement) throws DAOException {
        Movement move = new Movement();
        move.setDescription(dblMovement.getDescription());
        move.setMovementDate(dblMovement.getMovementDate());
        move.setConcept(dblMovement.getConcept());
        move.setAmount(dblMovement.getAmount());
        
        //calcular la cuenta dependiendo de si el concepto es transferencia o no
        Account a = null;
        if(ConceptUtils.isTransfer(dblMovement.getConcept())){
            a = dblMovement.getAccountTo();
            move.setAmount(dblMovement.getAmount() * -1);//invertir el movimiento
        } else {
            //para no transferencias, el movimiento es contra la cuenta 1
            AccountDAO accountDAO = new AccountDAO(session);
            a = accountDAO.find(1L);
        }
        move.setAccount(a);
        
        return move;
    }
    
    /**
     * es necesario un tercer movimiento si estamos en una transferencia y alguna de las cuentas es
     * NO contable.
     * No sera necesario si ninguna o ambas cuentas es NO contable
     * 
     * @param dblMovement
     * @return
     *      true si es necesario un tercer movimiento en una tranferencia
     */
    private boolean neededAThirdMovementInATransfer(DoubleMovement dblMovement){
        boolean isTransfer = ConceptUtils.isTransfer(dblMovement.getConcept());
        boolean bothAccountsAreAccountable = dblMovement.getAccount().isAccountable() && dblMovement.getAccountTo().isAccountable();
        boolean bothAccountsAreNotAccountable = !dblMovement.getAccount().isAccountable() && !dblMovement.getAccountTo().isAccountable();
        
        return isTransfer 
                && !bothAccountsAreAccountable
                && !bothAccountsAreNotAccountable;
    }
    
    /**
     * A la hora de hacer un traspaso:
     *  1. si la cuenta 'a' NO es contable, añado un movimiento a la cuenta '1', global, para contabilizarlo
     *     como un gasto
     *  2. si la cuenta 'desde' NO es contable, añado un movimiento a la cuenta '1', para contabilizarlo
     *     como un ingreso
     *     
     * @param dblMovement
     *      movimiento sobre el que se calcula el tercer movimiento en caso de transferencia
     *      
     * @return
     *      tercer movimiento, siempre sobre la cuenta '1', global
     *      
     * @throws DAOException
     */
    private Movement createThirdMovementInATransfer(DoubleMovement dblMovement) throws DAOException {
        Movement move = new Movement();
        move.setDescription(dblMovement.getDescription());
        move.setMovementDate(dblMovement.getMovementDate());
        move.setConcept(dblMovement.getConcept());
        move.setAccount(new AccountDAO(session).find(1L));

        if(! dblMovement.getAccount().isAccountable()){
            move.setAmount(dblMovement.getAmount() * -1);
            
        } else if(! dblMovement.getAccountTo().isAccountable()) {
            move.setAmount(dblMovement.getAmount());
            
        } else {
            throw new IllegalStateException("At least one account should be Not Accountable");
        }
        
        return move;
    }

    /**
	 * Inserta un movimiento aplicando l�gica de negocio
	 * @param movement
	 * @throws DAOException
	 */
	private void insertMovement(Movement movement) throws DAOException {
		AccountDAO accountDAO = new AccountDAO(session);
		Account account = accountDAO.find(movement.getAccount().getIdAccount());
		double finalBalance = account.getBalance() + movement.getAmount(); 
		account.setBalance(finalBalance);
		accountDAO.update(account);
		
		movement.setFinalBalance(finalBalance);
		MovementDAO movementDAO = new MovementDAO(session);
		movementDAO.insert(movement);
	}
	
	public List<Movement> listLastNMovements(Account account, int n) throws BussinessException{
		List<Movement> movements = Collections.emptyList();
		
		try {
			MovementDAO dao = new MovementDAO(session);
			movements = dao.listLastN(account, n);
			
		} catch (DAOException e) {
			String msg = "Error accesing database";
			throw new BussinessException(msg, e);
		}
		
		return movements;
	}

	/**
	 * Inserta simplemente un movimiento, sin hacer ningun calculo
	 * 
	 * @param m
	 *     moviviento a insertar
	 *     
	 * @return
	 *     id asignado en la base de datos al movimiento insertado
	 *     
	 * @throws BussinessException
	 */
	public long insert(Movement m) throws BussinessException {
		try {
			MovementDAO dao = new MovementDAO(session);
			return dao.insert(m);
			
		} catch (DAOException e) {
			String msg = "Error inserting movement: " + m.getDescription();
			throw new BussinessException(msg, e);
		}
	}

	/**
	 * 
     * @param account
     *         cuenta para la que se calculan los gastos
     * @param concept
     *         concepto para el que se calculan los gastos
	 * @param dateFrom
	 *         fecha desde la que buscar gastos
	 * @param dateTo
	 *         fecha hasta la que buscar gastos
	 * @return
	 *         total de los gastos para una cuenta, concepto y periodo dados
	 *         
	 * @throws BussinessException
	 */
    public double getExpenses(Account account, Concept concept, Date dateFrom, Date dateTo) throws BussinessException {
        try {
            MovementDAO dao = new MovementDAO(session);
            return dao.getExpenses(account, concept, dateFrom, dateTo);
            
        } catch (DAOException e) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
            String msg = "Error calculating expenses for " +
                         "account '" + account.getName() + "', " +
                         "concept '" + concept.getName() + "' " +
                         "from '" + sdf.format(dateFrom) + "' " +
                         "to '" + sdf.format(dateTo) + "'";
            throw new BussinessException(msg, e);
        }
    }
}
