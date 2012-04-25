package es.rchavarria.raccount.frontend.editor.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import es.rchavarria.raccount.frontend.editor.EditException;
import es.rchavarria.raccount.frontend.editor.Editor;
import es.rchavarria.raccount.model.Concept;

public class ConceptConsoleEditor implements Editor<Concept> {

	private InputStreamReader isr;
	private BufferedReader br;

	public ConceptConsoleEditor(){
		isr = new InputStreamReader(System.in);
		br = new BufferedReader(isr);
	}
	
	public Concept edit(Concept concept) throws EditException {
		System.out.println("Editing concept '"+concept.getName()+"' ("+concept.getIdConcept()+")");
		
		try {
			System.out.println("Enter a new name: ["+concept.getName()+"]");
			String strIn = br.readLine();
			if(strIn.length() > 0)
				concept.setName(strIn);
			
			String strVisible = concept.isVisible() ? "Y" : "N";
			System.out.println("The concept will be visible (Y/N)? ["+strVisible+"]");
			strIn = br.readLine();
			if(strIn.length() > 0)
				concept.setVisible("Y".equals(strIn) || "y".equals(strIn));
			
		} catch (IOException e) {
			String msg = "Error reading from input";
			throw new EditException(msg, e);
		}
		
		return concept;
	}
}
