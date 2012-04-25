package es.rchavarria.raccount.db.dao;

import java.util.Date;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.model.DoubleMovement;

public class DoubleMovementFactory {

    public static DoubleMovement createDoubleMovement(final Session session) throws DAOException {
        DoubleMovement m = new DoubleMovement();
        m.setDescription("desc");
        m.setAmount(3.4d);
        m.setFinalBalance(55d);
        m.setMovementDate(new Date());
        m.setAccount(new AccountDAO(session).listAll().get(0));
        m.setConcept(new ConceptDAO(session).listAll().get(0));
        m.setAccountTo(new AccountDAO(session).listAll().get(1));
        return m;
    }

}
