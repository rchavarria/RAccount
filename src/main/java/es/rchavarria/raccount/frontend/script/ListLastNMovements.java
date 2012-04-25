package es.rchavarria.raccount.frontend.script;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import es.rchavarria.raccount.bussines.BussinessException;
import es.rchavarria.raccount.db.dao.DAOException;
import es.rchavarria.raccount.frontend.listMovements.gui.ListLastNMovementsController;

public class ListLastNMovements {

	/**
	 * @param args
	 * @throws BussinessException
	 * @throws DAOException
	 * @throws SQLException
	 */
	public static void main(final String[] args) throws Exception {
		ListLastNMovementsController controller = new ListLastNMovementsController();
		JPanel view = (JPanel) controller.getView();

		JFrame frm = new JFrame("List last N movements");
		frm.setContentPane(view);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.pack();
		frm.setLocationRelativeTo(null);
		frm.setVisible(true);
	}

}
