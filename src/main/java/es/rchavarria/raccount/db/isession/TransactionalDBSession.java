package es.rchavarria.raccount.db.isession;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.rchavarria.raccount.db.DBConfig;
import es.rchavarria.raccount.db.Session;

public class TransactionalDBSession implements Session {
	private static final Log log = LogFactory.getLog(TransactionalDBSession.class);
	private static Session session;

	private Connection conn;

	public static Session getSession() throws SQLException, IOException {
		if (session == null) {
			InputStream is = TransactionalDBSession.class.getResourceAsStream(DBConfig.PROPERTIES_FILE_PATH);
			session = new TransactionalDBSession(new StandardDBConfig(is));
		}
		return session;
	}

	private TransactionalDBSession(final DBConfig cfg) throws SQLException {
		try {
			Class.forName(cfg.getDriver());
		} catch (java.lang.ClassNotFoundException e) {
			log.error("Error loading database driver [" + cfg.getDriver() + "]", e);
		}

		this.conn = DriverManager.getConnection(cfg.getConnectionURL());
		this.conn.setAutoCommit(false);
	}

	@Override
	public ResultSet sqlFind(final String sql) throws SQLException {
		Statement st = this.conn.createStatement();
		return st.executeQuery(sql);
	}

	@Override
	public void sqlExecute(final String sql) throws SQLException {
		Statement st = this.conn.createStatement();
		st.execute(sql);
	}

	@Override
	public void close() {
		try {
			this.conn.close();
		} catch (Throwable t) {
		}
		session = null;
	}

	@Override
	public void rollback() throws SQLException {
		this.conn.rollback();
	}

	@Override
	public void commit() throws SQLException {
		this.conn.commit();
	}
}
