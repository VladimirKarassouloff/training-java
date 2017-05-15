package persistence;

import exception.DAOCountException;
import exception.DAODeleteException;
import exception.DAOInsertException;
import exception.DAOSelectException;
import exception.DAOUpdateException;
import mapper.MapperComputer;
import model.Computer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.filter.FilterSelect;
import persistence.operator.Filter;
import utils.SqlNames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ComputerDAO implements IComputerDAO {

    //////////////////////////////////////////////////////////////////////
    /////Query parts
    public static final String SELECT = "SELECT " + SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTER_ID + ", "
            + SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTER_NAME + ", "
            + SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTER_INTRODUCED + ", "
            + SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTERDISCONTINUED + ", "
            + SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTER_COMPANY_ID + ", "
            + SqlNames.COMPANY_TABLE_NAME + "." + SqlNames.COMPANY_COL_COMPANY_NAME + " as " + SqlNames.COMPUTER_COL_JOINED_COMPANY_NAME
            + " FROM " + SqlNames.COMPUTER_TABLE_NAME + " LEFT JOIN " + SqlNames.COMPANY_TABLE_NAME
            + " ON " + SqlNames.COMPANY_TABLE_NAME + "." + SqlNames.COMPANY_COL_COMPANY_ID
            + "=" + SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTER_COMPANY_ID;


    public static final String COUNT = "SELECT Count(*) FROM " + SqlNames.COMPUTER_TABLE_NAME
            + " LEFT JOIN " + SqlNames.COMPANY_TABLE_NAME
            + " ON " + SqlNames.COMPANY_TABLE_NAME + "." + SqlNames.COMPANY_COL_COMPANY_ID
            + "=" + SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTER_COMPANY_ID;

    public static final String WHERE_FILTER_ID = " WHERE " + SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTER_ID + "=?";

    public static final String DELETE = "DELETE FROM " + SqlNames.COMPUTER_TABLE_NAME + " WHERE ";

    public static final String DELETE_COMPUTER_OF_COMPANY = "DELETE FROM " + SqlNames.COMPUTER_TABLE_NAME + " WHERE " +
            SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTER_COMPANY_ID + "= ?";


    public static final String INSERT = "INSERT INTO " + SqlNames.COMPUTER_TABLE_NAME + "(" + SqlNames.COMPUTER_COL_COMPUTER_NAME + ","
            + SqlNames.COMPUTER_COL_COMPUTER_INTRODUCED + "," + SqlNames.COMPUTER_COL_COMPUTERDISCONTINUED + "," + SqlNames.COMPUTER_COL_COMPUTER_COMPANY_ID + ") "
            + "VALUES (?,?,?,?)";


    public static final String UPDATE = "UPDATE " + SqlNames.COMPUTER_TABLE_NAME + " SET " + SqlNames.COMPUTER_COL_COMPUTER_NAME + " = ?,"
            + SqlNames.COMPUTER_COL_COMPUTER_INTRODUCED + " = ?, "
            + SqlNames.COMPUTER_COL_COMPUTERDISCONTINUED + " = ?, "
            + SqlNames.COMPUTER_COL_COMPUTER_COMPANY_ID + " = ? "
            + "WHERE " + SqlNames.COMPUTER_COL_COMPUTER_ID + "= ?";


    public static final String WHERE_NAME_FILTER = " WHERE " + SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTER_NAME + " LIKE ?";

    public static final String SELECT_FILTER_NAME = SELECT + " " + WHERE_NAME_FILTER;

    public static final String COUNT_FILTER_NAME = COUNT + WHERE_NAME_FILTER;

    public static final String SELECT_LAST_COMPUTER_INSERTED = SELECT + " ORDER BY " + SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTER_ID + " DESC LIMIT 1";

    public static final String LIMIT_PAGE = " LIMIT ? OFFSET ? ";

    ///////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////


    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    private static ComputerDAO dao = new ComputerDAO();
    private static Connector connector;

    /**
     * Constructor.
     */
    private ComputerDAO() {
        connector = Connector.getInstance();
    }

    public static ComputerDAO getInstance() {
        return dao;
    }


    @Override
    public List<Computer> getAll() throws DAOSelectException {
        Connection connection = null;
        ResultSet rs = null;
        List<Computer> result = null;

        try {
            connection = connector.getConnection();
            rs = connection.prepareStatement(SELECT).executeQuery();
            result = MapperComputer.mapResultSetToObjects(rs);

            LOGGER.info("Succes getAll computerdao");
            return result;
        } catch (SQLException e) {
            LOGGER.info("Erreur getAll computerdao : " + e.getMessage());
            e.printStackTrace();
            throw new DAOSelectException(SqlNames.COMPUTER_TABLE_NAME, SELECT);
        } finally {
            connector.closeIfNotTransactionnal();
        }
    }

    @Override
    public List<Computer> getFromFilter(FilterSelect fs) throws DAOSelectException {
        List<Computer> result = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        StringBuilder query = new StringBuilder();
        query.append(SELECT);

        try {
            connection = connector.getConnection();

            // If we have at least one column filtered
            Iterator<String> it = fs.getFilteredColumns().iterator();
            if (it.hasNext()) {
                query.append(" WHERE ");
                while (it.hasNext()) {
                    String col = it.next();
                    query.append(col + " " + fs.getFilterValue(col).getOperator() + " ? ");
                    if (it.hasNext()) {
                        query.append(" OR ");
                    }
                }
            }

            // Looking for order
            if (fs.getOrderOnColumn() != null) {
                query.append(" ORDER BY " + fs.getOrderOnColumn() + " " + (fs.isAsc() ? " ASC " : " DESC "));
            }

            // Looking for pagination
            if (fs.isPaginated()) {
                query.append(" LIMIT " + (fs.getNumberOfResult()) + " OFFSET " + (fs.getPage() * fs.getNumberOfResult()) + " ");
            }

            // Preparing query and binding arguments
            preparedStatement = connection.prepareStatement(query.toString());
            int paramCount = 1;

            // Binding for where
            for (Filter op : fs.getFilterValues()) {
                preparedStatement.setString(paramCount++, op.getValue());
            }

            System.out.println(preparedStatement);
            rs = preparedStatement.executeQuery();
            result = MapperComputer.mapResultSetToObjects(rs);
            preparedStatement.close();
            LOGGER.info("Succes getFromFilter ComputerDAO");
            return result;
        } catch (SQLException e) {
            LOGGER.info("Erreur getFromFilter ComputerDAO : " + e.getMessage() + " query built : " + query.toString());
        } finally {
            connector.closeIfNotTransactionnal();
        }

        throw new DAOSelectException(SqlNames.COMPUTER_TABLE_NAME, "Computer Select From Filter Exception");
    }

    @Override
    public Computer getById(int id) throws DAOSelectException {
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        Computer result = null;

        try {
            connection = connector.getConnection();
            preparedStatement = connection.prepareStatement(SELECT + WHERE_FILTER_ID);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            result = MapperComputer.mapResultSetToObject(rs);

            preparedStatement.close();
            LOGGER.info("Succes getbyid computerdao");
            return result;
        } catch (Exception e) {
            LOGGER.info("Erreur sql get by id : " + e.getMessage());
            e.printStackTrace();
            throw new DAOSelectException(SqlNames.COMPUTER_TABLE_NAME, SELECT + WHERE_FILTER_ID + " (id=" + id + ")");
        } finally {
            connector.closeIfNotTransactionnal();
        }
    }

    @Override
    public int insert(Computer computer) throws DAOInsertException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = connector.getConnection();
            statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, computer.getName());
            statement.setDate(2, (computer.getIntroduced() != null ? new java.sql.Date(computer.getIntroduced().getTime()) : null));
            statement.setDate(3, (computer.getDiscontinued() != null ? new java.sql.Date(computer.getDiscontinued().getTime()) : null));
            statement.setString(4, computer.getCompany() != null ? String.valueOf(computer.getCompany().getId()) : null);


            if (statement.executeUpdate() != 0) {
                generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    computer.setId((int) generatedKeys.getLong(1));
                    statement.close();
                    LOGGER.info("Success insert computerdao : " + computer);
                    return computer.getId();
                } else {
                    // No id returned
                    LOGGER.info("Erreur insert computerdao : " + computer);
                    statement.close();
                    throw new Exception();
                }

            } else {
                // No row affected
                LOGGER.info("Erreur insert 2 computerdao : " + computer);
                statement.close();
                throw new Exception();
            }
        } catch (SQLException e2) {
            LOGGER.info("Erreur 4 insert SQL computerdao : " + computer + " => " + e2.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("Erreur 3 insert computerdao : " + computer + " => " + e.getMessage());
        } finally {
            connector.closeIfNotTransactionnal();
        }

        throw new DAOInsertException(computer);
    }

    @Override
    public boolean deleteById(List<Integer> ids) throws DAODeleteException {
        if (ids == null) {
            LOGGER.info("Computerdao : Error delete nothing to delete");
            return false;
        }

        // Remove null values
        ids.removeAll(Collections.singleton(null));
        if (ids.isEmpty()) {
            LOGGER.info("Computerdao : Error delete nothing to delete after deleting null ids");
            return false;
        }

        // Build query
        StringBuilder queryDelete = new StringBuilder(DELETE);
        Iterator<Integer> it = ids.iterator();
        while (it.hasNext()) {
            Integer idToDelete = it.next();
            queryDelete.append(" " + SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTER_ID + "=" + idToDelete);
            if (it.hasNext()) {
                queryDelete.append(" OR ");
            }
        }

        // Exec query
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connector.getConnection();
            statement = connection.prepareStatement(queryDelete.toString());
            int resultExec = statement.executeUpdate();
            statement.close();
            LOGGER.info("Computerdao : Succes delete " + ids);
            return resultExec != 0;
        } catch (SQLException e) {
            LOGGER.info("Computerdao : Error delete " + ids + " : " + e.getMessage());
            e.printStackTrace();
            throw new DAODeleteException(SqlNames.COMPUTER_TABLE_NAME, ids);
        } finally {
            connector.closeIfNotTransactionnal();
        }
    }

    @Override
    public boolean update(Computer computer) throws DAOUpdateException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connector.getConnection();
            statement = connection.prepareStatement(UPDATE);
            statement.setString(1, computer.getName());
            statement.setDate(2, (computer.getIntroduced() != null ? new java.sql.Date(computer.getIntroduced().getTime()) : null));
            statement.setDate(3, (computer.getDiscontinued() != null ? new java.sql.Date(computer.getDiscontinued().getTime()) : null));
            statement.setString(4, (computer.getCompany() == null ? null : String.valueOf(computer.getCompany().getId())));
            statement.setInt(5, computer.getId());
            int resultExec = statement.executeUpdate();
            statement.close();
            LOGGER.info("Succes Update Computerdao : " + computer);
            return resultExec != 0;
        } catch (SQLException e) {
            LOGGER.info("Error Update Computerdao : " + computer + " => " + e.getMessage());
        } finally {
            connector.closeIfNotTransactionnal();
        }
        throw new DAOUpdateException(computer);
    }

    @Override
    public Computer getLastComputerInserted() throws DAOSelectException {
        Connection connection = null;
        ResultSet rs = null;
        Computer result = null;
        try {
            connection = connector.getConnection();
            rs = connection.prepareStatement(SELECT_LAST_COMPUTER_INSERTED).executeQuery();
            result = MapperComputer.mapResultSetToObject(rs);
            LOGGER.info("Succes getLastComputerInserted Computerdao");
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info("Error Get last computer inserted Computerdao : " + e.getMessage());
        } finally {
            connector.closeIfNotTransactionnal();
        }

        throw new DAOSelectException(SqlNames.COMPUTER_TABLE_NAME, SELECT_LAST_COMPUTER_INSERTED);
    }


    @Override
    public int getCount(FilterSelect fs) throws DAOCountException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Integer count = null;
        StringBuilder query = new StringBuilder();
        query.append(COUNT);

        try {
            connection = connector.getConnection();

            // If we have at least one column filtered
            Iterator<String> it = fs.getFilteredColumns().iterator();
            if (it.hasNext()) {
                query.append(" WHERE  ");
                while (it.hasNext()) {
                    String col = it.next();
                    query.append(col + " " + fs.getFilterValue(col).getOperator() + " ? ");
                    if (it.hasNext()) {
                        query.append(" OR  ");
                    }
                }
            }

            // Preparing query and binding arguments
            preparedStatement = connection.prepareStatement(query.toString());
            int paramCount = 1;

            // Binding for where
            for (Filter op : fs.getFilterValues()) {
                preparedStatement.setString(paramCount++, op.getValue());
            }

            System.out.println("Count : ");
            System.out.println(preparedStatement);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            LOGGER.info("Success Count Computerdao ");
            return count;

        } catch (SQLException e) {
            LOGGER.info("Erreur count getFromFilter ComputerDAO : " + e.getMessage() + " query built : " + query.toString());
        } finally {
            connector.closeIfNotTransactionnal();
        }

        throw new DAOCountException("Computer");
    }


    @Override
    public void deleteComputerBelongingToCompany(int idCompany) throws DAODeleteException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connector.getConnection();
            statement = connection.prepareStatement(DELETE_COMPUTER_OF_COMPANY);
            statement.setInt(1, idCompany);
            int resultExec = statement.executeUpdate();
            statement.close();
            LOGGER.info("Success deleting computers belonging to company " + idCompany + " computerdao");

        } catch (SQLException e) {
            LOGGER.info("Error deleting computers of company " + idCompany + " : " + e.getMessage());
            e.printStackTrace();
            throw new DAODeleteException(SqlNames.COMPUTER_TABLE_NAME, DELETE_COMPUTER_OF_COMPANY + " (idCompany = " + idCompany + ")");
        } finally {
            connector.closeIfNotTransactionnal();
        }
    }

}
