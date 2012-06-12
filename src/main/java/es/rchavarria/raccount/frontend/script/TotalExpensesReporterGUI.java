package es.rchavarria.raccount.frontend.script;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel;

import es.rchavarria.raccount.frontend.listMovements.gui.ListLastNMovementsController;

public class TotalExpensesReporterGUI {
    
    private JFrame buildGUI() {
        ListLastNMovementsController controller = new ListLastNMovementsController();
        JPanel view = (JPanel) controller.getView();
        
        JFrame frm = new JFrame("List last N movements");
        frm.setContentPane(view);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.pack();
        frm.setLocationRelativeTo(null);
        
        return frm;
    }

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception {
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            try {
	                UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
	            } catch (Exception e) {
	                System.out.println("Substance look and feel failed to initialize");
	            }
	            
	            new TotalExpensesReporterGUI().buildGUI().setVisible(true);
	        }
	    });
	}
}
