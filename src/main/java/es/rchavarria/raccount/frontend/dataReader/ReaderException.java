package es.rchavarria.raccount.frontend.dataReader;

public class ReaderException extends Exception {
	
    private static final long serialVersionUID = 4399074629465317288L;

    public ReaderException(String msg, Exception e){
		super(msg, e);
	}
}
