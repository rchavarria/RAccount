package es.rchavarria.raccount.frontend.expensesReport.gui;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.rchavarria.raccount.bussines.ServiceFacade;
import es.rchavarria.raccount.frontend.gui.view.GuiView;
import es.rchavarria.raccount.model.Account;

public class ExpensesReportController {

    private final static Logger log = LoggerFactory.getLogger(ExpensesReportController.class);

	private ExpensesReportView view;

	public ExpensesReportController() {
		view = new ExpensesReportView();

		load();
	}

	public GuiView getView() {
		return view;
	}

	private void load() {
		List<Account> accountList = null;
		try {
			accountList = new ServiceFacade().getAccountabelAccountList();
			
		} catch (Exception e) {
			log.info("Error getting account list", e);
			accountList = Collections.emptyList();
		}

		view.setAccounts(accountList);
//		view.setNMovements("50");
	}
}
