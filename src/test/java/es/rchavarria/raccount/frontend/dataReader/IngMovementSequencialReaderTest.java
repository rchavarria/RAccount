package es.rchavarria.raccount.frontend.dataReader;

import java.io.InputStream;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.rchavarria.raccount.model.DoubleMovement;

public class IngMovementSequencialReaderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNext() throws Exception {
		InputStream is = getClass().getResourceAsStream("/datatest/mov ing.csv");

		SequencialReader<DoubleMovement> importer = new IngMovementSequencialReader(is);
		int count = 0;
		DoubleMovement m = null;
		while ((m = importer.next()) != null) {
			Assert.assertNotNull(m.getMovementDate());
			Assert.assertNotNull(m.getDescription());
			Assert.assertNotNull(m.getAmount());
			count++;
		}

		Assert.assertTrue(count > 0);
	}

}
