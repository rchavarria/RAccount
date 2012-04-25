package es.rchavarria.raccount.bussines;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.dao.AccountDAO;
import es.rchavarria.raccount.db.dao.DAOException;
import es.rchavarria.raccount.model.Account;

public class AccountBussiness {
	private Session session;

	public AccountBussiness(Session session){
		this.session = session;
	}
	
	public void update(Account a) throws BussinessException{
		try {
			AccountDAO dao = new AccountDAO(session);
			dao.update(a);
			
		} catch (DAOException e) {
			String msg = "Error updating accuont: " + a.getName();
			throw new BussinessException(msg, e);
		}
	}

	public Account find(long idAccount) throws BussinessException {
		try {
			AccountDAO dao = new AccountDAO(session);
			return dao.find(idAccount);
			
		} catch (DAOException e) {
			String msg = "Account not found, id: " + idAccount;
			throw new BussinessException(msg, e);
		}
	}

	public long insert(Account a) throws BussinessException {
		try {
			AccountDAO dao = new AccountDAO(session);
			return dao.insert(a);
			
		} catch (DAOException e) {
			String msg = "Error inserting account: " + a.getName();
			throw new BussinessException(msg, e);
		}
	}
}
