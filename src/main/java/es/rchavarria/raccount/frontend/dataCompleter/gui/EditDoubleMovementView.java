package es.rchavarria.raccount.frontend.dataCompleter.gui;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import es.rchavarria.raccount.frontend.gui.renderer.AccountJListRenderer;
import es.rchavarria.raccount.frontend.gui.renderer.ConceptJListRenderer;
import es.rchavarria.raccount.frontend.gui.view.GuiView;
import es.rchavarria.raccount.model.Account;
import es.rchavarria.raccount.model.Concept;

public class EditDoubleMovementView extends JPanel implements GuiView {
    private static final long serialVersionUID = -8134504055497707320L;

    private JTextField txtDescription;
    private JTextField txtDate;
    private JTextField txtAmount;
    private JList lstConcept;
    private JList lstAccountFrom;
    private JList lstAccountTo;

    public EditDoubleMovementView() {
        super();

        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout("wrap 2"));

        add(new JLabel("Description:"));
        txtDescription = new JTextField(35);
        add(txtDescription);

        add(new JLabel("Date (dd/MM/yy):"));
        txtDate = new JTextField(10);
        add(txtDate);

        add(new JLabel("Amount:"));
        txtAmount = new JTextField(10);
        add(txtAmount);

        add(new JLabel("Concept:"));
        lstConcept = new JList();
        lstConcept.setCellRenderer(new ConceptJListRenderer());
        add(new JScrollPane(lstConcept));

        add(new JLabel("Account from:"));
        lstAccountFrom = new JList();
        lstAccountFrom.setCellRenderer(new AccountJListRenderer());
        add(new JScrollPane(lstAccountFrom));

        add(new JLabel("Account to:"));
        lstAccountTo = new JList();
        lstAccountTo.setCellRenderer(new AccountJListRenderer());
        add(new JScrollPane(lstAccountTo));
    }

    public void setDescription(final String description) {
        txtDescription.setText(description);
    }

    public void setMovementDate(final String strDate) {
        txtDate.setText(strDate);
    }

    public void setAmount(final String amount) {
        txtAmount.setText(amount);
    }

    public void setConcept(final Concept concept) {
        lstConcept.setSelectedValue(concept, true);
    }

    public void setAccountFrom(final Account accountFrom) {
        lstAccountFrom.setSelectedValue(accountFrom, true);
    }

    public void setAccountTo(final Account accountTo) {
        lstAccountTo.setSelectedValue(accountTo, true);
    }

    public String getDescription() {
        return txtDescription.getText();
    }

    public String getMovementDate() {
        return txtDate.getText();
    }

    public String getAmount() {
        return txtAmount.getText();
    }

    public Concept getConcept() {
        return (Concept) lstConcept.getSelectedValue();
    }

    public Account getAccountFrom() {
        return (Account) lstAccountFrom.getSelectedValue();
    }

    public Account getAccountTo() {
        return (Account) lstAccountTo.getSelectedValue();
    }

    public void setConcepts(final List<Concept> conceptList) {
        DefaultComboBoxModel model = new DefaultComboBoxModel(conceptList.toArray());
        lstConcept.setModel(model);
    }

    public void setAccounts(final List<Account> accountList) {
        lstAccountTo.setModel(new DefaultComboBoxModel(accountList.toArray()));
        lstAccountFrom.setModel(new DefaultComboBoxModel(accountList.toArray()));
    }
}
