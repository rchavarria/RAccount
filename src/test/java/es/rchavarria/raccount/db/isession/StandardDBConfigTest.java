package es.rchavarria.raccount.db.isession;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.rchavarria.raccount.db.DBConfig;

public class StandardDBConfigTest {

	private File f;
	private StandardDBConfig stdDBCfg;
	private final static String DRIVER_PROPERTY_VALUE = "Driver test";
	private final static String CONN_STRING_PROPERTY_VALUE = "Connection string test = with equal character";

	@Before
	public void setUp() throws Exception {
		createTmpFile();

		stdDBCfg = new StandardDBConfig(new FileInputStream(f));
	}

	private void createTmpFile() throws IOException {
		f = File.createTempFile("StandardDBConfigTest", null);
		FileWriter fw = new FileWriter(f);
		fw.write(DBConfig.PROPERTY_DRIVER + "=" + DRIVER_PROPERTY_VALUE + "\n");
		fw.write(DBConfig.PROPERTY_CONN_STRING + "=" + CONN_STRING_PROPERTY_VALUE + "\n");
		fw.close();
	}

	@After
	public void tearDown() throws Exception {
		stdDBCfg = null;
	}

	@Test
	public void testGetDriver() {
		Assert.assertEquals(DRIVER_PROPERTY_VALUE, stdDBCfg.getDriver());
	}

	@Test
	public void testGetConnectionURL() {
		Assert.assertEquals(CONN_STRING_PROPERTY_VALUE, stdDBCfg.getConnectionURL());
	}

}
