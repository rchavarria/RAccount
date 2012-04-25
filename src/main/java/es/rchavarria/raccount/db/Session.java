package es.rchavarria.raccount.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Session {

    public ResultSet sqlFind(String sql) throws SQLException;
    public void sqlExecute(String sql) throws SQLException;
    public void commit() throws SQLException;
    public void rollback() throws SQLException;
    public void close();
}
