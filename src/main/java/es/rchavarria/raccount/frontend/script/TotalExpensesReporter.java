package es.rchavarria.raccount.frontend.script;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import es.rchavarria.raccount.bussines.BussinessException;
import es.rchavarria.raccount.bussines.ServiceFacade;
import es.rchavarria.raccount.db.dao.DAOException;
import es.rchavarria.raccount.model.Concept;

public class TotalExpensesReporter {

	private InputStreamReader isr;
	private BufferedReader br;

	public TotalExpensesReporter(){
		isr = new InputStreamReader(System.in);
		br = new BufferedReader(isr);
	}
	
	public void startReporting() throws BussinessException, DAOException, SQLException, IOException {
		System.out.println("Select a month (1 to 12): ");
		String strMonth = br.readLine();
        int month = new GregorianCalendar().get(Calendar.MONTH);
	    try{
	        month = NumberFormat.getIntegerInstance().parse(strMonth).intValue();
	    } catch(Throwable t) {}
	    
	    System.out.println("Calculating expenses during month: " + month);
	    Map<Concept, Double> expenses = new ServiceFacade().getMonthTotalExpenses(month);
	    for(Concept c : expenses.keySet()){
	        Double expense = expenses.get(c);
	        System.out.println("Concept: " + c.getName() + " | " + expense);
	    }
	}
	
	public static void main(String[] args) throws Exception {
		new TotalExpensesReporter().startReporting();
	}
}
