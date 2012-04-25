package es.rchavarria.raccount.frontend.editor;

public class EditException extends Exception {

    private static final long serialVersionUID = -3539165252159885145L;

    public EditException(String msg, Exception e){
		super(msg, e);
	}
}
