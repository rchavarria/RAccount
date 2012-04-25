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
import es.rchavarria.raccount.frontend.dataImporter.ConceptImporter;
import es.rchavarria.raccount.frontend.dataImporter.ImportException;
import es.rchavarria.raccount.model.Concept;

public class ConceptImporterToDerby {
	private static final Log log = LogFactory.getLog(ConceptImporterToDerby.class);

	public void doImport() throws ImportException, DAOException, BussinessException, SQLException, IOException {
		FileInputStream fis = new FileInputStream("import/concepts.csv");
		ConceptImporter importer = new ConceptImporter(fis);
		List<Concept> concepts = importer.doImport();
		log.info("Concepts imported: " + concepts.size());
		ServiceFacade facade = new ServiceFacade();

		log.info("Concepts before importation: " + facade.getVisibleConceptList().size());
		for (Concept c : concepts) {
			facade.insertConcept(c);
		}
		log.info("Concepts after importation: " + facade.getVisibleConceptList().size());
	}
}
