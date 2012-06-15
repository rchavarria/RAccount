package es.rchavarria.raccount.frontend.expensesReport.gui;

import java.util.Date;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import es.rchavarria.raccount.bussines.ServiceFacade;
import es.rchavarria.raccount.model.Account;
import es.rchavarria.raccount.model.ExpensesByConcept;

public class ExpensesReportWorker extends SwingWorker<List<ExpensesByConcept>, Void> {

    private Account account;
    private Date dateFrom;
    private Date dateTo;
    
    public ExpensesReportWorker(Account account, Date dateFrom, Date dateTo) {
        this.account = account;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    protected List<ExpensesByConcept> doInBackground() throws Exception {
        List<ExpensesByConcept> expenses = new ServiceFacade().getExpenses(account, dateFrom, dateTo);
        return expenses;
    }

    @Override
    protected void done() {
        try {
            List<ExpensesByConcept> result = get();
            
            for(ExpensesByConcept expense : result){
                System.out.println(expense.concept.getName() + " -> " + expense.expenses);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
