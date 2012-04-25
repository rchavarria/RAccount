package es.rchavarria.raccount.frontend.listMovements.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import es.rchavarria.raccount.frontend.gui.models.MovementListTableModel;
import es.rchavarria.raccount.frontend.gui.renderer.AccountJListRenderer;
import es.rchavarria.raccount.frontend.gui.renderer.CurrencyTableCellRenderer;
import es.rchavarria.raccount.frontend.gui.renderer.DateTableCellRenderer;
import es.rchavarria.raccount.frontend.gui.view.GuiView;
import es.rchavarria.raccount.model.Account;
import es.rchavarria.raccount.model.Movement;

public class ListLastNMovementsView extends JPanel implements GuiView {
    private static final long serialVersionUID = 3222142278286804580L;
    private JComboBox cmbAccounts;
    private JTextField txtNMovements;
    private JTable tblMovementList;
    private JButton btnList;

    public ListLastNMovementsView() {
        super();

        initComponents();
    }

    private void initComponents() {
        JPanel pnConfiguration = new JPanel(new GridLayout(0, 2));

        pnConfiguration.add(new JLabel("Select an account:"));
        cmbAccounts = new JComboBox();
        cmbAccounts.setRenderer(new AccountJListRenderer());
        pnConfiguration.add(cmbAccounts);

        pnConfiguration.add(new JLabel("Movements nº:"));
        txtNMovements = new JTextField(10);
        pnConfiguration.add(txtNMovements);

        pnConfiguration.add(new JLabel());
        btnList = new JButton("List");
        btnList.addActionListener(new BtnListNMovementsAction(this));
        pnConfiguration.add(btnList);

        tblMovementList = new JTable();
        tblMovementList.setDefaultRenderer(Date.class, new DateTableCellRenderer());
        tblMovementList.setDefaultRenderer(Double.class, new CurrencyTableCellRenderer());
        JPanel pnTable = new JPanel(new BorderLayout());
        pnTable.add(new JScrollPane(tblMovementList), BorderLayout.CENTER);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(pnConfiguration);
        add(pnTable);
    }

    public void setAccounts(final List<Account> accountList) {
        DefaultComboBoxModel model = new DefaultComboBoxModel(accountList.toArray());
        cmbAccounts.setModel(model);
    }

    public void setNMovements(final String strNMovements) {
        txtNMovements.setText(strNMovements);
    }

    public Account getAccount() {
        return (Account) cmbAccounts.getSelectedItem();
    }

    public String getNMovements() {
        return txtNMovements.getText();
    }

    public void setListMovements(final List<Movement> movs) {
        MovementListTableModel model = new MovementListTableModel(movs);
        tblMovementList.setModel(model);
    }
}
