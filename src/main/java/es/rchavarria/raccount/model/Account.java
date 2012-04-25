package es.rchavarria.raccount.model;

public class Account {

    public static final String ID_ACCOUNT = "idAccount";
    public static final String NAME = "name";
    public static final String BALANCE = "balance";
    public static final String ACCOUNTABLE = "accountable";
    public static final String CODE_NUMBER = "codeNumber";

    private long idAccount;
    private String name;
    private double balance;
    private boolean accountable;
    private String codeNumber;

    public long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(final long idAccount) {
        this.idAccount = idAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(final double balance) {
        this.balance = balance;
    }

    public boolean isAccountable() {
        return accountable;
    }

    public void setAccountable(final boolean accountable) {
        this.accountable = accountable;
    }

    public String getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(final String codeNumber) {
        this.codeNumber = codeNumber;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Account)) {
            throw new IllegalArgumentException("Trying to compare to a non Account object");
        }

        Account a = (Account) obj;
        return accountable == a.isAccountable() && balance == a.getBalance() && codeNumber.equals(a.getCodeNumber())
                && idAccount == a.getIdAccount() && name.equals(a.getName());
    }
}
