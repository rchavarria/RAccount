package es.rchavarria.raccount.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.model.DoubleMovement;

public class DoubleMovementDAO {
    
    private Session session;
    private SQLValuesFormatter formatter;

    public DoubleMovementDAO(Session session){
        this.session = session;
        formatter = new SQLValuesFormatter();
    }

    private void closeResultSet(ResultSet rs) {
        if(rs != null){
            try { rs.close(); }
            catch (SQLException e1) { }
        }
    }
    
    public long insert(DoubleMovement movement) throws DAOException {
        String names = DoubleMovement.DESCRIPTION + ", " + 
					    DoubleMovement.MOVEMENT_DATE + ", " + 
					    DoubleMovement.AMOUNT + ", " + 
					    DoubleMovement.FINAL_BALANCE + ", " + 
					    DoubleMovement.ID_ACCOUNT + ", " + 
					    DoubleMovement.ID_CONCEPT;
        String sql = null;
        
        try {
        	String valuesFrom = formatter.format(movement, movement.getAccount());
        	sql = "INSERT INTO Movement ("+names+") VALUES ("+valuesFrom+")";
            session.sqlExecute(sql);

            String valuesTo = formatter.format(movement, movement.getAccountTo());
        	sql = "INSERT INTO Movement ("+names+") VALUES ("+valuesTo+")";
            session.sqlExecute(sql);
            
            // se deber�a utilizar una sesi�n transaccional, que permita realizar varias operaciones at�micamente
            session.commit();
        } catch (SQLException e) {
			try { session.rollback(); } 
			catch (Throwable t) { }

			String msg = "Error creating new DoubleMovement. SQL string=["
					+ sql + "]";
			// log.error(msg, e);
			throw new DAOException(msg, e);
        }
        
        String sqlSelect = "SELECT MAX(idMovement) FROM Movement";
        ResultSet rs = null;
        try {
            rs = session.sqlFind(sqlSelect);
            if(rs.next()){
                long id = rs.getLong(1);
                return id;
            }
        } catch (SQLException e) {
        	String msg = "Error retrieving id of new Movement created";
//          log.error(msg, e);
          throw new DAOException(msg, e);
        } finally {
        	closeResultSet(rs);
        }
            
        return -1; //throw exception ?
    }
}    
