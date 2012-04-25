package es.rchavarria.raccount.bussines;

public class BussinessException extends Exception {

    private static final long serialVersionUID = -2645082843835507014L;

    public BussinessException(String msg, Exception e) {
		super(msg, e);
	}

}
