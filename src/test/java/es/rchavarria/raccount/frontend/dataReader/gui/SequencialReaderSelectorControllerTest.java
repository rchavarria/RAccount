package es.rchavarria.raccount.frontend.dataReader.gui;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.rchavarria.raccount.frontend.dataReader.CjMdMovementSequencialReader;
import es.rchavarria.raccount.frontend.dataReader.SequencialReader;
import es.rchavarria.raccount.frontend.dataReader.IbcjMovementSequencialReader;
import es.rchavarria.raccount.frontend.dataReader.IngMovementSequencialReader;
import es.rchavarria.raccount.frontend.dataReader.SequencialReaderFactory;

public class SequencialReaderSelectorControllerTest {

	private SequencialReaderSelectorView view;
	private SequencialReaderSelectorController controller;

	@Before
	public void setUp() throws Exception {
		view = new SequencialReaderSelectorView();
		controller = new SequencialReaderSelectorController(view);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetView() {
		Assert.assertEquals(view, controller.getView());
	}

	@Test
	public void testAreValuesOKFalse() {
		Assert.assertFalse(controller.areValuesOK());
	}

	@Test
	public void testAreValuesOKTrue() throws IOException {
		File file = File.createTempFile(getClass().getSimpleName(), "");
		view.setFilePath(file.getAbsolutePath());

		Assert.assertTrue(controller.areValuesOK());
	}

	@Test
	public void testGetElementING() throws IOException {
		File file = File.createTempFile(getClass().getSimpleName(), "");
		view.setFilePath(file.getAbsolutePath());
		view.setSelectedOption(SequencialReaderFactory.Reader.ING);

		SequencialReader<?> reader = controller.getElement();
		Assert.assertTrue(reader instanceof IngMovementSequencialReader);
	}

	@Test
	public void testGetElementCAJAMADRID() throws IOException {
		File file = File.createTempFile(getClass().getSimpleName(), "");
		view.setFilePath(file.getAbsolutePath());
		view.setSelectedOption(SequencialReaderFactory.Reader.CAJAMADRID);

		SequencialReader<?> reader = controller.getElement();
		Assert.assertTrue(reader instanceof CjMdMovementSequencialReader);
	}

	@Test
	public void testGetElementIBERCAJA() throws IOException {
		File file = File.createTempFile(getClass().getSimpleName(), "");
		view.setFilePath(file.getAbsolutePath());
		view.setSelectedOption(SequencialReaderFactory.Reader.IBERCAJA);

		SequencialReader<?> reader = controller.getElement();
		Assert.assertTrue(reader instanceof IbcjMovementSequencialReader);
	}

}
