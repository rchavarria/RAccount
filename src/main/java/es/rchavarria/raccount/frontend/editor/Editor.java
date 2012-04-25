package es.rchavarria.raccount.frontend.editor;

public interface Editor<T> {
	public T edit(T element) throws EditException;
}
