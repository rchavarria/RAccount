package es.rchavarria.raccount.frontend.expensesReport.data;

import java.util.Date;
import java.util.List;

import es.rchavarria.raccount.model.Account;
import es.rchavarria.raccount.model.ExpensesByConcept;

public class ResultFormData {
    
    public final Account account;
    public final Date dateFrom;
    public final Date dateTo;
    public final List<ExpensesByConcept> expenses;
    
    public ResultFormData(Account account, Date dateFrom, Date dateTo, List<ExpensesByConcept> expenses) {
        this.account = account;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.expenses = expenses;
    }
}
