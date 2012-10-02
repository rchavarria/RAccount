package es.rchavarria.raccount.db.dao;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.isession.DataBaseTests;
import es.rchavarria.raccount.db.isession.TransactionalDBSession;
import es.rchavarria.raccount.model.DoubleMovement;
import es.rchavarria.raccount.model.DoubleMovementTestFactory;
import es.rchavarria.raccount.model.Movement;

public class DoubleMovementDAOTest {

	private Session session;

    @Before
    public void setUp() throws Exception {
        session = TransactionalDBSession.getSession();
        DataBaseTests.setup(session);
    }

    @After
    public void tearDown() throws Exception {
    	DataBaseTests.teardown(session);
        session.close();
    }

    @Test
    public void testInsert() throws Exception {
        DoubleMovement m = new DoubleMovementTestFactory(session).create();

        MovementDAO mdao = new MovementDAO(session);
        DoubleMovementDAO dao = new DoubleMovementDAO(session);
        int nMovements = mdao.count();

        long id = dao.insert(m);
        Assert.assertTrue(id != -1);
        int nMovPlus2 = mdao.count();
        Assert.assertEquals(nMovements, nMovPlus2 - 2L);

        Movement mLast = mdao.find(id);
        Assert.assertNotNull(mLast);
        mdao.delete(mLast);
        Movement mFirst = mdao.find(id - 1);
        Assert.assertNotNull(mFirst);
        mdao.delete(mFirst);
        int nEndMov = mdao.count();
        Assert.assertEquals(nMovements, nEndMov);
    }
    
    @Test
    public void testInsertDescriptionContainsSingleQuoutes() throws Exception {
        DoubleMovement m = new DoubleMovementTestFactory(session).create();
        m.setDescription("Kiddy's class");
        
        MovementDAO mdao = new MovementDAO(session);
        DoubleMovementDAO dao = new DoubleMovementDAO(session);
        int nMovements = mdao.count();
        
        long id = dao.insert(m);
        Assert.assertTrue(id != -1);
        int nMovPlus2 = mdao.count();
        Assert.assertEquals(nMovements, nMovPlus2 - 2L);
        
        Movement mLast = mdao.find(id);
        Assert.assertNotNull(mLast);
        mdao.delete(mLast);
        Movement mFirst = mdao.find(id - 1);
        Assert.assertNotNull(mFirst);
        mdao.delete(mFirst);
        int nEndMov = mdao.count();
        Assert.assertEquals(nMovements, nEndMov);
    }
}
