package es.rchavarria.raccount.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.model.Account;
import es.rchavarria.raccount.model.Concept;
import es.rchavarria.raccount.model.Movement;

public class MovementDAO {

    private Session session;

    public MovementDAO(final Session session) {
        this.session = session;
    }

    public Movement find(final long idMovement) throws DAOException {
        String sql = "SELECT * FROM Movement WHERE idMovement=" + idMovement;
        Movement movement = null;
        ResultSet rs = null;

        try {
            rs = session.sqlFind(sql);
            while (rs.next()) {
                movement = fill(rs);
            }

        } catch (SQLException e) {
            String msg = "Error retriving Movement with id: " + idMovement;
            // log.error(msg, e);
            throw new DAOException(msg, e);
        } finally {
            closeResultSet(rs);
        }

        return movement;
    }

    private void closeResultSet(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e1) {
            }
        }
    }

    public void update(final Movement movement) throws DAOException {
        if (!(movement.getIdMovement() > 0)) {
            throw new IllegalArgumentException("Wrong idMovement: " + movement.getIdMovement());
        }

        String sql = "UPDATE Movement SET ";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sql += Movement.DESCRIPTION + "='" + movement.getDescription() + "', ";
        sql += Movement.MOVEMENT_DATE + "='" + sdf.format(movement.getMovementDate()) + "', ";
        sql += Movement.AMOUNT + "=" + movement.getAmount() + ", ";
        sql += Movement.FINAL_BALANCE + "=" + movement.getFinalBalance() + ", ";
        sql += Movement.ID_ACCOUNT + "=" + movement.getAccount().getIdAccount() + ", ";
        sql += Movement.ID_CONCEPT + "=" + movement.getConcept().getIdConcept() + " ";
        sql += "WHERE idMovement=" + movement.getIdMovement();

        try {
            new AccountDAO(session).update(movement.getAccount());
            new ConceptDAO(session).update(movement.getConcept());

            session.sqlExecute(sql);
        } catch (SQLException e) {
            String msg = "Error updating Account: id=" + movement.getIdMovement() + ", name="
                    + movement.getDescription();
            // log.error(msg, e);
            throw new DAOException(msg, e);
        }
    }

    public long insert(final Movement movement) throws DAOException {
        String names = Movement.DESCRIPTION + ", " 
                       + Movement.MOVEMENT_DATE + ", " 
                       + Movement.AMOUNT + ", "
                       + Movement.FINAL_BALANCE + ", " 
                       + Movement.ID_ACCOUNT + ", " 
                       + Movement.ID_CONCEPT;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String values = "'" + movement.getDescription() + "', " 
                      + "'" + sdf.format(movement.getMovementDate()) + "', "
                      + movement.getAmount() + ", " 
                      + movement.getFinalBalance() + ", "
                      + movement.getAccount().getIdAccount() + ", " 
                      + movement.getConcept().getIdConcept();
        String sql = "INSERT INTO Movement (" + names + ") VALUES (" + values + ")";

        try {
            session.sqlExecute(sql);
        } catch (SQLException e) {
            String msg = "Error creating new Movement. SQL string=[" + sql + "]";
            // log.error(msg, e);
            throw new DAOException(msg, e);
        }

        String sqlSelect = "SELECT MAX(" + Movement.ID_MOVEMENT + ") FROM Movement";
        ResultSet rs = null;
        try {
            rs = session.sqlFind(sqlSelect);
            if (rs.next()) {
                long id = rs.getLong(1);
                return id;
            }
        } catch (SQLException e) {
            String msg = "Error retrieving id of new Movement created";
            // log.error(msg, e);
            throw new DAOException(msg, e);
        } finally {
            closeResultSet(rs);
        }

        return -1; // throw exception ?
    }

    public List<Movement> listAll() throws DAOException {
        List<Movement> all = new ArrayList<Movement>();
        String sql = "SELECT * FROM Movement";
        ResultSet rs = null;

        try {
            rs = session.sqlFind(sql);
            while (rs.next()) {
                Movement concept = fill(rs);
                all.add(concept);
            }

        } catch (SQLException e) {
            String msg = "Error retriving Movements";
            // log.error(msg, e);
            throw new DAOException(msg, e);
        } finally {
            closeResultSet(rs);
        }

        return all;
    }

    public void delete(final Movement movement) throws DAOException {
        String sql = "DELETE FROM Movement WHERE idMovement=" + movement.getIdMovement();

        try {
            session.sqlExecute(sql);

            // no se deben borrar cuentas ni conceptos
            // new AccountDAO(session).delete(movement.getAccount());
            // new ConceptDAO(session).delete(movement.getConcept());
        } catch (SQLException e) {
            String msg = "Error deleting Movement: id=" + movement.getIdMovement() + ", name="
                    + movement.getDescription();
            // log.error(msg, e);
            throw new DAOException(msg, e);
        }
    }

    private Movement fill(final ResultSet rs) throws SQLException, DAOException {
        Movement movement = new Movement();
        movement.setIdMovement(rs.getLong(Movement.ID_MOVEMENT));
        movement.setDescription(rs.getString(Movement.DESCRIPTION));
        java.sql.Date sqlDate = rs.getDate(Movement.MOVEMENT_DATE);
        java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
        movement.setMovementDate(utilDate);
        movement.setAmount(rs.getDouble(Movement.AMOUNT));
        movement.setFinalBalance(rs.getDouble(Movement.FINAL_BALANCE));
        long idAccount = rs.getLong(Movement.ID_ACCOUNT);
        movement.setAccount(new AccountDAO(session).find(idAccount));
        long idConcept = rs.getLong(Movement.ID_CONCEPT);
        movement.setConcept(new ConceptDAO(session).find(idConcept));

        return movement;
    }

    public int count() throws DAOException {
        String sqlSelect = "SELECT COUNT(" + Movement.ID_MOVEMENT + ") FROM Movement";
        ResultSet rs = null;
        try {
            rs = session.sqlFind(sqlSelect);
            if (rs.next()) {
                int id = rs.getInt(1);
                return id;
            }
        } catch (SQLException e) {
            String msg = "Error counting number of movements";
            throw new DAOException(msg, e);
        } finally {
            closeResultSet(rs);
        }

        return -1;
    }

    /**
     * Referencia de la BBDD derby para seleccionar un cierto numero de filas
     * 
     * @see http://db.apache.org/derby/docs/10.6/ref/rrefsqljoffsetfetch.html#rrefsqljoffsetfetch
     * 
     * @param account
     *            Cuenta desde donde se tomar�n los movimientos
     * @param n
     *            Numero de filas a seleccionar
     * @return n filas ordenadas por fecha
     * @throws DAOException
     */
    public List<Movement> listLastN(final Account account, final int n) throws DAOException {
        List<Movement> all = new ArrayList<Movement>();
        String sql = "SELECT * FROM Movement" + " WHERE " + Movement.ID_ACCOUNT + "=" + account.getIdAccount()
                + " ORDER BY " + Movement.MOVEMENT_DATE + " DESC" + " FETCH NEXT " + n + " ROWS ONLY";
        // Y si me quiero saltar los 10 primeros
        // String sql =
        // "SELECT * FROM Movement ORDER BY "+Movement.MOVEMENT_DATE+" OFFSET 10 ROWS FETCH NEXT "+n+" ROWS ONLY";
        ResultSet rs = null;

        try {
            rs = session.sqlFind(sql);
            while (rs.next()) {
                Movement concept = fill(rs);
                all.add(concept);
            }

        } catch (SQLException e) {
            String msg = "Error retriving first " + n + " Movements ordered by " + Movement.MOVEMENT_DATE;
            // log.error(msg, e);
            throw new DAOException(msg, e);
        } finally {
            closeResultSet(rs);
        }

        return all;
    }

    public double getExpenses(Account account, Concept concept, Date start, Date end) 
            throws DAOException {
        return 0;
    }
}
