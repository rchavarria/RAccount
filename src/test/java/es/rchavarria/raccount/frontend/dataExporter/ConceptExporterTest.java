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

import es.rchavarria.raccount.frontend.dataImporter.ConceptImporter;
import es.rchavarria.raccount.model.Concept;

public class ConceptExporterTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExport() throws Exception {
		List<Concept> original = createConceptList();
		File f = File.createTempFile("concept-test", null);
		FileOutputStream fos = new FileOutputStream(f);
		
		new ConceptExporter(fos).export(original);
		
		fos.close();
		FileInputStream fis = new FileInputStream(f);
		List<Concept> imported = new ConceptImporter(fis).doImport();
		
		Assert.assertEquals(original.size(), imported.size());
		for(int i = 0; i < original.size(); i++)
			Assert.assertEquals(original.get(i), imported.get(i));
	}

	private List<Concept> createConceptList() {
		List<Concept> list = new ArrayList<Concept>();
		for(int i = 1; i < 10; i++){
			Concept c = new Concept();
//			c.setIdConcept(i); //el id concept no se importa, luego tampoco se debe exportar
			c.setName("Concept test n: " + i);
			c.setVisible(i % 2 == 0);
			list.add(c);
		}
		return list;
	}
}
