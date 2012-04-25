package es.rchavarria.raccount.frontend.dataCompleter.gui;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;

import es.rchavarria.raccount.bussines.ServiceFacade;
import es.rchavarria.raccount.frontend.gui.controller.DialogableController;
import es.rchavarria.raccount.model.Account;
import es.rchavarria.raccount.model.Concept;
import es.rchavarria.raccount.model.DoubleMovement;

public class EditDoubleMovementController implements DialogableController<DoubleMovement> {

    private DoubleMovement dm;
    private EditDoubleMovementView view;
    private SimpleDateFormat sdf;
    private NumberFormat nf;

    public EditDoubleMovementController() {
        this(new DoubleMovement(), new EditDoubleMovementView());
    }

    public EditDoubleMovementController(final DoubleMovement dm, final EditDoubleMovementView view) {
        this.dm = dm;
        this.view = view;
        sdf = new SimpleDateFormat("dd/MM/yy");
        nf = NumberFormat.getInstance();

        load();
    }

    @Override
    public JPanel getView() {
        return view;
    }

    @Override
    public DoubleMovement getElement() {
        fill();

        return dm;
    }

    private void fill() {
        dm.setDescription(view.getDescription());
        Date mvDate = null;
        try {
            mvDate = sdf.parse(view.getMovementDate());
        } catch (ParseException e) {
            mvDate = new Date();
        }
        dm.setMovementDate(mvDate);
        try {
            dm.setAmount(nf.parse(view.getAmount()).floatValue());
            dm.setConcept(view.getConcept());
            dm.setAccount(view.getAccountFrom());
            dm.setAccountTo(view.getAccountTo());
        } catch (Exception e) {
            // es labor de areVAluesOK de validar los datos
        }
    }

    @Override
    public boolean areValuesOK() {
        try {
            sdf.parse(view.getMovementDate());
        } catch (ParseException e) {
            System.out.println("Date format is wrong: " + view.getMovementDate());
            return false;
        }
        try {
            nf.parse(view.getAmount());
        } catch (Exception e) {
            System.out.println("Some number is wrong");
            return false;
        }

        return view.getConcept() != null && view.getAccountFrom() != null && view.getAccountTo() != null;
    }

    private void load() {
        List<Concept> conceptList = null;
        List<Account> accountList = null;
        try {
            conceptList = new ServiceFacade().getVisibleConceptList();
            accountList = new ServiceFacade().getAccountabelAccountList();
        } catch (Exception e) {
            e.printStackTrace();
            conceptList = Collections.emptyList();
            accountList = Collections.emptyList();
        }

        view.setDescription(dm.getDescription());
        String strDate = dm.getMovementDate() != null ? sdf.format(dm.getMovementDate()) : "";
        view.setMovementDate(strDate);
        view.setAmount(nf.format(dm.getAmount()));
        view.setConcepts(conceptList);
        if (dm.getConcept() != null) {
            view.setConcept(dm.getConcept());
        }
        view.setAccounts(accountList);
        if (dm.getAccount() != null) {
            view.setAccountFrom(dm.getAccount());
        }
        if (dm.getAccountTo() != null) {
            view.setAccountTo(dm.getAccountTo());
        }
    }

    public static void main(final String[] args) {
        EditDoubleMovementController controller = new EditDoubleMovementController();
        JDialog dlg = new JDialog();
        dlg.setContentPane(controller.getView());
        dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dlg.pack();
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);
    }
}
