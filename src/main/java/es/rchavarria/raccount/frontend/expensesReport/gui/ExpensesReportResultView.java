package es.rchavarria.raccount.frontend.expensesReport.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;

import es.rchavarria.raccount.frontend.expensesReport.models.ExpensesReportResultTableModel;
import es.rchavarria.raccount.frontend.gui.view.GuiView;

public class ExpensesReportResultView extends JXPanel implements GuiView {
    
    private static final long serialVersionUID = 6989676921321246246L;

    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font DATA_FONT = new Font("Arial", Font.BOLD, 14);
    
    private JXTable table;
    private JXLabel lblTitle;
    private JXLabel lblAccount;
    private JXLabel lblDateFrom;
    private JXLabel lblDateTo;

    public ExpensesReportResultView() {
        super();
        
        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout(
                "",
                "[][]",
                ""));
        
        add(getTitle(), "span 2, wrap");
        
        add(getAccountLabel());
        add(getAccountName(), "wrap");
        
        add(getDateFromLabel());
        add(getDateFromName(), "wrap");
        
        add(getDateToLabel());
        add(getDateToName(), "wrap");
        
        add(getTable(), "grow, span 2");
    }

    private Component getTitle() {
        lblTitle = new JXLabel("Expenses report");
        lblTitle.setFont(TITLE_FONT);
        return lblTitle;
    }
    
    private Component getAccountLabel() {
        URL iconURL = getClass().getResource("/images/icons/euro.png");
        JXLabel lbl = new JXLabel("Account: ");
        lbl.setIcon(new ImageIcon(iconURL));
        return lbl;
    }
    
    private Component getAccountName() {
        lblAccount = new JXLabel("account");
        lblAccount.setFont(DATA_FONT);
        return lblAccount;
    }
    
    private Component getDateFromLabel() {
        URL iconURL = getClass().getResource("/images/icons/calendar.png");
        JXLabel lbl = new JXLabel("Date to: ");
        lbl.setIcon(new ImageIcon(iconURL));
        return lbl;
    }
    
    private Component getDateFromName() {
        lblDateFrom = new JXLabel("date from");
        lblDateFrom.setFont(DATA_FONT);
        return lblDateFrom;
    }

    private Component getDateToLabel() {
        URL iconURL = getClass().getResource("/images/icons/calendar.png");
        JXLabel lbl = new JXLabel("Date to: ");
        lbl.setIcon(new ImageIcon(iconURL));
        return lbl;
    }
    
    private Component getDateToName() {
        lblDateTo = new JXLabel("date to");
        lblDateTo.setFont(DATA_FONT);
        return lblDateTo;
    }
    
    private Component getTable() {
        table = new JXTable();
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(350, 400));
        return scroll;
    }

    public void setTableModel(ExpensesReportResultTableModel model) {
        table.setModel(model);
    }
    
    public void setAccountName(String name) {
        lblAccount.setText(name);
    }
    
    public void setDateFromName(String name) {
        lblDateFrom.setText(name);
    }
    
    public void setDateToName(String name) {
        lblDateTo.setText(name);
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
