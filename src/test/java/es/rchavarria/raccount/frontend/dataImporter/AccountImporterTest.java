package es.rchavarria.raccount.frontend.dataImporter;

import java.util.List;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.rchavarria.raccount.model.Account;

public class AccountImporterTest {
    private static final Log log = LogFactory.getLog(AccountImporterTest.class);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDoImport() throws Exception {
        // ver el fichero para comprender los Assert
        AccountImporter ai = new AccountImporter(getClass().getResourceAsStream("/datatest/accounts.csv"));
        List<Account> accounts = ai.doImport();

        Assert.assertFalse(accounts.isEmpty());
        Assert.assertEquals(5, accounts.size());
        log.info("Number of imported accounts: " + accounts.size());

        // cuenta 1
        Assert.assertEquals(7.34d, accounts.get(0).getBalance());
        Assert.assertEquals(true, accounts.get(0).isAccountable());
        Assert.assertEquals("mi codigo cuenta", accounts.get(0).getCodeNumber());
        // cuenta 2
        Assert.assertEquals(-0.33d, accounts.get(1).getBalance());
        Assert.assertEquals(true, accounts.get(1).isAccountable());
        Assert.assertNull(accounts.get(1).getCodeNumber());
        // cuenta 3
        Assert.assertEquals(3831.39d, accounts.get(2).getBalance());
        Assert.assertEquals(true, accounts.get(2).isAccountable());
        Assert.assertEquals("mi codigo cuenta", accounts.get(2).getCodeNumber());
        // cuenta 4
        Assert.assertEquals(2273.71d, accounts.get(3).getBalance());
        Assert.assertEquals(false, accounts.get(3).isAccountable());
        Assert.assertEquals("mi codigo cuenta", accounts.get(3).getCodeNumber());
        // cuenta 5
        Assert.assertEquals(-51d, accounts.get(4).getBalance());
        Assert.assertEquals(false, accounts.get(4).isAccountable());
        Assert.assertNull(accounts.get(4).getCodeNumber());
    }
}
