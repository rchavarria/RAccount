package es.rchavarria.raccount.frontend.expensesReport.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;

import es.rchavarria.raccount.frontend.expensesReport.models.ExpensesReportResultTableModel;
import es.rchavarria.raccount.frontend.gui.view.GuiView;

public class ExpensesReportResultView extends JXPanel implements GuiView {

    private static final long serialVersionUID = 6989676921321246246L;
    
    private JXTable table;
    private JXLabel lblTitle;

    public ExpensesReportResultView() {
        super();
        
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        add(getTitle(), BorderLayout.NORTH);
        add(getTable(), BorderLayout.CENTER);
    }

    private Component getTitle() {
        lblTitle = new JXLabel("no title yet");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        return lblTitle;
    }

    private Component getTable() {
        table = new JXTable();
        return new JScrollPane(table);
    }

    public void setTableModel(ExpensesReportResultTableModel model) {
        table.setModel(model);
    }
    
    public void setTitle(String title) {
        lblTitle.setText(title);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setContentPane(new ExpensesReportResultView());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
