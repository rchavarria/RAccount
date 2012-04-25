package es.rchavarria.raccount.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.rchavarria.raccount.db.Session;
import es.rchavarria.raccount.model.Concept;

public class ConceptDAO {

    private Session session;

    public ConceptDAO(final Session session) {
        this.session = session;
    }

    public Concept find(final long idConcept) throws DAOException {
        String sql = "SELECT * FROM Concept WHERE idConcept=" + idConcept;
        Concept concept = null;
        ResultSet rs = null;

        try {
            rs = session.sqlFind(sql);
            if (rs.next()) {
                concept = fill(rs);
            }

        } catch (SQLException e) {
            String msg = "Error retriving Concept with id: " + idConcept;
            // log.error(msg, e);
            throw new DAOException(msg, e);

        } finally {
            closeResultSet(rs);
        }

        return concept;
    }

    private Concept fill(final ResultSet rs) throws SQLException {
        Concept concept = new Concept();
        concept.setIdConcept(rs.getLong(Concept.ID_CONCEPT));
        concept.setName(rs.getString(Concept.NAME));
        int visible = rs.getInt(Concept.VISIBLE);
        concept.setVisible(visible == 1);

        return concept;
    }

    private void closeResultSet(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e1) {
            }
        }
    }

    public void update(final Concept concept) throws DAOException {
        if (!(concept.getIdConcept() > 0)) {
            throw new IllegalArgumentException("Wrond idConcept: " + concept.getIdConcept());
        }

        String sql = "UPDATE Concept SET ";
        sql += Concept.NAME + "='" + concept.getName() + "', ";
        String boolValue = concept.isVisible() ? "1" : "0";
        sql += Concept.VISIBLE + "=" + boolValue + " ";
        sql += "WHERE idConcept=" + concept.getIdConcept();

        try {
            session.sqlExecute(sql);
        } catch (SQLException e) {
            String msg = "Error updating Concept: id=" + concept.getIdConcept() + ", name=" + concept.getName();
            // log.error(msg, e);
            throw new DAOException(msg, e);
        }
    }

    public long insert(final Concept concept) throws DAOException {
        String names = Concept.NAME + ", " + Concept.VISIBLE;
        String boolValue = concept.isVisible() ? "1" : "0";
        String values = "'" + concept.getName() + "', " + boolValue + "";
        String sql = "INSERT INTO Concept (" + names + ") VALUES (" + values + ")";

        try {
            session.sqlExecute(sql);
        } catch (SQLException e) {
            String msg = "Error creating new Concept. SQL string=[" + sql + "]";
            // log.error(msg, e);
            throw new DAOException(msg, e);
        }

        String sqlSelect = "SELECT MAX(idConcept) FROM Concept";
        ResultSet rs = null;
        try {
            rs = session.sqlFind(sqlSelect);
            if (rs.next()) {
                long id = rs.getLong(1);
                return id;
            }
        } catch (SQLException e) {
            String msg = "Error retrieving id of new Concept created";
            // log.error(msg, e);
            throw new DAOException(msg, e);
        } finally {
            closeResultSet(rs);
        }

        return -1; // throw exception ?
    }

    public List<Concept> listAll() throws DAOException {
        String sql = "SELECT * FROM Concept";
        return getSqlSelectResult(sql);
    }

    public List<Concept> listAllVisible() throws DAOException {
        String sql = "SELECT * FROM Concept WHERE " + Concept.VISIBLE + "=1";
        return getSqlSelectResult(sql);
    }

    private List<Concept> getSqlSelectResult(final String sql) throws DAOException {
        List<Concept> all = new ArrayList<Concept>();
        ResultSet rs = null;

        try {
            rs = session.sqlFind(sql);
            while (rs.next()) {
                Concept concept = fill(rs);
                all.add(concept);
            }

        } catch (SQLException e) {
            String msg = "Error retriving concepts";
            // log.error(msg, e);
            throw new DAOException(msg, e);
        } finally {
            closeResultSet(rs);
        }

        return all;
    }

    public void delete(final Concept concept) throws DAOException {
        String sql = "DELETE FROM Concept WHERE idConcept=" + concept.getIdConcept();
        try {
            session.sqlExecute(sql);
        } catch (SQLException e) {
            String msg = "Error deleting Concept: id=" + concept.getIdConcept() + ", name=" + concept.getName();
            // log.error(msg, e);
            throw new DAOException(msg, e);
        }
    }
}
