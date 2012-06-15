package es.rchavarria.raccount.frontend.expensesReport.gui;

import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import es.rchavarria.raccount.model.Account;

public class ExpensesReportAcceptAction extends AbstractAction {

    private static final long serialVersionUID = -8713949185746365403L;
    private ExpensesReportView view;

    public ExpensesReportAcceptAction(ExpensesReportView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Account account = view.getAccount();
        Date dateFrom = view.getDateFrom();
        Date dateTo = view.getDateTo();
        
        if(dateFrom == null){
            showDateFromError();
            return;
        }
        
        if(dateTo == null){
            showDateToError();
            return;
        }
        
        if(dateTo.before(dateFrom)){
            showPeriodError();
            return;
        }
        
        ExpensesReportWorker worker = new ExpensesReportWorker(account, dateFrom, dateTo, view);
        worker.execute();
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(view, 
                                      msg, 
                                      "Error requesting expenses", 
                                      JOptionPane.ERROR_MESSAGE);
    }

    private void showDateFromError() {
        showError("Date 'from' can not be empty");
    }

    private void showDateToError() {
        showError("Date 'to' can not be empty");
    }
    
    private void showPeriodError() {
        showError("Date 'to' can not be before date 'from'");
    }

}
