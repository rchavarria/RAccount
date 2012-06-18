package es.rchavarria.raccount.frontend.script;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.rchavarria.raccount.bussines.BussinessException;
import es.rchavarria.raccount.bussines.ServiceFacade;
import es.rchavarria.raccount.db.dao.DAOException;
import es.rchavarria.raccount.db.isession.DBSession;
import es.rchavarria.raccount.frontend.dataImporter.AccountImporter;
import es.rchavarria.raccount.frontend.dataImporter.ImportException;
import es.rchavarria.raccount.model.Account;

public class AccountImporterToDerby {
    private static final Logger log = LoggerFactory.getLogger(DBSession.class);

	public void doImport() throws ImportException, DAOException, BussinessException, SQLException, IOException {
		AccountImporter importer = new AccountImporter(new FileInputStream("import/accounts.csv"));
		List<Account> concepts = importer.doImport();
		log.info("Accounts imported: " + concepts.size());
		ServiceFacade facade = new ServiceFacade();

		log.info("Accounts before importation: " + facade.getAccountabelAccountList().size());
		for (Account a : concepts) {
			facade.insertAccount(a);
		}
		log.info("Accounts after importation: " + facade.getAccountabelAccountList().size());
	}
}
