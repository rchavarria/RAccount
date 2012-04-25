package es.rchavarria.raccount.frontend.script;

import java.io.IOException;
import java.sql.SQLException;

import es.rchavarria.raccount.bussines.BussinessException;
import es.rchavarria.raccount.bussines.ServiceFacade;
import es.rchavarria.raccount.frontend.dataCompleter.CompleterException;
import es.rchavarria.raccount.frontend.dataCompleter.gui.DoubleMovementGUICompleter;
import es.rchavarria.raccount.model.DoubleMovement;

public class MovementCompleterManager {

	/**
	 * @param args
	 */
	public static void main(final String[] args) throws Exception {
		new MovementCompleterManager().startCompletion();
	}

	public void startCompletion() throws CompleterException, BussinessException, SQLException, IOException {
		System.out.println("You are going to create a new movement");

		// DoubleMovement dm = new DoubleMovementConsoleCompleter(System.out, System.in).complete(new DoubleMovement(),
		// 0);
		DoubleMovement dm = new DoubleMovementGUICompleter().complete(new DoubleMovement(), 0);
		if (dm != null) {
			new ServiceFacade().insertDoubleMovement(dm);
			System.out.println("Movement inserted");
		}
	}
}
