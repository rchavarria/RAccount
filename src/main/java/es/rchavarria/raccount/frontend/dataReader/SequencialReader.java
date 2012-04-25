package es.rchavarria.raccount.frontend.dataReader;


public interface SequencialReader<T> {
	
	public T next() throws ReaderException;
}
