package es.rchavarria.raccount.frontend.expensesReport.gui;

import java.awt.Component;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

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
        setLayout(new MigLayout());
        
        add(getTitleLabel(), "wrap");
        add(getComboAccounts(), "wrap");
        add(getDatePickerFrom());
        add(getDatePickerTo(), "wrap");
        add(getBtnOk());
    }

    private Component getTitleLabel() {
        return new JXLabel("Expenses report");
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
        return new JXButton("OK");
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
}
