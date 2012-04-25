package es.rchavarria.raccount.frontend.script;

import java.util.Arrays;
import java.util.List;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.isession.DBSession;

public class Install {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
    	List<Session> ss = Arrays.asList(DBSession.getSession());
    	for(Session s : ss){
    		new DeleteTables(s).execute();
    		new CreateTables(s).execute();
    		
    		new ConceptImporterToDerby().doImport();
    		new AccountImporterToDerby().doImport();
    		new MovementImporterToDerby().doImport();
    		
    		s.close();
    	}
    }

}
