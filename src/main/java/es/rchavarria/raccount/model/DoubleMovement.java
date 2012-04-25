package es.rchavarria.raccount.model;

public class DoubleMovement extends Movement {

    private Account accountTo;

	public Account getAccountTo() {
		return accountTo;
	}
	public void setAccountTo(Account accountTo) {
		this.accountTo = accountTo;
	}
}
