package es.rchavarria.raccount.db.dao;

import java.sql.SQLException;

public class DAOException extends Exception {

    private static final long serialVersionUID = 891420937864807485L;

    public DAOException(String msg, SQLException e) {
		super(msg, e);
	}

}
