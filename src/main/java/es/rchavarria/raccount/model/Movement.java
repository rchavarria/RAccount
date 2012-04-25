package es.rchavarria.raccount.model;

import java.util.Date;

public class Movement {

    public static final String ID_MOVEMENT = "idMovement";
    public static final String DESCRIPTION = "description";
    public static final String MOVEMENT_DATE = "movementDate";
    public static final String AMOUNT = "amount";
    public static final String FINAL_BALANCE = "finalBalance";
    public static final String ID_ACCOUNT = "idAccount";
    public static final String ID_CONCEPT = "idConcept";

    private long idMovement;
    private Date movementDate;
    private String description;
    private double amount;
    private double finalBalance;
    private Concept concept;
    private Account account;

    public long getIdMovement() {
        return idMovement;
    }
    public void setIdMovement(long idMovement) {
        this.idMovement = idMovement;
    }
    public Date getMovementDate() {
        return movementDate;
    }
    public void setMovementDate(Date movementDate) {
        this.movementDate = movementDate;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public double getFinalBalance() {
        return finalBalance;
    }
    public void setFinalBalance(double finalBalance) {
        this.finalBalance = finalBalance;
    }
    public Concept getConcept() {
        return concept;
    }
    public void setConcept(Concept concept) {
        this.concept = concept;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if(! (obj instanceof Movement))
    		throw new IllegalArgumentException("Trying to compare to a non Movement object");
    	
    	Movement m = (Movement) obj;
    	return account.equals(m.getAccount()) 
	    	&& amount == m.getAmount()
	    	&& concept.equals(m.getConcept())
	    	&& description.equals(m.getDescription())
	    	&& finalBalance == m.getFinalBalance()
	    	&& idMovement == m.getIdMovement()
	    	&& movementDate.equals(m.getMovementDate());
    }
}
