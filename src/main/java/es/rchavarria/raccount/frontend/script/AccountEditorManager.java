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
import es.rchavarria.raccount.frontend.editor.console.AccountConsoleEditor;
import es.rchavarria.raccount.model.Account;

public class AccountEditorManager {

	private InputStreamReader isr;
	private BufferedReader br;

	public AccountEditorManager(){
		isr = new InputStreamReader(System.in);
		br = new BufferedReader(isr);
	}
	
	public void startConceptEdit() throws DAOException, SQLException, IOException, ParseException, EditException, BussinessException{
		System.out.println("Retrieving account list:");
		List<Account> accounts = new ServiceFacade().getAccountabelAccountList();
		for(int i = 0; i < accounts.size(); i++){
			Account c = accounts.get(i);
			String msg = "["+(i+1)+"] " + c.getName();
			if(! c.isAccountable())
				msg += " [Not Accountable]";
			System.out.println(msg);
		}
		System.out.println("Select an account to edit (0 to create one): ");
		String strIn = br.readLine();
		if(strIn.length() == 0) return;
		
		int idx = NumberFormat.getIntegerInstance().parse(strIn).intValue() - 1;
		Account a = (idx >= 0 && idx < accounts.size()) 
				? accounts.get(idx) 
				: new Account();
		
		a = new AccountConsoleEditor().edit(a);
		
		if(a.getIdAccount() == 0){
			new ServiceFacade().insertAccount(a);
			System.out.println("Account '"+a.getName()+"' inserted");
		} else {
			new ServiceFacade().updateAccount(a);
			System.out.println("Account '"+a.getName()+"' updated");
		}
	}
	
	public static void main(String[] args) throws Exception {
		new AccountEditorManager().startConceptEdit();
	}
}
