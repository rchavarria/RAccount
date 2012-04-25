package es.rchavarria.raccount.frontend.dataCompleter.gui;

import es.rchavarria.raccount.frontend.dataCompleter.CompleterException;
import es.rchavarria.raccount.frontend.dataCompleter.Completer;
import es.rchavarria.raccount.frontend.gui.components.JResultDialog;
import es.rchavarria.raccount.model.DoubleMovement;

/**
 * Completa movimientos mostrando un dialogo al usuario
 * 
 * @author RChavarria
 */
public class DoubleMovementGUICompleter implements Completer<DoubleMovement> {

    @Override
    public DoubleMovement complete(final DoubleMovement element, final int count) throws CompleterException {
        EditDoubleMovementController controller = new EditDoubleMovementController(element,
                new EditDoubleMovementView());
        JResultDialog dlg = new JResultDialog(null, "Edit movement", controller);
        dlg.setLocationRelativeTo(null);
        dlg.setModal(true);
        dlg.setVisible(true);

        return dlg.isOK() ? controller.getElement() : null;
    }
}
