package es.rchavarria.raccount.frontend.expensesReport.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;

import es.rchavarria.raccount.frontend.expensesReport.models.ExpensesReportResultTableModel;
import es.rchavarria.raccount.frontend.gui.view.GuiView;

public class ExpensesReportResultView extends JXPanel implements GuiView {

    private static final long serialVersionUID = 6989676921321246246L;
    
    private JXTable table;

    public ExpensesReportResultView() {
        super();
        
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        add(getTable(), BorderLayout.CENTER);
    }

    private Component getTable() {
        table = new JXTable();
        return new JScrollPane(table);
    }

    public void setTableModel(ExpensesReportResultTableModel model) {
        table.setModel(model);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setContentPane(new ExpensesReportResultView());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.pack();
        f.setVisible(true);
    }
}
