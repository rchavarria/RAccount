package es.rchavarria.raccount.frontend.expensesReport.gui;

import java.util.Collections;
import java.util.List;

import es.rchavarria.raccount.bussines.ServiceFacade;
import es.rchavarria.raccount.frontend.gui.view.GuiView;
import es.rchavarria.raccount.model.Account;

public class ExpensesReportController {

	private ExpensesReportView view;

	public ExpensesReportController() {
		this.view = new ExpensesReportView();

		load();
	}

	public GuiView getView() {
		return this.view;
	}

	private void load() {
		List<Account> accountList = null;
		try {
			accountList = new ServiceFacade().getAccountabelAccountList();
			
		} catch (Exception e) {
			e.printStackTrace();
			accountList = Collections.emptyList();
		}

		this.view.setAccounts(accountList);
//		this.view.setNMovements("50");
	}
}
