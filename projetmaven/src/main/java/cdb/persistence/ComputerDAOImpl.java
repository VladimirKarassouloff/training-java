package cdb.persistence;

import cdb.exception.DAOCountException;
import cdb.exception.DAODeleteException;
import cdb.exception.DAOInsertException;
import cdb.exception.DAOSelectException;
import cdb.exception.DAOUpdateException;
import cdb.mapper.MapperComputer;
import cdb.model.Computer;
import cdb.persistence.filter.FilterSelect;
import cdb.persistence.operator.Filter;
import cdb.utils.SqlNames;
import cdb.utils.UtilsSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


@Repository()
public class ComputerDAOImpl implements IComputerDAO {

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


    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAOImpl.class);

    private DataSource dataSource;

    /**
     * Default constructor.
     * @param dataSource from cdb DB
     */
    @Autowired
    public ComputerDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<Computer> getAll() throws DAOSelectException {
        Connection connection = null;
        ResultSet rs = null;
        List<Computer> result = null;

        try {
            connection = DataSourceUtils.getConnection(dataSource);
            rs = connection.prepareStatement(SELECT).executeQuery();
            result = MapperComputer.mapResultSetToObjects(rs);
            return result;
        } catch (SQLException e) {
            LOGGER.info("Erreur getAll computerdao : " + e.getMessage());
            throw new DAOSelectException(SqlNames.COMPUTER_TABLE_NAME, SELECT);
        } finally {
            UtilsSql.closeResultSetSafely(rs);
            DataSourceUtils.releaseConnection(connection, dataSource);
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
            connection = DataSourceUtils.getConnection(dataSource);

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

            rs = preparedStatement.executeQuery();
            result = MapperComputer.mapResultSetToObjects(rs);
            return result;
        } catch (ArrayIndexOutOfBoundsException | SQLException e) {
            LOGGER.info("Erreur getFromFilter ComputerDAOImpl : " + e.getMessage() + " query built : " + query.toString());
        } finally {
            UtilsSql.closeResultSetSafely(rs);
            UtilsSql.closeStatementSafely(preparedStatement);
            DataSourceUtils.releaseConnection(connection, dataSource);
        }

        throw new DAOSelectException(SqlNames.COMPUTER_TABLE_NAME, "Computer Select From Filter Exception");
    }

    @Override
    public Computer get(int id) throws DAOSelectException {
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        Computer result = null;

        try {
            connection = DataSourceUtils.getConnection(dataSource);
            preparedStatement = connection.prepareStatement(SELECT + WHERE_FILTER_ID);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            result = MapperComputer.mapResultSetToObject(rs);
            return result;
        } catch (SQLException e) {
            LOGGER.info("Erreur sql get by id : " + e.getMessage());
            throw new DAOSelectException(SqlNames.COMPUTER_TABLE_NAME, SELECT + WHERE_FILTER_ID + " (id=" + id + ")");
        } finally {
            UtilsSql.closeResultSetSafely(rs);
            UtilsSql.closeStatementSafely(preparedStatement);
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public int insert(Computer computer) throws DAOInsertException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = DataSourceUtils.getConnection(dataSource);
            statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, computer.getName());
            statement.setDate(2, (computer.getIntroduced() != null ? new java.sql.Date(computer.getIntroduced().getTime()) : null));
            statement.setDate(3, (computer.getDiscontinued() != null ? new java.sql.Date(computer.getDiscontinued().getTime()) : null));
            statement.setString(4, computer.getCompany() != null ? String.valueOf(computer.getCompany().getId()) : null);

            if (statement.executeUpdate() != 0) {
                generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    computer.setId((int) generatedKeys.getLong(1));
                    return computer.getId();
                } else {
                    LOGGER.info("Erreur insert computerdao : " + computer + " => no id returned");
                    throw new SQLException();
                }

            } else {
                LOGGER.info("Erreur insert 2 computerdao : " + computer + " => no row affected");
                throw new SQLException();
            }
        } catch (SQLException e2) {
            LOGGER.info("Erreur 3 insert SQL computerdao : " + computer + " => " + e2.getMessage());
        } finally {
            UtilsSql.closeResultSetSafely(generatedKeys);
            UtilsSql.closeStatementSafely(statement);
            DataSourceUtils.releaseConnection(connection, dataSource);
        }

        throw new DAOInsertException(computer);
    }

    @Override
    public boolean delete(List<Integer> ids) throws DAODeleteException {
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
            connection = DataSourceUtils.getConnection(dataSource);
            statement = connection.prepareStatement(queryDelete.toString());
            int resultExec = statement.executeUpdate();
            return resultExec != 0;
        } catch (SQLException e) {
            LOGGER.info("Computerdao : Error delete " + ids + " : " + e.getMessage());
            throw new DAODeleteException(SqlNames.COMPUTER_TABLE_NAME, ids);
        } finally {
            UtilsSql.closeStatementSafely(statement);
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public boolean update(Computer computer) throws DAOUpdateException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DataSourceUtils.getConnection(dataSource);
            statement = connection.prepareStatement(UPDATE);
            statement.setString(1, computer.getName());
            statement.setDate(2, (computer.getIntroduced() != null ? new java.sql.Date(computer.getIntroduced().getTime()) : null));
            statement.setDate(3, (computer.getDiscontinued() != null ? new java.sql.Date(computer.getDiscontinued().getTime()) : null));
            statement.setString(4, (computer.getCompany() == null ? null : String.valueOf(computer.getCompany().getId())));
            statement.setInt(5, computer.getId());

            int resultExec = statement.executeUpdate();
            return resultExec != 0;
        } catch (SQLException e) {
            LOGGER.info("Error Update Computerdao : " + computer + " => " + e.getMessage());
        } finally {
            UtilsSql.closeStatementSafely(statement);
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
        throw new DAOUpdateException(computer);
    }

    @Override
    public Computer getLastComputerInserted() throws DAOSelectException {
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement statement = null;
        Computer result = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            statement = connection.prepareStatement(SELECT_LAST_COMPUTER_INSERTED);
            rs = statement.executeQuery();
            result = MapperComputer.mapResultSetToObject(rs);
            return result;
        } catch (SQLException e) {
            LOGGER.info("Error Get last computer inserted Computerdao : " + e.getMessage());
        } finally {
            UtilsSql.closeResultSetSafely(rs);
            UtilsSql.closeStatementSafely(statement);
            DataSourceUtils.releaseConnection(connection, dataSource);
        }

        throw new DAOSelectException(SqlNames.COMPUTER_TABLE_NAME, SELECT_LAST_COMPUTER_INSERTED);
    }


    @Override
    public int getCount(FilterSelect fs) throws DAOCountException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        int count = 0;
        StringBuilder query = new StringBuilder();
        query.append(COUNT);

        try {
            connection = DataSourceUtils.getConnection(dataSource);

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

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            return count;

        } catch (SQLException e) {
            LOGGER.info("Erreur count getFromFilter ComputerDAOImpl : " + e.getMessage() + " query built : " + query.toString());
        } finally {
            UtilsSql.closeResultSetSafely(rs);
            UtilsSql.closeStatementSafely(preparedStatement);
            DataSourceUtils.releaseConnection(connection, dataSource);
        }

        throw new DAOCountException("Computer");
    }


    @Override
    public void deleteComputerBelongingToCompany(int idCompany) throws DAODeleteException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DataSourceUtils.getConnection(dataSource);
            statement = connection.prepareStatement(DELETE_COMPUTER_OF_COMPANY);
            statement.setInt(1, idCompany);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Error deleting computers of company " + idCompany + " : " + e.getMessage());
            throw new DAODeleteException(SqlNames.COMPUTER_TABLE_NAME, DELETE_COMPUTER_OF_COMPANY + " (idCompany = " + idCompany + ")");
        } finally {
            UtilsSql.closeStatementSafely(statement);
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }


}
