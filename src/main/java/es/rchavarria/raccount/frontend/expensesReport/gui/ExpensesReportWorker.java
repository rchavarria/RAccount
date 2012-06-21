package es.rchavarria.raccount.frontend.expensesReport.gui;

import java.awt.Container;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.rchavarria.raccount.bussines.ServiceFacade;
import es.rchavarria.raccount.frontend.dataCompleter.gui.EditDoubleMovementController;
import es.rchavarria.raccount.frontend.expensesReport.data.ResultFormData;
import es.rchavarria.raccount.model.Account;
import es.rchavarria.raccount.model.ExpensesByConcept;

public class ExpensesReportWorker extends SwingWorker<List<ExpensesByConcept>, Void> {

    private final static Logger log = LoggerFactory.getLogger(EditDoubleMovementController.class);

    private Account account;
    private Date dateFrom;
    private Date dateTo;
    private ExpensesReportView view;
    
    public ExpensesReportWorker(Account account, Date dateFrom, Date dateTo, ExpensesReportView view) {
        this.account = account;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.view = view;
    }

    @Override
    protected List<ExpensesByConcept> doInBackground() throws Exception {
        List<ExpensesByConcept> expenses = new ServiceFacade().getExpenses(account, dateFrom, dateTo);
        return expenses;
    }

    @Override
    protected void done() {
        try {
            List<ExpensesByConcept> expenses = get();
            
            for(ExpensesByConcept expense : expenses){
                log.info(expense.concept.getName() + " -> " + expense.expenses);
            }
            
            ExpensesReportResultController controller = new ExpensesReportResultController();
            ResultFormData data = new ResultFormData(account, dateFrom, dateTo, expenses);
            controller.load(expenses);
            
            JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, view);
            frame.setContentPane((Container) controller.getView());
            frame.pack();
            frame.setLocationRelativeTo(null);
            
        } catch (Exception e) {
            log.error("Error showing expenses report result", e);
        }
    }
}
