package es.rchavarria.raccount.frontend.script;

import java.util.List;

import es.rchavarria.raccount.bussines.ServiceFacade;
import es.rchavarria.raccount.frontend.dataCompleter.Completer;
import es.rchavarria.raccount.frontend.dataCompleter.gui.DoubleMovementGUICompleter;
import es.rchavarria.raccount.frontend.dataImporter.ImporterManager;
import es.rchavarria.raccount.frontend.dataMixer.AccountFromMixer;
import es.rchavarria.raccount.frontend.dataMixer.FieldMixer;
import es.rchavarria.raccount.frontend.dataReader.SequencialReader;
import es.rchavarria.raccount.frontend.dataReader.gui.SequencialReaderSelectorController;
import es.rchavarria.raccount.frontend.dataReader.gui.SequencialReaderSelectorView;
import es.rchavarria.raccount.frontend.gui.components.JResultDialog;
import es.rchavarria.raccount.model.DoubleMovement;

public class ImportFromBankReport {

    public static void main(final String[] args) throws Exception {

        SequencialReaderSelectorView view = new SequencialReaderSelectorView();
        SequencialReaderSelectorController controller = new SequencialReaderSelectorController(view);
        JResultDialog dlg = new JResultDialog(null, "Select a file to read", controller);
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);

        // ICompleter<DoubleMovement> completer = new DoubleMovementConsoleCompleter(System.out, System.in);
        Completer<DoubleMovement> completer = new DoubleMovementGUICompleter();
        FieldMixer<DoubleMovement> mixer = new AccountFromMixer();
        if (dlg.isOK()) {
            SequencialReader<DoubleMovement> seqReader = controller.getElement();
            ImporterManager<DoubleMovement> cm = new ImporterManager<DoubleMovement>(completer, seqReader, mixer);
            List<DoubleMovement> movs = cm.execute();
            System.out.println("importados " + movs.size() + " movs");
            for (DoubleMovement dm : movs) {
                new ServiceFacade().insertDoubleMovement(dm);
            }
        }
    }
}
