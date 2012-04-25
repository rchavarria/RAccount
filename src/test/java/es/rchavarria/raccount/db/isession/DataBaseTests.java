package es.rchavarria.raccount.db.isession;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.dao.populators.AccountPopulator;
import es.rchavarria.raccount.db.dao.populators.ConceptPopulator;
import es.rchavarria.raccount.frontend.script.CreateTables;
import es.rchavarria.raccount.frontend.script.DeleteTables;

public class DataBaseTests {

	public static void setup(Session session) throws Exception {
        new CreateTables(session).execute();
        
        new AccountPopulator(session).populate(15);
        new ConceptPopulator(session).populate(35);
	}

	public static void teardown(Session session) throws Exception {
    	new DeleteTables(session).execute();
	}
}
