package es.rchavarria.raccount.db.dao.populators;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.dao.ConceptDAO;
import es.rchavarria.raccount.db.dao.DAOException;
import es.rchavarria.raccount.model.ConceptTestFactory;

public class ConceptPopulator {

	private ConceptDAO dao;

	public ConceptPopulator(Session session) {
		dao = new ConceptDAO(session);
	}

	public void populate(int numberOfAccounts) throws DAOException{
		for(int i = 0; i < numberOfAccounts; i++){
			String name = "Concept name: " + i;
			dao.insert(ConceptTestFactory.create(i + 1, name, true));
		}
	}
}
