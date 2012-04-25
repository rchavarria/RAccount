package es.rchavarria.raccount.db.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.isession.DBSession;
import es.rchavarria.raccount.db.isession.DataBaseTests;
import es.rchavarria.raccount.model.Account;
import es.rchavarria.raccount.model.AccountTestFactory;

public class AccountDAOTest {

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
        Account a = AccountTestFactory.create();

        AccountDAO dao = new AccountDAO(session);
        long id = dao.insert(a);
        a.setIdAccount(id);

        Account c2 = dao.find(a.getIdAccount());
        dao.delete(a);

        Assert.assertEquals(a, c2);
    }

    @Test
    public void testUpdate() throws DAOException {
        Account a = AccountTestFactory.create();

        AccountDAO dao = new AccountDAO(session);
        long id = dao.insert(a);
        a.setIdAccount(id);

        a.setName("other name");
        dao.update(a);

        Account c2 = dao.find(a.getIdAccount());
        dao.delete(a);

        Assert.assertEquals(a.getName(), c2.getName());
    }

    @Test
    public void testInsert() throws Exception {
        Account a = AccountTestFactory.create();

        AccountDAO dao = new AccountDAO(session);
        long id = dao.insert(a);
        a.setIdAccount(id);
        Assert.assertTrue(id != -1);

        Account bbdd = dao.find(id);
        dao.delete(a);

        Assert.assertEquals(a, bbdd);
    }

    @Test
    public void testListAll() throws DAOException {
        Account account = AccountTestFactory.create();

        AccountDAO dao = new AccountDAO(session);
        long id = dao.insert(account);
        account.setIdAccount(id);

        List<Account> list = dao.listAll();
        Assert.assertFalse(list.isEmpty());

        findAndAssertAccount(account, id, list);
    }

    @Test
    public void testListAllAccountables() throws DAOException {
        Account account = AccountTestFactory.create();
        AccountDAO dao = new AccountDAO(session);
        long id = dao.insert(account);
        account.setIdAccount(id);
        List<Account> list = dao.listAllAccountables();
        dao.delete(account);
        Assert.assertTrue(findAndAssertAccount(account, id, list));

        Account notAccountableAccount = AccountTestFactory.create();
        notAccountableAccount.setAccountable(false);
        id = dao.insert(notAccountableAccount);
        notAccountableAccount.setIdAccount(id);
        list = dao.listAllAccountables();
        dao.delete(notAccountableAccount);
        Assert.assertFalse(findAndAssertAccount(notAccountableAccount, id, list));
    }

    private boolean findAndAssertAccount(final Account account, final long id, final List<Account> list) {
        for (Account a : list) {
            if (a.getIdAccount() == id) {
                Assert.assertEquals(account, a);
                return true;
            }
        }
        return false;
    }
}
