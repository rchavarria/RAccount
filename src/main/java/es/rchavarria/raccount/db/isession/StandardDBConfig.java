package es.rchavarria.raccount.db.isession;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import es.rchavarria.raccount.db.DBConfig;

public class StandardDBConfig implements DBConfig {
	private Properties cfgProperties;

	public StandardDBConfig(final InputStream isCfgFile) throws IOException {
		cfgProperties = new Properties();
		cfgProperties.load(isCfgFile);
	}

	@Override
	public String getDriver() {
		return cfgProperties.getProperty(PROPERTY_DRIVER);
	}

	@Override
	public String getConnectionURL() {
		return cfgProperties.getProperty(PROPERTY_CONN_STRING);
	}
}
