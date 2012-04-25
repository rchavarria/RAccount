package es.rchavarria.raccount.db;

public interface DBConfig {
	public final String PROPERTIES_FILE_PATH = "/database.properties";
	public final String PROPERTY_DRIVER = "db.driver";
	public final String PROPERTY_CONN_STRING = "db.connectionString";

	public String getDriver();

	public String getConnectionURL();
}
