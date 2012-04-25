package es.rchavarria.raccount.frontend.dataMixer;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.dao.DoubleMovementFactory;
import es.rchavarria.raccount.db.isession.DBSession;
import es.rchavarria.raccount.db.isession.DataBaseTests;
import es.rchavarria.raccount.db.isession.TransactionalDBSession;
import es.rchavarria.raccount.model.DoubleMovement;

public class AccountFromMixerTest {

    private AccountFromMixer mixer;
    private Session session;
	
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
        mixer = new AccountFromMixer();
        session = TransactionalDBSession.getSession();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMix() throws Exception {
        DoubleMovement dmOrigin = DoubleMovementFactory.createDoubleMovement(session);
        DoubleMovement dmTarget = new DoubleMovement();
        dmTarget = mixer.mix(dmTarget, dmOrigin);

        Assert.assertEquals(dmOrigin.getAccount(), dmTarget.getAccount());
    }
}
