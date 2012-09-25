package es.rchavarria.raccount.frontend.script;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import es.rchavarria.raccount.bussines.BussinessException;
import es.rchavarria.raccount.bussines.ServiceFacade;
import es.rchavarria.raccount.db.dao.DAOException;
import es.rchavarria.raccount.frontend.editor.EditException;
import es.rchavarria.raccount.frontend.editor.console.ConceptConsoleEditor;
import es.rchavarria.raccount.model.Concept;

public class ConceptEditorManager {

	private InputStreamReader isr;
	private BufferedReader br;

	public ConceptEditorManager(){
		isr = new InputStreamReader(System.in);
		br = new BufferedReader(isr);
	}
	
	public void startConceptEdit() throws DAOException, SQLException, IOException, ParseException, EditException, BussinessException{
		System.out.println("Retrieving concept list:");
		List<Concept> concepts = new ServiceFacade().getConceptList();
		for(int i = 0; i < concepts.size(); i++){
			Concept c = concepts.get(i);
			String msg = "["+(i+1)+"] " + c.getName();
			if(! c.isVisible())
				msg += " [Not visible]";
			System.out.println(msg);
		}
		System.out.println("Select a concept to edit (0 to create one): ");
		String strIn = br.readLine();
		if(strIn.length() == 0) return;
		
		int idx = NumberFormat.getIntegerInstance().parse(strIn).intValue() - 1;
		Concept c = (idx >= 0 && idx < concepts.size()) 
				? concepts.get(idx) 
				: new Concept();

		c = new ConceptConsoleEditor().edit(c);

		if(c.getIdConcept() == 0){
			new ServiceFacade().insertConcept(c);
			System.out.println("Concept '"+c.getName()+"' inserted");
		} else {
			new ServiceFacade().updateConcept(c);
			System.out.println("Concept '"+c.getName()+"' updated");
		}
	}
	
	public static void main(String[] args) throws Exception {
		new ConceptEditorManager().startConceptEdit();
	}
}
