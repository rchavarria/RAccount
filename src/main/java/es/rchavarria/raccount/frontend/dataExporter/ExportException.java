package es.rchavarria.raccount.frontend.dataExporter;

public class ExportException extends Exception {

    private static final long serialVersionUID = -5841639269075832181L;

    public ExportException(String msg, Exception e) {
		super(msg, e);
	}

}
