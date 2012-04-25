package es.rchavarria.raccount.frontend.dataExporter;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.rchavarria.raccount.frontend.dataImporter.AccountImporter;
import es.rchavarria.raccount.model.Account;

public class AccountExporterTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExport() throws Exception {
		List<Account> original = createAccountList();
		File f = File.createTempFile("concept-test", null);
		FileOutputStream fos = new FileOutputStream(f);
		
		new AccountExporter(fos).export(original);
		
		fos.close();
		FileInputStream fis = new FileInputStream(f);
		List<Account> imported = new AccountImporter(fis).doImport();
		
		Assert.assertEquals(original.size(), imported.size());
		for(int i = 0; i < original.size(); i++)
			Assert.assertEquals("Idx=" + i, original.get(i), imported.get(i));
	}

	private List<Account> createAccountList() {
		List<Account> list = new ArrayList<Account>();
		for(int i = 1; i < 10; i++){
			Account a = new Account();
			a.setAccountable(i % 2 == 0);
			a.setBalance(2.31 * i);
			a.setCodeNumber("Code Number Test: " + i);
			a.setName("Account Name Test: " + i);
			list.add(a);
		}
		return list;
	}
}
