package es.rchavarria.raccount.frontend.dataReader.gui;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import es.rchavarria.raccount.frontend.dataReader.SequencialReaderFactory;
import es.rchavarria.raccount.frontend.dataReader.SequencialReaderFactory.Reader;
import es.rchavarria.raccount.frontend.gui.view.GuiView;

public class SequencialReaderSelectorView extends JPanel implements GuiView {

    private static final long serialVersionUID = -6785603767290171793L;

    private JTextField txtFilePath;
	private JButton btnSelect;
	private JRadioButton rbtnIng;
	private JRadioButton rbtnCajamadrid;
	private JRadioButton rbtnIbercaja;

	public SequencialReaderSelectorView() {
		super();

		initComponents();
	}

	private void initComponents() {
		txtFilePath = new JTextField(25);
		txtFilePath.setEditable(false);
		btnSelect = new JButton("Select");
		btnSelect.addActionListener(new BtnSelectPressedAction(this));

		JPanel pnFileChooser = new JPanel();
		pnFileChooser.setBorder(BorderFactory.createTitledBorder("Choose a file from which read the movements"));
		pnFileChooser.add(txtFilePath);
		pnFileChooser.add(btnSelect);

		rbtnIng = new JRadioButton("Ing Direct");
		rbtnIng.setSelected(true);
		rbtnCajamadrid = new JRadioButton("Cajamadrid");
		rbtnIbercaja = new JRadioButton("Ibercaja");
		ButtonGroup btngrp = new ButtonGroup();
		btngrp.add(rbtnIng);
		btngrp.add(rbtnCajamadrid);
		btngrp.add(rbtnIbercaja);

		JPanel pnOptions = new JPanel();
		pnOptions.setLayout(new GridLayout(0, 1));
		pnOptions.setBorder(BorderFactory.createTitledBorder("What bank are the movements from?"));
		pnOptions.add(rbtnIng);
		pnOptions.add(rbtnCajamadrid);
		pnOptions.add(rbtnIbercaja);

		setLayout(new GridLayout(0, 1));
		add(pnFileChooser);
		add(pnOptions);
	}

	public void setSelectedOption(final SequencialReaderFactory.Reader options) {
		switch (options) {
		case ING:
			rbtnIng.setSelected(true);
			break;
		case CAJAMADRID:
			rbtnCajamadrid.setSelected(true);
			break;
		case IBERCAJA:
			rbtnIbercaja.setSelected(true);
		}
	}

	public void setFilePath(final String fileName) {
		txtFilePath.setText(fileName);
	}

	public String getFilePath() {
		return txtFilePath.getText();
	}

	public Reader getReader() {
		if (rbtnIng.isSelected()) {
			return Reader.ING;
		} else if (rbtnCajamadrid.isSelected()) {
			return Reader.CAJAMADRID;
		}
		// else if(rbtnIbercaja.isSelected())
		return Reader.IBERCAJA;
	}
}
