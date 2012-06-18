package es.rchavarria.raccount.frontend.listMovements.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.rchavarria.raccount.bussines.ServiceFacade;
import es.rchavarria.raccount.frontend.dataCompleter.gui.EditDoubleMovementController;
import es.rchavarria.raccount.model.Account;
import es.rchavarria.raccount.model.Movement;

public class BtnListNMovementsAction implements ActionListener {

    private final static Logger log = LoggerFactory.getLogger(EditDoubleMovementController.class);

	private ListLastNMovementsView view;
	private NumberFormat nf;

	public BtnListNMovementsAction(final ListLastNMovementsView view) {
		this.view = view;
		this.nf = NumberFormat.getIntegerInstance();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		Account account = this.view.getAccount();
		String txtNMovements = this.view.getNMovements();
		Integer nMovements = null;
		try {
			nMovements = this.nf.parse(txtNMovements).intValue();
		} catch (Exception ex) {
			nMovements = 50;
		}

		List<Movement> movs = null;
		try {
			movs = new ServiceFacade().listLastNMovements(account, nMovements);
			
		} catch (Exception e1) {
			log.info("Error getting movements", e1);
			movs = Collections.emptyList();
		}
		this.view.setListMovements(movs);
	}

}
