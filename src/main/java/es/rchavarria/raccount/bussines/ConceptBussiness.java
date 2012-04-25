package es.rchavarria.raccount.bussines;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.dao.ConceptDAO;
import es.rchavarria.raccount.db.dao.DAOException;
import es.rchavarria.raccount.model.Concept;

public class ConceptBussiness {
	private Session session;

	public ConceptBussiness(Session session){
		this.session = session;
	}
	
	public void update(Concept c) throws BussinessException{
		try {
			ConceptDAO dao = new ConceptDAO(session);
			dao.update(c);
			
		} catch (DAOException e) {
			String msg = "Error updating concept: " + c.getName();
			throw new BussinessException(msg, e);
		}
	}

	public Concept find(long idConcept) throws BussinessException {
		ConceptDAO dao = new ConceptDAO(session);
		try {
			return dao.find(idConcept);
		} catch (DAOException e) {
			String msg = "Concept not found, id: " + idConcept;
			throw new BussinessException(msg, e);
		}
	}

	public long insert(Concept c) throws BussinessException {
		ConceptDAO dao = new ConceptDAO(session);
		try {
			return dao.insert(c);
		} catch (DAOException e) {
			String msg = "Error inserting concept: " + c.getName();
			throw new BussinessException(msg, e);
		}
	}
}
