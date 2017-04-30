package persistence;

import exception.*;
import mapper.MapperComputer;
import model.Company;
import model.Computer;
import model.FilterSelect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.operator.Filter;
import services.TransactionHolder;
import utils.SqlNames;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ComputerDAO implements IComputerDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);


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

    public static final String DELETE = "DELETE FROM " + SqlNames.COMPUTER_TABLE_NAME + " WHERE " + SqlNames.COMPUTER_TABLE_NAME + "." + SqlNames.COMPUTER_COL_COMPUTER_ID + "=?";

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


    public static ComputerDAO dao = new ComputerDAO();

    private Connector connector;

    /**
     * Constructor.
     */
    private ComputerDAO() {
        connector = Connector.getInstance();
    }

    public static ComputerDAO getInstance() {
        return dao;
    }


    // When selecting multiple computer, if they belong to the same company, we make them point on the same Company object
    // Each company is build at most one time
    public static final HashMap<Integer, Company> CACHE_COMPANY = new HashMap<>();

    @Override
    public List<Computer> getAll() throws DAOSelectException {
        CACHE_COMPANY.clear();
        Connection connection = null;
        ResultSet rs = null;
        List<Computer> result = null;

        try {
            connection = TransactionHolder.get();
            rs = connection.prepareStatement(SELECT).executeQuery();
            result = MapperComputer.mapResultSetToObjects(rs);

            LOGGER.info("Succes getAll computerdao");
            CACHE_COMPANY.clear();
            return result;
        } catch (SQLException e) {
            LOGGER.info("Erreur getAll computerdao : " + e.getMessage());
            e.printStackTrace();
            CACHE_COMPANY.clear();
            throw new DAOSelectException(SqlNames.COMPUTER_TABLE_NAME, SELECT);
        }
    }

    @Override
    public List<Computer> getFromFilter(FilterSelect fs) throws DAOSelectException {
        CACHE_COMPANY.clear();
        List<Computer> result = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        StringBuilder query = new StringBuilder();
        query.append(SELECT);

        try {
            connection = TransactionHolder.get();

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
        }

        throw new DAOSelectException(SqlNames.COMPUTER_TABLE_NAME, "Computer Select From Filter Exception");
    }

    @Override
    public Computer getById(int id) throws DAOSelectException {
        CACHE_COMPANY.clear();
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        Computer result = null;

        try {
            connection = TransactionHolder.get();
            preparedStatement = connection.prepareStatement(SELECT + WHERE_FILTER_ID);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            result = MapperComputer.mapResultSetToObject(rs);

            preparedStatement.close();
            LOGGER.info("Succes getbyid computerdao");
            CACHE_COMPANY.clear();
            return result;
        } catch (Exception e) {
            LOGGER.info("Erreur sql get by id : " + e.getMessage());
            e.printStackTrace();
            CACHE_COMPANY.clear();
            throw new DAOSelectException(SqlNames.COMPUTER_TABLE_NAME, SELECT + WHERE_FILTER_ID + " (id=" + id + ")");
        }
    }

    @Override
    public int insert(Computer computer) throws DAOInsertException {
        CACHE_COMPANY.clear();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = TransactionHolder.get();
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
                    CACHE_COMPANY.clear();
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
        }

        CACHE_COMPANY.clear();
        throw new DAOInsertException(computer);
    }

    @Override
    public boolean deleteById(int id) throws DAODeleteException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = TransactionHolder.get();
            statement = connection.prepareStatement(DELETE);
            statement.setInt(1, id);
            int resultExec = statement.executeUpdate();
            statement.close();
            LOGGER.info("Succes delete " + id + " computerdao");
            return resultExec != 0;
        } catch (SQLException e) {
            LOGGER.info("Error delete Computerdao " + id + " : " + e.getMessage());
            e.printStackTrace();
            throw new DAODeleteException(SqlNames.COMPUTER_TABLE_NAME, id);
        }
    }

    @Override
    public boolean update(Computer computer) throws DAOUpdateException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = TransactionHolder.get();
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
        }
        throw new DAOUpdateException(computer);
    }

    @Override
    public Computer getLastComputerInserted() throws DAOSelectException {
        CACHE_COMPANY.clear();
        Connection connection = null;
        ResultSet rs = null;
        Computer result = null;
        try {
            connection = TransactionHolder.get();
            rs = connection.prepareStatement(SELECT_LAST_COMPUTER_INSERTED).executeQuery();
            result = MapperComputer.mapResultSetToObject(rs);
            LOGGER.info("Succes getLastComputerInserted Computerdao");
            CACHE_COMPANY.clear();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info("Error Get last computer inserted Computerdao : " + e.getMessage());
        }

        CACHE_COMPANY.clear();
        throw new DAOSelectException(SqlNames.COMPUTER_TABLE_NAME, SELECT_LAST_COMPUTER_INSERTED);
    }

    @Override
    public List<Computer> getPagination(int page, int numberOfResults, String filterName) throws DAOSelectException {
        CACHE_COMPANY.clear();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Computer> res = null;

        try {
            connection = TransactionHolder.get();

            if (filterName == null) {
                ps = connection.prepareStatement(SELECT + LIMIT_PAGE);
                ps.setInt(1, numberOfResults);
                ps.setInt(2, page * numberOfResults);
            } else {
                ps = connection.prepareStatement(SELECT_FILTER_NAME + LIMIT_PAGE);
                ps.setString(1, "%" + filterName + "%");
                ps.setInt(2, numberOfResults);
                ps.setInt(3, page * numberOfResults);
            }
            rs = ps.executeQuery();
            res = MapperComputer.mapResultSetToObjects(rs);

            ps.close();
            LOGGER.info("Succes pagination Computerdao");
            CACHE_COMPANY.clear();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info("Error Pagination Computerdao : " + e.getMessage());
        }

        CACHE_COMPANY.clear();
        throw new DAOSelectException(SqlNames.COMPUTER_TABLE_NAME, SELECT + LIMIT_PAGE);
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
            connection = TransactionHolder.get();

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
        }

        throw new DAOCountException("Computer");
    }


    @Override
    public void deleteComputerBelongingToCompany(int idCompany) throws DAODeleteException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = TransactionHolder.get();
            statement = connection.prepareStatement(DELETE_COMPUTER_OF_COMPANY);
            statement.setInt(1, idCompany);
            int resultExec = statement.executeUpdate();
            statement.close();
            LOGGER.info("Success deleting computers belonging to company " + idCompany + " computerdao");

        } catch (SQLException e) {
            LOGGER.info("Error deleting computers of company " + idCompany + " : " + e.getMessage());
            e.printStackTrace();
            throw new DAODeleteException(SqlNames.COMPUTER_TABLE_NAME, DELETE_COMPUTER_OF_COMPANY + " (idCompany = " + idCompany + ")");
        }
    }

}
