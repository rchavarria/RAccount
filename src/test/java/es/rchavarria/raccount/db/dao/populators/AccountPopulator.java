package es.rchavarria.raccount.db.dao.populators;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.dao.AccountDAO;
import es.rchavarria.raccount.db.dao.DAOException;
import es.rchavarria.raccount.model.AccountTestFactory;

public class AccountPopulator {

	private AccountDAO dao;

	public AccountPopulator(Session session) {
		dao = new AccountDAO(session);
	}

	public void populate(int numberOfAccounts) throws DAOException{
		for(int i = 0; i < numberOfAccounts; i++){
			String name = "Account name: " + i;
			boolean accountable = (i % 7) != 0; //alguna cuenta como NO contable

			dao.insert(AccountTestFactory.create(name, accountable));
		}
	}
}
