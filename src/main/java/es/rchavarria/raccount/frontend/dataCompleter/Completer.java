package es.rchavarria.raccount.frontend.dataCompleter;

public interface Completer<T> {
	public T complete(T element, int count) throws CompleterException;
}
