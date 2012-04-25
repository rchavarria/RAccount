package es.rchavarria.raccount.frontend.dataImporter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import es.rchavarria.raccount.model.Concept;

/**
 * Importa un fichero con el siguiente formato a objetos Concept:
 * 
 * idConcepto;Concepto;Visible
 * 43;abono piscina;FALSO
 * 44;abono.renfe;VERDADERO
 *
 * @author rchavarria
 */
public class ConceptImporter {
	private InputStream is;
    
    public ConceptImporter(InputStream is){
        this.is = is;
    }

    public List<Concept> doImport() throws ImportException{
        List<Concept> concepts = new ArrayList<Concept>();
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
        	isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            String line = br.readLine(); // primera fila == cabecera
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(";");
                Concept c = new Concept();
                c.setName(tokens[1]);
                c.setVisible("VERDADERO".equals(tokens[2]) ? true : false);
                concepts.add(c);
            }
            
            
        } catch (Exception e) {
            throw new ImportException("Error reading from input stream: " + is.toString(), e);

        } finally {
        	if(br != null){
        		try { br.close(); } 
        		catch (Throwable e1) { }
        	}
        	if(isr != null){
        		try { isr.close(); } 
        		catch (Throwable e1) { }
        	}
        	
        }

        return concepts;
    }
}
