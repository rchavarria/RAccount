package es.rchavarria.raccount.db.dao.populators;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.dao.MovementDAO;
import es.rchavarria.raccount.model.DoubleMovementTestFactory;

public class MovementPopulator {

	private MovementDAO dao;
	private Session session;

	public MovementPopulator(Session session) {
		this.session = session;
		dao = new MovementDAO(session);
	}

	public void populate(int numberOfAccounts) throws Exception{
		for(int i = 0; i < numberOfAccounts; i++){
			dao.insert(new DoubleMovementTestFactory(session).create());
		}
	}
}
