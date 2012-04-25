package es.rchavarria.raccount.frontend.editor.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;

import es.rchavarria.raccount.frontend.editor.EditException;
import es.rchavarria.raccount.frontend.editor.Editor;
import es.rchavarria.raccount.model.Account;

public class AccountConsoleEditor implements Editor<Account>{

	private InputStreamReader isr;
	private BufferedReader br;

	public AccountConsoleEditor(){
		isr = new InputStreamReader(System.in);
		br = new BufferedReader(isr);
	}
	
	public Account edit(Account account) throws EditException {
		System.out.println("Editing account '"+account.getName()+"' ("+account.getIdAccount()+")");
		
		try {
			System.out.println("Enter a new name: ["+account.getName()+"]");
			String strIn = br.readLine();
			if(strIn.length() > 0)
				account.setName(strIn);
			
			System.out.println("Enter a new code number: ["+account.getCodeNumber()+"]");
			strIn = br.readLine();
			if(strIn.length() > 0)
				account.setCodeNumber(strIn);
			
			System.out.println("Enter a new balance (+-##,##) ["+account.getBalance()+"]");
			strIn = br.readLine();
			if(strIn.length() > 0){
				double balance = NumberFormat.getInstance().parse(strIn).doubleValue();
				account.setBalance(balance);
			}
			
			String strAccountable = account.isAccountable() ? "Y" : "N";
			System.out.println("The account will be accountable (Y/N)? ["+strAccountable+"]");
			strIn = br.readLine();
			if(strIn.length() > 0)
				account.setAccountable("Y".equals(strIn) || "y".equals(strIn));
			
		} catch (IOException e) {
			String msg = "Error reading from input";
			throw new EditException(msg, e);
		} catch (ParseException e) {
			String msg = "Error parsing a number";
			throw new EditException(msg, e);
		}
		
		return account;
	}
}
