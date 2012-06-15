package es.rchavarria.raccount.frontend.expensesReport.gui;

import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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
        
        ExpensesReportWorker worker = new ExpensesReportWorker(account, dateFrom, dateTo);
        worker.execute();
    }

}
