package es.rchavarria.raccount.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.model.Account;

public class AccountDAO {

    private Session session;

    public AccountDAO(final Session session) {
        this.session = session;
    }

    public Account find(final long idAccount) throws DAOException {
        String sql = "SELECT * FROM Account WHERE idAccount=" + idAccount;
        Account account = null;
        ResultSet rs = null;

        try {
            rs = session.sqlFind(sql);
            if (rs.next()) {
                account = fill(rs);
            }

        } catch (SQLException e) {
            String msg = "Error retriving Account with id: " + idAccount;
            // log.error(msg, e);
            throw new DAOException(msg, e);
        } finally {
            closeResultSet(rs);
        }

        return account;
    }

    private void closeResultSet(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e1) {
            }
        }
    }

    public void update(final Account account) throws DAOException {
        if (!(account.getIdAccount() > 0)) {
            throw new IllegalArgumentException("Wrond idAccount: " + account.getIdAccount());
        }

        String sql = "UPDATE Account SET ";
        sql += Account.NAME + "='" + account.getName() + "', ";
        sql += Account.BALANCE + "=" + account.getBalance() + ", ";
        sql += Account.CODE_NUMBER + "='" + account.getCodeNumber() + "', ";
        String boolValue = account.isAccountable() ? "1" : "0";
        sql += Account.ACCOUNTABLE + "=" + boolValue + " ";
        sql += "WHERE idAccount=" + account.getIdAccount();

        try {
            session.sqlExecute(sql);
        } catch (SQLException e) {
            String msg = "Error updating Account: id=" + account.getIdAccount() + ", name=" + account.getName();
            // log.error(msg, e);
            throw new DAOException(msg, e);
        }
    }

    public long insert(final Account account) throws DAOException {
        String names = Account.NAME + ", " + Account.BALANCE + ", " + Account.CODE_NUMBER + ", " + Account.ACCOUNTABLE;
        String boolValue = account.isAccountable() ? "1" : "0";
        String values = "'" + account.getName() + "', " + account.getBalance() + ", " + "'" + account.getCodeNumber()
                + "', " + boolValue;
        String sql = "INSERT INTO Account (" + names + ") VALUES (" + values + ")";

        try {
            session.sqlExecute(sql);
        } catch (SQLException e) {
            String msg = "Error creating new Account. SQL string=[" + sql + "]";
            // log.error(msg, e);
            throw new DAOException(msg, e);
        }

        String sqlSelect = "SELECT MAX(idAccount) FROM Account";
        ResultSet rs = null;
        try {
            rs = session.sqlFind(sqlSelect);
            if (rs.next()) {
                long id = rs.getLong(1);
                return id;
            }
        } catch (SQLException e) {
            String msg = "Error retrieving id of new Account created";
            // log.error(msg, e);
            throw new DAOException(msg, e);
        } finally {
            closeResultSet(rs);
        }

        return -1; // throw exception ?
    }

    public List<Account> listAll() throws DAOException {
        String sql = "SELECT * FROM Account";
        return getSqlSelectResult(sql);
    }

    public List<Account> listAllAccountables() throws DAOException {
        String sql = "SELECT * FROM Account WHERE " + Account.ACCOUNTABLE + "=1";
        return getSqlSelectResult(sql);
    }

    private List<Account> getSqlSelectResult(final String sql) throws DAOException {
        List<Account> all = new ArrayList<Account>();
        ResultSet rs = null;

        try {
            rs = session.sqlFind(sql);
            while (rs.next()) {
                Account account = fill(rs);
                all.add(account);
            }

        } catch (SQLException e) {
            String msg = "Error retriving Accounts";
            // log.error(msg, e);
            throw new DAOException(msg, e);
        } finally {
            closeResultSet(rs);
        }

        return all;
    }

    private Account fill(final ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setIdAccount(rs.getLong(Account.ID_ACCOUNT));
        account.setName(rs.getString(Account.NAME));
        int accountable = rs.getInt(Account.ACCOUNTABLE);
        account.setAccountable(accountable == 1);
        account.setBalance(rs.getDouble(Account.BALANCE));
        account.setCodeNumber(rs.getString(Account.CODE_NUMBER));

        return account;
    }

    public void delete(final Account account) throws DAOException {
        String sql = "DELETE FROM Account WHERE idAccount=" + account.getIdAccount();
        try {
            session.sqlExecute(sql);
        } catch (SQLException e) {
            String msg = "Error deleting Account: id=" + account.getIdAccount() + ", name=" + account.getName();
            // log.error(msg, e);
            throw new DAOException(msg, e);
        }
    }
}
