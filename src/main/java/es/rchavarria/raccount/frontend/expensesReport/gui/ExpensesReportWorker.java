package es.rchavarria.raccount.frontend.expensesReport.gui;

import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import es.rchavarria.raccount.bussines.ServiceFacade;
import es.rchavarria.raccount.model.Account;
import es.rchavarria.raccount.model.ExpensesByConcept;

public class ExpensesReportWorker extends SwingWorker<List<ExpensesByConcept>, Void> {

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
                System.out.println(expense.concept.getName() + " -> " + expense.expenses);
            }
            
            JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, view);
            frame.dispose();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
