package es.rchavarria.raccount.frontend.dataReader.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JPanel;

import es.rchavarria.raccount.frontend.dataReader.SequencialReader;
import es.rchavarria.raccount.frontend.dataReader.SequencialReaderFactory;
import es.rchavarria.raccount.frontend.gui.controller.DialogableController;
import es.rchavarria.raccount.model.DoubleMovement;

/**
 * Permite seleccionar un archivo desde donde importar los movimientos así como un <code>ISequencialReader</code> que
 * entiende el formato del fichero seleccionado
 * 
 * @author RChavarria
 * 
 */
public class SequencialReaderSelectorController implements DialogableController<SequencialReader<?>> {

	private SequencialReaderSelectorView view;

	public SequencialReaderSelectorController(final SequencialReaderSelectorView view) {
		this.view = view;
	}

	@Override
	public JPanel getView() {
		return view;
	}

	@Override
	public SequencialReader<DoubleMovement> getElement() {
		String fileName = view.getFilePath();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			// areValuesOK should check the name
		}
		SequencialReaderFactory.Reader reader = view.getReader();

		return SequencialReaderFactory.create(fis, reader);
	}

	@Override
	public boolean areValuesOK() {
		String fileName = view.getFilePath();
		if (fileName == null || fileName.length() == 0) {
			return false;
		}
		return true;
	}

}
