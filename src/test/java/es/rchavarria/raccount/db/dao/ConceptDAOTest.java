package es.rchavarria.raccount.db.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.isession.DBSession;
import es.rchavarria.raccount.db.isession.DataBaseTests;
import es.rchavarria.raccount.model.Concept;

public class ConceptDAOTest {

    private Session session;

    @Before
    public void setUp() throws Exception {
        session = DBSession.getSession();
        DataBaseTests.setup(session);
    }

    @After
    public void tearDown() throws Exception {
    	DataBaseTests.teardown(session);
        session.close();
    }

    @Test
    public void testFind() throws DAOException {
        Concept c = createNotVisibleConcept();

        ConceptDAO dao = new ConceptDAO(session);
        long id = dao.insert(c);
        c.setIdConcept(id);

        Concept c2 = dao.find(c.getIdConcept());
        Assert.assertEquals(c.getIdConcept(), c2.getIdConcept());
        Assert.assertEquals(c.getName(), c2.getName());
        Assert.assertEquals(c.isVisible(), c2.isVisible());

        dao.delete(c);
    }

    @Test
    public void testUpdate() throws DAOException {
        Concept c = createNotVisibleConcept();

        ConceptDAO dao = new ConceptDAO(session);
        long id = dao.insert(c);
        c.setIdConcept(id);

        c.setName("other name");
        dao.update(c);

        Concept c2 = dao.find(c.getIdConcept());
        dao.delete(c);

        Assert.assertEquals(c.getName(), c2.getName());
    }

    @Test
    public void testInsert() throws DAOException {
        Concept c = createNotVisibleConcept();

        ConceptDAO dao = new ConceptDAO(session);
        long id = dao.insert(c);
        c.setIdConcept(id);
        Assert.assertTrue(id != -1);

        Concept bbdd = dao.find(id);
        dao.delete(c);

        Assert.assertEquals(c, bbdd);
    }

    @Test
    public void testListAll() throws DAOException {
        Concept concept = createNotVisibleConcept();

        ConceptDAO dao = new ConceptDAO(session);
        long id = dao.insert(concept);
        concept.setIdConcept(id);

        List<Concept> list = dao.listAll();
        dao.delete(concept);
        Assert.assertFalse(list.isEmpty());

        Assert.assertTrue(findAndCheckConceptInList(concept, id, list));
    }

    private boolean findAndCheckConceptInList(final Concept concept, final long id, final List<Concept> list) {
        for (Concept c : list) {
            if (c.getIdConcept() == id) {
                Assert.assertEquals(concept, c);
                return true;
            }
        }
        return false;
    }

    @Test
    public void testListAllVisible() throws DAOException {
        Concept concept = createNotVisibleConcept();
        ConceptDAO dao = new ConceptDAO(session);
        long id = dao.insert(concept);
        concept.setIdConcept(id);
        List<Concept> list = dao.listAllVisible();
        dao.delete(concept);
        Assert.assertFalse(findAndCheckConceptInList(concept, id, list));

        // visible
        Concept visibleConcept = createNotVisibleConcept();
        visibleConcept.setVisible(true);
        id = dao.insert(visibleConcept);
        visibleConcept.setIdConcept(id);
        list = dao.listAllVisible();
        dao.delete(visibleConcept);
        Assert.assertTrue(findAndCheckConceptInList(visibleConcept, id, list));
    }

    private Concept createNotVisibleConcept() {
        Concept c = new Concept();
        c.setName("name to find");
        c.setVisible(false);
        return c;
    }

}
