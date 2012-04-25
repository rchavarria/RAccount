package es.rchavarria.raccount.frontend.script;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.rchavarria.raccount.bussines.BussinessException;
import es.rchavarria.raccount.bussines.ServiceFacade;
import es.rchavarria.raccount.db.dao.DAOException;
import es.rchavarria.raccount.frontend.dataImporter.ImportException;
import es.rchavarria.raccount.frontend.dataImporter.MovementImporter;
import es.rchavarria.raccount.model.Movement;

public class MovementImporterToDerby {
	private static final Log log = LogFactory.getLog(MovementImporterToDerby.class);

	public void doImport() throws ImportException, DAOException, BussinessException, SQLException, IOException {
		// AccessMovementImporter importer = new AccessMovementImporter("movements.csv");
		MovementImporter importer = new MovementImporter(new FileInputStream("import/movements.csv"));
		List<Movement> movements = importer.doImport();
		log.info("Movements imported: " + movements.size());
		ServiceFacade facade = new ServiceFacade();

		log.info("Movements before importation: " + facade.getMovementList().size());
		for (Movement m : movements) {
			facade.insertMovement(m);
		}
		log.info("Movements after importation: " + facade.getMovementList().size());
	}

}
