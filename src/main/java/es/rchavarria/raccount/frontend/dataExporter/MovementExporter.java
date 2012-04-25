package es.rchavarria.raccount.frontend.dataExporter;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.db.isession.DBSession;
import es.rchavarria.raccount.model.Movement;

public class MovementExporter {

	private FileOutputStream fos;
	private SimpleDateFormat sdf;
	private NumberFormat nf;
	
	public MovementExporter(FileOutputStream fos) {
		this.fos = fos;
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		nf = NumberFormat.getCurrencyInstance();
	}

	public void export(List<Movement> original) throws ExportException {
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		Session session = null;
		
        try {
        	session = DBSession.getSession();
        	osw = new OutputStreamWriter(fos);
        	bw = new BufferedWriter(osw);

        	//cabecera
        	bw.write("idMovimiento;Fecha;Cantidad;Concepto;Notas;Cuenta;Saldo_En_Cuenta");
        	bw.newLine();
        	
        	for(Movement m : original){
        		String id = new Integer(-1).toString();
        		String date = sdf.format(m.getMovementDate());
        		String amount = nf.format(m.getAmount());
        		String concept = new Long(m.getConcept().getIdConcept()).toString();
        		String description = m.getDescription();
        		String account = new Long(m.getAccount().getIdAccount()).toString();
        		String finalBalance = nf.format(m.getFinalBalance());
        		
        		bw.write(id + ";" + 
        				date + ";" + 
        				amount + ";" + 
        				concept + ";" + 
        				description + ";" + 
        				account + ";" + 
        				finalBalance);
        		bw.newLine();
        	}
            
        } catch (Exception e) {
            throw new ExportException("Error reading from input stream: " + fos.toString(), e);

        } finally {
        	if(bw != null){
        		try { bw.close(); } 
        		catch (Throwable e1) { }
        	}
        	if(osw != null){
        		try { osw.close(); } 
        		catch (Throwable e1) { }
        	}
        	if(session != null) session.close();
        }
	}

}
