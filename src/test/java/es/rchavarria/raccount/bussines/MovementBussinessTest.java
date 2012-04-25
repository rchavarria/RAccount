package es.rchavarria.raccount.bussines;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.dao.AccountDAO;
import es.rchavarria.raccount.db.dao.ConceptDAO;
import es.rchavarria.raccount.db.dao.DAOException;
import es.rchavarria.raccount.db.isession.DataBaseTests;
import es.rchavarria.raccount.db.isession.TransactionalDBSession;
import es.rchavarria.raccount.model.Account;
import es.rchavarria.raccount.model.Concept;
import es.rchavarria.raccount.model.ConceptTestFactory;
import es.rchavarria.raccount.model.DoubleMovement;

public class MovementBussinessTest {

	private Session session;
	private ConceptDAO cdao;
	private AccountDAO adao;
	
	@Before
	public void setUp() throws Exception {
		session = TransactionalDBSession.getSession();
		DataBaseTests.setup(session);
		
		adao = new AccountDAO(session);
		cdao = new ConceptDAO(session);
	}

	@After
	public void tearDown() throws Exception {
		DataBaseTests.teardown(session);
		session.close();
		session = null;
		adao = null;
		cdao = null;
	}
	
	@Test
	public void testInsertDoubleMovementOutcome() throws Exception {
		testInOut(-10.0d);
	}
	
	@Test
	public void testInsertDoubleMovementIncome() throws DAOException, BussinessException {
		testInOut(10.0d);
	}
    
    @Test
    public void testInsertTransfer_BothAccountsAreAccountable() throws Exception {
        int idAccountFrom = 4;
        int idAccountTo = 6;
        int idMainAccount = 1;

        int concept = insertTransferConcept();
        assertEquals("Traspasos", cdao.find(concept).getName());
        
        double amount = 10.0d;
        
        DoubleMovement dm = createDoubleMovement(amount, idAccountFrom, idAccountTo, concept);
        
        Account accountFrom = adao.find(idAccountFrom);
        Account accountTo = adao.find(idAccountTo);
        Account mainAccount = adao.find(idMainAccount);
        
        // comprobar que las cuentas entre las que se hará la transferencia, son contables
        assertTrue("La cuenta 'desde' debe ser contable", accountFrom.isAccountable());
        assertTrue("La cuenta 'a' debe ser contable", accountTo.isAccountable());
        
        double accountFromInitialBalance = accountFrom.getBalance();
        double accountToInitialBalance = accountTo.getBalance();
        double mainAccountInitialBalance = mainAccount.getBalance();
        
        //TEST
        new MovementBussiness(session).insertDoubleMovement(dm);
        
        //verify
        double accountFromFinalBalance = adao.find(idAccountFrom).getBalance();
        double accountToFinalBalance = adao.find(idAccountTo).getBalance();
        double mainAccountFinalBalance = adao.find(idMainAccount).getBalance();
        assertEquals(accountFromInitialBalance, accountFromFinalBalance - amount, 0.01);
        assertEquals(accountToInitialBalance, accountToFinalBalance + amount, 0.01);
        assertEquals(mainAccountInitialBalance, mainAccountFinalBalance, 0.01);
    }
    
    /**
     * Si la cuenta 'a' es NO contable, cuando traspase dinero a ella será como un gasto, ya que
     * estoy transfiriendo un dinero con el que ya no 'quiero' contar.
     * 
     * @throws Exception
     */
    @Test
    public void testInsertTransfer_AccountToIsNotAccountable() throws Exception {
        int idAccountFrom = 4;
        int idAccountTo = 8; //la 8 suele ser no contable, aun asi hay asserts para comprobar esto
        int idMainAccount = 1;

        int concept = insertTransferConcept();
        assertEquals("Traspasos", cdao.find(concept).getName());
        
        double amount = 10.0d;
        
        DoubleMovement dm = createDoubleMovement(amount, idAccountFrom, idAccountTo, concept);
        
        Account accountFrom = adao.find(idAccountFrom);
        Account accountTo = adao.find(idAccountTo);
        Account mainAccount = adao.find(idMainAccount);
        
        // comprobar que las cuentas entre las que se hará la transferencia, son contables o no
        assertTrue("La cuenta 'desde' debe ser contable", accountFrom.isAccountable());
        assertFalse("La cuenta 'a' debe ser NO contable", accountTo.isAccountable());
        
        double accountFromInitialBalance = accountFrom.getBalance();
        double accountToInitialBalance = accountTo.getBalance();
        double mainAccountInitialBalance = mainAccount.getBalance();
        
        //TEST
        new MovementBussiness(session).insertDoubleMovement(dm);
        
        //verify
        double accountFromFinalBalance = adao.find(idAccountFrom).getBalance();
        double accountToFinalBalance = adao.find(idAccountTo).getBalance();
        double mainAccountFinalBalance = adao.find(idMainAccount).getBalance();
        assertEquals(accountFromInitialBalance, accountFromFinalBalance - amount, 0.01);
        assertEquals(accountToInitialBalance, accountToFinalBalance + amount, 0.01);
        assertEquals(mainAccountInitialBalance, mainAccountFinalBalance - amount, 0.01);
    }
    
    /**
     * Si la cuenta 'desde' es NO contable, cuando traspase dinero de ella será como un ingreso, ya que
     * estoy transfiriendo un dinero con el que ya no contaba y ahora contaré
     * 
     * @throws Exception
     */
    @Test
    public void testInsertTransfer_AccountFromIsNotAccountable() throws Exception {
        int idAccountFrom = 8;
        int idAccountTo = 6;
        int idMainAccount = 1;
        
        int concept = insertTransferConcept();
        assertEquals("Traspasos", cdao.find(concept).getName());
        
        double amount = 10.0d;
        
        DoubleMovement dm = createDoubleMovement(amount, idAccountFrom, idAccountTo, concept);
        
        Account accountFrom = adao.find(idAccountFrom);
        Account accountTo = adao.find(idAccountTo);
        Account mainAccount = adao.find(idMainAccount);
        
        // comprobar que las cuentas entre las que se hará la transferencia, son contables o no
        assertFalse("La cuenta 'desde' debe ser NO contable", accountFrom.isAccountable());
        assertTrue("La cuenta 'a' debe ser contable", accountTo.isAccountable());
        
        double accountFromInitialBalance = accountFrom.getBalance();
        double accountToInitialBalance = accountTo.getBalance();
        double mainAccountInitialBalance = mainAccount.getBalance();
        
        //TEST
        new MovementBussiness(session).insertDoubleMovement(dm);
        
        //verify
        double accountFromFinalBalance = adao.find(idAccountFrom).getBalance();
        double accountToFinalBalance = adao.find(idAccountTo).getBalance();
        double mainAccountFinalBalance = adao.find(idMainAccount).getBalance();
        assertEquals(accountFromInitialBalance, accountFromFinalBalance - amount, 0.01);
        assertEquals(accountToInitialBalance, accountToFinalBalance + amount, 0.01);
        assertEquals(mainAccountInitialBalance, mainAccountFinalBalance + amount, 0.01);
    }

	private int insertTransferConcept() throws Exception {
		Concept c = ConceptTestFactory.create();
		c.setName("Traspasos");
		return (int) cdao.insert(c);
	}

	private void testInOut(double amount) throws DAOException, BussinessException {
		int accountFrom = 4;
		int accountTo = 6;
		int mainAccount = 1;
		int concept = 5;
		//asegurarnos de que no cogemos el concepto de trasapasos
		assertFalse("Traspasos".equals(cdao.find(concept).getName()));
		
		DoubleMovement dm = createDoubleMovement(amount, accountFrom, accountTo, concept);
		
		double accountFromInitialBalance = adao.find(accountFrom).getBalance();
		double accountToInitialBalance = adao.find(accountTo).getBalance();
		double mainAccountInitialBalance = adao.find(mainAccount).getBalance();
		
		//TEST
		new MovementBussiness(session).insertDoubleMovement(dm);
		
		//verify
		double accountFromFinalBalance = adao.find(accountFrom).getBalance();
		double accountToFinalBalance = adao.find(accountTo).getBalance();
		double mainAccountFinalBalance = adao.find(mainAccount).getBalance();
		assertEquals(accountFromInitialBalance, accountFromFinalBalance - amount, 0.01);
		assertEquals(accountToInitialBalance, accountToFinalBalance, 0.01);
		assertEquals(mainAccountInitialBalance, mainAccountFinalBalance - amount, 0.01);
	}

	private DoubleMovement createDoubleMovement(double amount, int accountFrom, int accountTo, int concept) 
			throws DAOException {
		DoubleMovement dm = new DoubleMovement();
		dm.setMovementDate(new Date());
		dm.setDescription("testing outcome");
		dm.setAmount(amount);
		dm.setConcept(cdao.find(concept));
		dm.setAccount(adao.find(accountFrom));
		dm.setAccountTo(adao.find(accountTo));
		return dm;
	}

}
