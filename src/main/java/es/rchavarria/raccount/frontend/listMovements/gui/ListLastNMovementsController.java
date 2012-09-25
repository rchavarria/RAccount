package es.rchavarria.raccount.frontend.listMovements.gui;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.rchavarria.raccount.bussines.ServiceFacade;
import es.rchavarria.raccount.frontend.dataCompleter.gui.EditDoubleMovementController;
import es.rchavarria.raccount.frontend.gui.view.GuiView;
import es.rchavarria.raccount.model.Account;

public class ListLastNMovementsController {

    private final static Logger log = LoggerFactory.getLogger(EditDoubleMovementController.class);

	private ListLastNMovementsView view;

	public ListLastNMovementsController() {
		this.view = new ListLastNMovementsView();

		load();
	}

	public GuiView getView() {
		return this.view;
	}

	private void load() {
		List<Account> accountList = null;
		try {
			accountList = new ServiceFacade().getAccountList();
			
		} catch (Exception e) {
			log.info("Error getting account list", e);
			accountList = Collections.emptyList();
		}

		this.view.setAccounts(accountList);
		this.view.setNMovements("50");
	}
}
