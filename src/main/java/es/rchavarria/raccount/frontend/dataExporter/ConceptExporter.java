package es.rchavarria.raccount.frontend.dataExporter;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import es.rchavarria.raccount.model.Concept;

public class ConceptExporter {

	private FileOutputStream fos;

	public ConceptExporter(FileOutputStream fos) {
		this.fos = fos;
	}

	public void export(List<Concept> concepts) throws ExportException {
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;

        try {
        	osw = new OutputStreamWriter(fos);
        	bw = new BufferedWriter(osw);

        	//cabecera
        	bw.write("idConcept;name;visible");
        	bw.newLine();
        	
        	for(Concept c : concepts){
        		String id = new Integer(-1).toString();
        		String name = c.getName();
        		String visible = c.isVisible() ? "VERDADERO" : "FALSO";
        		
        		bw.write(id + ";" + name + ";" + visible);
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
        }
	}

}
