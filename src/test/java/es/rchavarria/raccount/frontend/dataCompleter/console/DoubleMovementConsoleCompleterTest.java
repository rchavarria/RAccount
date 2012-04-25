package es.rchavarria.raccount.frontend.dataCompleter.console;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.isession.DBSession;
import es.rchavarria.raccount.db.isession.DataBaseTests;
import es.rchavarria.raccount.model.DoubleMovement;

public class DoubleMovementConsoleCompleterTest {
	
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
	public void testComplete() throws Exception {
		PipedInputStream pis = new PipedInputStream();
		PipedOutputStream pos = new PipedOutputStream(pis);
		PrintStream ps = new PrintStream(pos);
		ps.println("15-05-1980"); //valid date
		ps.println("a description");
		ps.println("-32,5");
		ps.println("1");//an account from
		ps.println("1");//an account to
		ps.println("1");//a concept
		ps.println();//a concept
		
		DoubleMovementConsoleCompleter completer = new DoubleMovementConsoleCompleter(System.out, pis);
		DoubleMovement m = completer.complete(new DoubleMovement(), 1);
		
		Assert.assertNotNull(m.getMovementDate());
		Assert.assertNotNull(m.getDescription());
		Assert.assertNotNull(m.getAmount());
		Assert.assertNotNull(m.getAccount());
		Assert.assertNotNull(m.getConcept());
	}

}
