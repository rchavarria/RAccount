package es.rchavarria.raccount.frontend.dataExporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.dao.AccountDAO;
import es.rchavarria.raccount.db.dao.ConceptDAO;
import es.rchavarria.raccount.db.isession.DBSession;
import es.rchavarria.raccount.db.isession.DataBaseTests;
import es.rchavarria.raccount.frontend.dataImporter.MovementImporter;
import es.rchavarria.raccount.model.Movement;

public class MovementExporterTest {

	Session session;
	
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
	
	@Before
	public void setUp() throws Exception {
		session = DBSession.getSession();
	}

	@After
	public void tearDown() throws Exception {
		session.close();
	}

	@Test
	public void testExport() throws Exception {
		List<Movement> original = createMovementList();
		File f = File.createTempFile("movement-test", null);
		FileOutputStream fos = new FileOutputStream(f);
		
		new MovementExporter(fos).export(original);
		
		fos.close();
		FileInputStream fis = new FileInputStream(f);
		List<Movement> imported = new MovementImporter(fis).doImport();
		
		Assert.assertEquals(original.size(), imported.size());
		for(int i = 0; i < original.size(); i++)
			Assert.assertEquals("Index: " + i, original.get(i), imported.get(i));
	}

	private List<Movement> createMovementList() throws Exception {
		List<Movement> list = new ArrayList<Movement>();
		for(int i = 1; i < 10; i++){
			Movement m = new Movement();
			m.setAccount(new AccountDAO(session).find(1L));
			double amount = 3.21d * i;
			NumberFormat nf = NumberFormat.getCurrencyInstance();
			m.setAmount(nf.parse(nf.format(amount)).doubleValue()); //se formatea y luego parsea para evitar errores de conversi�n de NumberFormat
			m.setConcept(new ConceptDAO(session).find(1L));
			m.setDescription("description test");
			double finalBalance = 1234.43 * i; 
			m.setFinalBalance(nf.parse(nf.format(finalBalance)).doubleValue());
			m.setMovementDate(new SimpleDateFormat("dd/MM/yyyy").parse("15/05/2010"));
			
			list.add(m);
		}
		return list;
	}
}
