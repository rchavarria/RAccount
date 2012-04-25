package es.rchavarria.raccount.frontend.dataCompleter;

public class CompleterException extends Exception {

    private static final long serialVersionUID = 5893269816846387372L;

    public CompleterException(String msg, Exception e) {
		super(msg, e);
	}

}
