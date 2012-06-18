package es.rchavarria.raccount.db.isession;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.rchavarria.raccount.db.DBConfig;
import es.rchavarria.raccount.db.Session;

public class DBSession implements Session {
	private static final Logger log = LoggerFactory.getLogger(DBSession.class);
	private static Session session;

	private Connection conn;

	public static Session getSession() throws SQLException, IOException {
		if (session == null) {
			InputStream is = DBSession.class.getResourceAsStream(DBConfig.PROPERTIES_FILE_PATH);
			session = new DBSession(new StandardDBConfig(is));
		}
		return session;
	}

	private DBSession(final DBConfig cfg) throws SQLException {
		try {
			Class.forName(cfg.getDriver());
		} catch (java.lang.ClassNotFoundException e) {
			log.error("Error loading database driver [" + cfg.getDriver() + "]", e);
		}

		this.conn = DriverManager.getConnection(cfg.getConnectionURL());
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
	public void rollback() {
		log.info("rollback");
	}

	@Override
	public void commit() {
		log.info("commit");
	}
}
