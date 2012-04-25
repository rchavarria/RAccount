package es.rchavarria.raccount.model;

public class AccountTestFactory {

	public static Account create(){
        return create("Account test name", true);
	}

	public static Account create(String name, boolean accountable){
        Account a = new Account();
        a.setName(name);
        a.setBalance(2.5d);
        a.setCodeNumber("code number");
        a.setAccountable(accountable);
        return a;
	}
}
