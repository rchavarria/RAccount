package es.rchavarria.raccount.frontend.gui.components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import es.rchavarria.raccount.frontend.dataCompleter.gui.EditDoubleMovementController;
import es.rchavarria.raccount.frontend.gui.controller.DialogableController;

public class JResultDialog extends JDialog {

    private static final long serialVersionUID = 8553421724681437280L;

    private DialogableController<?> pnEdit;
	private boolean isOK;

	public static void main(final String[] args) {
		JResultDialog me = new JResultDialog(null, "test", new EditDoubleMovementController());
		me.setVisible(true);
	}

	public JResultDialog(final JFrame parent, final String title, final DialogableController<?> pnEdit) {
		super(parent, title);
		this.pnEdit = pnEdit;
		setModal(true);

		initComponents();
	}

	private void initComponents() {
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.add(pnEdit.getView(), BorderLayout.CENTER);

		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				btnOKActionListener(e);
			}
		});
		JPanel pnButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnButtons.add(btnOK);
		content.add(pnButtons, BorderLayout.PAGE_END);

		setContentPane(content);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		pack();
	}

	private void btnOKActionListener(final ActionEvent e) {
		isOK = pnEdit.areValuesOK();
		if (isOK) {
			dispose();
		}
	}

	public boolean isOK() {
		return isOK;
	}

}
