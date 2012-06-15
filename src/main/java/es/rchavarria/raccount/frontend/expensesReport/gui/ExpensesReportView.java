package es.rchavarria.raccount.frontend.expensesReport.gui;

import java.awt.Component;
import java.awt.Font;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXComboBox;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;

import es.rchavarria.raccount.frontend.gui.renderer.AccountJListRenderer;
import es.rchavarria.raccount.frontend.gui.view.GuiView;
import es.rchavarria.raccount.model.Account;

public class ExpensesReportView extends JXPanel implements GuiView {

    private static final long serialVersionUID = -3310185680615734465L;
    
    private JComboBox cmbAccounts;
    private JXDatePicker dateFrom;
    private JXDatePicker dateTo;

    public ExpensesReportView() {
        super();
        
        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout(
                "",
                "[][]",
                ""));
        
        add(getTitleLabel(), "span 2, wrap");
        add(getComboAccounts(), "span 2, wrap");
        add(getDatePickerFrom(), "align center");
        add(getDatePickerTo(), "align center, wrap");
        add(new JXLabel());
        add(getBtnOk(), "align right");
    }

    private Component getTitleLabel() {
        JXLabel lbl = new JXLabel("Expenses report");
        lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        return lbl;
    }
    
    private Component getComboAccounts() {
        cmbAccounts = new JXComboBox();
        cmbAccounts.setRenderer(new AccountJListRenderer());
        return cmbAccounts;
    }
    
    private Component getDatePickerFrom() {
        dateFrom = new JXDatePicker();
        return dateFrom;
    }
    
    private Component getDatePickerTo() {
        dateTo = new JXDatePicker();
        return dateTo;
    }
    
    private Component getBtnOk() {
        JXButton btnOk = new JXButton("OK");
        btnOk.addActionListener(new ExpensesReportAcceptAction(this));
        return btnOk;
    }

    public void setAccounts(final List<Account> accountList) {
        DefaultComboBoxModel model = new DefaultComboBoxModel(accountList.toArray());
        cmbAccounts.setModel(model);
    }

    public Account getAccount() {
        return (Account) cmbAccounts.getSelectedItem();
    }

    public Date getDateFrom() {
        return dateFrom.getDate();
    }

    public Date getDateTo() {
        return dateTo.getDate();
    }
    
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setContentPane(new ExpensesReportView());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.pack();
        f.setVisible(true);
    }
}
