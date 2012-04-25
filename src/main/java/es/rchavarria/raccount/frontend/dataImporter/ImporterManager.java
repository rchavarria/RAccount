package es.rchavarria.raccount.frontend.dataImporter;

import java.util.ArrayList;
import java.util.List;

import es.rchavarria.raccount.frontend.dataCompleter.Completer;
import es.rchavarria.raccount.frontend.dataCompleter.CompleterException;
import es.rchavarria.raccount.frontend.dataMixer.FieldMixer;
import es.rchavarria.raccount.frontend.dataReader.ReaderException;
import es.rchavarria.raccount.frontend.dataReader.SequencialReader;

/**
 * Maneja un completador y un importador secuencial para importar y completar secuencialmente datos
 * 
 * @author RChavarria
 * @param <T>
 */
public class ImporterManager<T> {

    private SequencialReader<T> seqReader;
    private Completer<T> completer;
    private FieldMixer<T> preprocesor;

    public ImporterManager(final Completer<T> completer, final SequencialReader<T> seqReader,
            final FieldMixer<T> preprocesor) {
        this.completer = completer;
        this.seqReader = seqReader;
        this.preprocesor = preprocesor;
    }

    public List<T> execute() throws CompleterException, ReaderException {
        List<T> data = new ArrayList<T>();

        T element = null;
        T postCompleted = null;
        int count = 1;
        while ((element = seqReader.next()) != null) {
            T preCompleted = preprocesor.mix(element, postCompleted);
            T completed = completer.complete(preCompleted, count++);
            if (completed != null) {
                data.add(completed);
                postCompleted = preprocesor.mix(postCompleted, completed);
            }
        }

        return data;
    }
}
