package es.rchavarria.raccount.model;

import java.util.Date;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.dao.AccountDAO;
import es.rchavarria.raccount.db.dao.ConceptDAO;

public class DoubleMovementTestFactory {

	private Session session;
	
    public DoubleMovementTestFactory(Session session) {
		this.session = session;
	}

	public DoubleMovement create() throws Exception {
        return create("test description", new Date(), 12.5d, 21, 4, 1);
    }

    private DoubleMovement create(final String description, final Date movDate, final double amount,
            final int concept, final int accountFrom, final int accountTo) throws Exception {
        
    	DoubleMovement dm = new DoubleMovement();
        dm.setDescription(description);
        dm.setMovementDate(movDate);
        dm.setAmount(amount);

        dm.setConcept(new ConceptDAO(session).find(concept));
        dm.setAccount(new AccountDAO(session).find(accountFrom));
        dm.setAccountTo(new AccountDAO(session).find(accountTo));

        return dm;
    }
}
