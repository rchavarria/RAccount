package es.rchavarria.raccount.frontend.dataReader.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import es.rchavarria.raccount.frontend.dataReader.SequencialReaderFactory;

public class BtnSelectPressedAction implements ActionListener {

	private SequencialReaderSelectorView view;
	private JFileChooser chooser;

	public BtnSelectPressedAction(final SequencialReaderSelectorView view) {
		this.view = view;
		chooser = new JFileChooser();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		chooser.setVisible(true);

		if (chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			String fileName = file.getAbsolutePath();
			view.setFilePath(fileName);

			if (fileName.indexOf("ing") >= 0) {
				view.setSelectedOption(SequencialReaderFactory.Reader.ING);
			} else if (fileName.indexOf("cjmd") >= 0) {
				view.setSelectedOption(SequencialReaderFactory.Reader.CAJAMADRID);
			} else if (fileName.indexOf("ibcj") >= 0) {
				view.setSelectedOption(SequencialReaderFactory.Reader.IBERCAJA);
			}

		} else {
			view.setFilePath("");
		}
	}
}
