package es.rchavarria.raccount.frontend.script;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;

import es.rchavarria.raccount.frontend.dataReader.gui.SequencialReaderSelectorView;
import es.rchavarria.raccount.frontend.gui.view.GuiView;

public class FrameToShowPanels extends JFrame {

    private static final long serialVersionUID = -7461007618940344959L;

    public static void main(final String[] args) {
		GuiView view = new SequencialReaderSelectorView();

		FrameToShowPanels frm = new FrameToShowPanels();
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.getContentPane().setLayout(new BorderLayout());
		frm.getContentPane().add((Component) view, BorderLayout.CENTER);
		frm.pack();
		frm.setVisible(true);
	}
}
