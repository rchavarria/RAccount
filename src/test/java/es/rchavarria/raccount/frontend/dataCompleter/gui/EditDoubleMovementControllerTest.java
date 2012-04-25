package es.rchavarria.raccount.frontend.dataCompleter.gui;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.isession.DBSession;
import es.rchavarria.raccount.db.isession.DataBaseTests;
import es.rchavarria.raccount.model.DoubleMovement;
import es.rchavarria.raccount.model.DoubleMovementTestFactory;

public class EditDoubleMovementControllerTest {

	private Session session;
	private EditDoubleMovementView view;
	private DoubleMovement dm;
	private EditDoubleMovementController controller;

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

		view = new EditDoubleMovementView();
		dm = new DoubleMovementTestFactory(session).create();
		controller = new EditDoubleMovementController(dm, view);
	}

	@After
	public void tearDown() throws Exception {
		controller = null;
		dm = null;
		view = null;
	}

	@Test
	public void testGetView() {
		Assert.assertEquals(view, controller.getView());
	}

	@Test
	public void testGetElement() {
		Assert.assertEquals(dm, controller.getElement());
	}

	@Test
	public void testAreValuesOK() {
		Assert.assertTrue(controller.areValuesOK());
	}

	@Test
	public void testValueWrongDate() {
		view.setMovementDate("wrong date");
		Assert.assertFalse(controller.areValuesOK());
	}

	@Test
	public void testValueWrongAmount() {
		view.setAmount("wrong amount");
		Assert.assertFalse(controller.areValuesOK());
	}
}
