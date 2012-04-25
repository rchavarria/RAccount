package es.rchavarria.raccount.frontend.dataImporter;

import java.io.InputStream;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.rchavarria.raccount.model.Concept;

public class ConceptImporterTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDoImport() throws Exception {
		InputStream is = getClass().getResourceAsStream("/datatest/concepts.csv");
		ConceptImporter ci = new ConceptImporter(is);
		List<Concept> concepts = ci.doImport();

		Assert.assertEquals(3, concepts.size());
		// concept 1
		Assert.assertEquals("Concept Test 1", concepts.get(0).getName());
		Assert.assertEquals(false, concepts.get(0).isVisible());
		// concept 2
		Assert.assertEquals("Concept Test 2", concepts.get(1).getName());
		Assert.assertEquals(true, concepts.get(1).isVisible());
		// concept 3
		Assert.assertEquals("Concept Test 3", concepts.get(2).getName());
		Assert.assertEquals(true, concepts.get(2).isVisible());
	}

}
