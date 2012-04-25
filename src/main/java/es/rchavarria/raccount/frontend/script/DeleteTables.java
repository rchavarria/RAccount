package es.rchavarria.raccount.frontend.script;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.rchavarria.raccount.db.Session;

public class DeleteTables {
    private static final Log log = LogFactory.getLog(DeleteTables.class);
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
