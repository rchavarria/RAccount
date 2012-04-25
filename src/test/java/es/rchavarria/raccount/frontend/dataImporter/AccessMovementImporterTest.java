package es.rchavarria.raccount.frontend.dataImporter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.isession.DBSession;
import es.rchavarria.raccount.db.isession.DataBaseTests;
import es.rchavarria.raccount.model.Movement;

public class AccessMovementImporterTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		Session s = DBSession.getSession();
		DataBaseTests.setup(s);
		s.close();
	}
	
	@AfterClass
	public static void tearDownClass() throws Exception {
		Session s = DBSession.getSession();
		DataBaseTests.teardown(s);
		s.close();
	}

	@Test
	public void testDoImport() throws Exception {
		AccessMovementImporter mi = new AccessMovementImporter("/datatest/movements.csv");
		List<Movement> movements = mi.doImport();

		Assert.assertEquals(15, movements.size());
		// movement 1
		assertMovement("10/10/2010", 10d, 22, "Movement Test 1", 1, 100.0, movements.get(0));
		assertMovement("01/10/2010", 10d, 22, "Movement Test 2", 1, 100.0, movements.get(1));
		assertMovement("10/01/2010", 10d, 22, "Movement Test 3", 1, 100.0, movements.get(2));
		assertMovement("10/10/2010", 10d, 22, "Movement Test 4", 1, 100.0, movements.get(3));
		assertMovement("10/10/2010", -10d, 22, "Movement Test 5", 1, 100.0, movements.get(4));
		assertMovement("10/10/2010", 1000d, 22, "Movement Test 6", 1, 100.0, movements.get(5));
		assertMovement("10/10/2010", -1010d, 22, "Movement Test 7", 1, 100.0, movements.get(6));
		assertMovement("10/10/2010", 10.0, 22, "Movement Test 8", 1, -100.0, movements.get(7));
		assertMovement("10/10/2010", 10.0, 22, "Movement Test 9", 1, 1100.0, movements.get(8));
		assertMovement("10/10/2010", 10.0, 22, "Movement Test 10", 1, 100.0, movements.get(9));
		assertMovement("10/10/2010", -10.0, 22, "Movement Test 11", 1, 100.0, movements.get(10));
		assertMovement("10/10/2010", 10.0, 22, "Movement Test 12", 1, 100.0, movements.get(11));
		assertMovement("10/10/2010", 10.0, 22, "Movement Test 13", 1, -100.0, movements.get(12));
		assertMovement("10/10/2010", 10.4, 22, "Movement Test 14", 1, 100.0, movements.get(13));
		assertMovement("10/10/2010", 10.0, 22, "Movement Test 15", 1, 100.4, movements.get(14));
	}

	private void assertMovement(final String date, final double amount, final int concept, final String description,
			final int account, final double finalBalance, final Movement m) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Assert.assertEquals(sdf.parse(date), m.getMovementDate());
		Assert.assertEquals(amount, m.getAmount());
		Assert.assertEquals(Translator.translateConcept(concept), m.getConcept().getIdConcept());
		Assert.assertEquals(description, m.getDescription());
		Assert.assertEquals(Translator.translateAccount(account), m.getAccount().getIdAccount());
		Assert.assertEquals(finalBalance, m.getFinalBalance());
	}

}
