package es.rchavarria.raccount.bussines;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.dao.AccountDAO;
import es.rchavarria.raccount.db.dao.ConceptDAO;
import es.rchavarria.raccount.db.dao.DAOException;
import es.rchavarria.raccount.db.dao.MovementDAO;
import es.rchavarria.raccount.db.isession.DBSession;
import es.rchavarria.raccount.db.isession.TransactionalDBSession;
import es.rchavarria.raccount.frontend.dataCompleter.gui.EditDoubleMovementController;
import es.rchavarria.raccount.model.Account;
import es.rchavarria.raccount.model.Concept;
import es.rchavarria.raccount.model.DoubleMovement;
import es.rchavarria.raccount.model.ExpensesByConcept;
import es.rchavarria.raccount.model.Movement;

/**
 * Fachada para separar las funcionalidades de los DAOs
 * 
 * @author RChavarria
 */
public class ServiceFacade {

    private final static Logger log = LoggerFactory.getLogger(EditDoubleMovementController.class);

    public List<Account> getAccountList() throws SQLException, DAOException, IOException {
        Session session = null;
        try {
            session = DBSession.getSession();
            return new AccountDAO(session).listAll();

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Concept> getVisibleConceptList() throws DAOException, SQLException, IOException {
        Session session = null;
        try {
            session = DBSession.getSession();
            return new ConceptDAO(session).listAllVisible();

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Concept> getConceptList() throws DAOException, SQLException, IOException {
        Session session = null;
        try {
            session = DBSession.getSession();
            return new ConceptDAO(session).listAll();

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Movement> listLastNMovements(final Account account, final int n) throws BussinessException,
            SQLException, IOException {
        Session session = null;
        try {
            session = DBSession.getSession();
            return new MovementBussiness(session).listLastNMovements(account, n);

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void updateConcept(final Concept c) throws SQLException, BussinessException, IOException {
        Session session = null;
        try {
            session = DBSession.getSession();
            new ConceptBussiness(session).update(c);

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void updateAccount(final Account a) throws BussinessException, SQLException, IOException {
        Session session = null;
        try {
            session = DBSession.getSession();
            new AccountBussiness(session).update(a);

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Account findAccount(final long idAccount) throws BussinessException, SQLException, IOException {
        Session session = null;
        try {
            session = DBSession.getSession();
            return new AccountBussiness(session).find(idAccount);

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Movement> getMovementList() throws DAOException, SQLException, IOException {
        Session session = null;
        try {
            session = DBSession.getSession();
            return new MovementDAO(session).listAll();

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Concept findConcept(final long idConcept) throws SQLException, BussinessException, IOException {
        Session session = null;
        try {
            session = DBSession.getSession();
            return new ConceptBussiness(session).find(idConcept);

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public long insertConcept(final Concept c) throws BussinessException, SQLException, IOException {
        Session session = null;
        try {
            session = DBSession.getSession();
            return new ConceptBussiness(session).insert(c);

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public long insertAccount(final Account a) throws BussinessException, SQLException, IOException {
        Session session = null;
        try {
            session = DBSession.getSession();
            return new AccountBussiness(session).insert(a);

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public long insertMovement(final Movement m) throws BussinessException, SQLException, IOException {
        Session session = null;
        try {
            session = DBSession.getSession();
            return new MovementBussiness(session).insert(m);

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void insertDoubleMovement(final DoubleMovement dm) throws BussinessException, SQLException, IOException {
        Session session = null;
        try {
            session = TransactionalDBSession.getSession();
            new MovementBussiness(session).insertDoubleMovement(dm);

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<ExpensesByConcept> getExpenses(Account account, Date dateFrom, Date dateTo) throws BussinessException, DAOException, SQLException, IOException {
        List<Concept> concepts = getVisibleConceptList();
        log.info("Hay " + concepts.size() + " conceptos");

        List<ExpensesByConcept> expenses = new ArrayList<ExpensesByConcept>();
        Session session = null;
        try {
            session = DBSession.getSession();
            MovementBussiness mb = new MovementBussiness(session);
            for(Concept concept : concepts){
                double expense = mb.getExpenses(account, concept, dateFrom, dateTo);
                expenses.add(new ExpensesByConcept(expense, concept));
            }

        } finally {
            if (session != null) { session.close(); }
        }
        
        return expenses;
    }
}
