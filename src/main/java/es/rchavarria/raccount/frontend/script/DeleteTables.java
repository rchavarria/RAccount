package es.rchavarria.raccount.frontend.script;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.isession.DBSession;

public class DeleteTables {
    private static final Logger log = LoggerFactory.getLogger(DBSession.class);
	private Session session;

    public DeleteTables(Session session){
    	this.session = session;
    }
    
    /**
     * @param args
     * @throws SQLException 
     */
    public void execute() throws SQLException {
        List<String> tableNames = Arrays.asList("Movement", "Account", "Concept");
        for(String name : tableNames){
        	String sql = "DROP TABLE " + name;
        	session.sqlExecute(sql);
        	session.commit();
            log.info("Table ["+name+"] deleted");
        }
    }
}
