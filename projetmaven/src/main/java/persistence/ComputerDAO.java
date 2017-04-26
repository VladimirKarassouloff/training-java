package persistence;

import exception.DAOInsertException;
import exception.DAODeleteException;
import exception.DAOUpdateException;
import exception.DAOSelectException;
import exception.DAOCountException;
import mapper.MapperComputer;
import model.Company;
import model.Computer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComputerDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    private static final String TABLE_NAME = "computer";
    public static final String COL_COMPUTER_ID = "id";
    public static final String COL_COMPUTER_NAME = "name";
    public static final String COL_COMPUTER_INTRODUCED = "introduced";
    public static final String COL_COMPUTERDISCONTINUED = "discontinued";
    public static final String COL_COMPUTER_COMPANY_ID = "company_id";
    public static final String COL_JOINED_COMPANY_NAME = "company_name";


    //////////////////////////////////////////////////////////////////////
    /////Query parts
    public static final String SELECT = "SELECT " + TABLE_NAME + "." + COL_COMPUTER_ID + ", " + TABLE_NAME + "." + COL_COMPUTER_NAME + ", " + COL_COMPUTER_INTRODUCED + ", " + COL_COMPUTERDISCONTINUED +
            ", " + COL_COMPUTER_COMPANY_ID + ", " + CompanyDAO.TABLE_NAME + "." + CompanyDAO.COL_COMPANY_NAME + " as " + COL_JOINED_COMPANY_NAME + " FROM " + ComputerDAO.TABLE_NAME +
            " LEFT JOIN " + CompanyDAO.TABLE_NAME + " ON " + CompanyDAO.TABLE_NAME + "." + CompanyDAO.COL_COMPANY_ID +
            "=" + ComputerDAO.TABLE_NAME + "." + ComputerDAO.COL_COMPUTER_COMPANY_ID;


    public static final String COUNT = "SELECT Count(*) FROM " + ComputerDAO.TABLE_NAME;

    public static final String WHERE_FILTER_ID = " WHERE " + ComputerDAO.TABLE_NAME + "." + ComputerDAO.COL_COMPUTER_ID + "=?";

    public static final String DELETE = "DELETE FROM " + ComputerDAO.TABLE_NAME + " WHERE " + ComputerDAO.TABLE_NAME + "." + ComputerDAO.COL_COMPUTER_ID + "=?";


    public static final String INSERT = "INSERT INTO " + ComputerDAO.TABLE_NAME + "(" + COL_COMPUTER_NAME + ","
            + COL_COMPUTER_INTRODUCED + "," + COL_COMPUTERDISCONTINUED + "," + COL_COMPUTER_COMPANY_ID + ") "
            + "VALUES (?,?,?,?)";


    public static final String UPDATE = "UPDATE " + TABLE_NAME + " SET " + ComputerDAO.COL_COMPUTER_NAME + " = ?,"
            + ComputerDAO.COL_COMPUTER_INTRODUCED + " = ?, "
            + ComputerDAO.COL_COMPUTERDISCONTINUED + " = ?, "
            + ComputerDAO.COL_COMPUTER_COMPANY_ID + " = ? "
            + "WHERE " + ComputerDAO.COL_COMPUTER_ID + "= ?";


    public static final String WHERE_NAME_FILTER = " WHERE " + TABLE_NAME + "." + COL_COMPUTER_NAME + " LIKE ?";

    public static final String SELECT_FILTER_NAME = SELECT + " " + WHERE_NAME_FILTER;

    public static final String COUNT_FILTER_NAME = COUNT + WHERE_NAME_FILTER;

    public static final String SELECT_LAST_COMPUTER_INSERTED = SELECT + " ORDER BY " + TABLE_NAME + "." + COL_COMPUTER_ID + " DESC LIMIT 1";

    public static final String LIMIT_PAGE = " LIMIT ? OFFSET ? ";

    ///////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////


    public static ComputerDAO dao = new ComputerDAO();

    private ComputerDAO() {

    }

    public static ComputerDAO getInstance() {
        return dao;
    }



    // When selecting multiple computer, if they belong to the same company, we make them point on the same Company object
    // Each company is build at most one time
    public static final HashMap<Integer, Company> CACHE_COMPANY = new HashMap<>();

    public List<Computer> getAll() throws DAOSelectException {
        CACHE_COMPANY.clear();
        Connection connection = null;
        ResultSet rs = null;
        List<Computer> result = null;

        try {
            connection = Connector.getDataSource().getConnection();
            rs = connection.prepareStatement(SELECT).executeQuery();
            result = MapperComputer.mapResultSetToObjects(rs);

            connection.close();
            LOGGER.info("Succes getAll computerdao");
            CACHE_COMPANY.clear();
            return result;
        } catch (Exception e) {
            LOGGER.info("Erreur getAll computerdao : " + e.getMessage());
            e.printStackTrace();
            CACHE_COMPANY.clear();
            throw new DAOSelectException("Computer", SELECT);
        }
    }

    public Computer getById(int id) throws DAOSelectException {
        CACHE_COMPANY.clear();
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        Computer result = null;

        try {
            connection = Connector.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement(SELECT + WHERE_FILTER_ID);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            result = MapperComputer.mapResultSetToObject(rs);

            preparedStatement.close();
            connection.close();
            LOGGER.info("Succes getbyid computerdao");
            CACHE_COMPANY.clear();
            return result;
        } catch (Exception e) {
            LOGGER.info("Erreur sql get by id : " + e.getMessage());
            e.printStackTrace();
            CACHE_COMPANY.clear();
            throw new DAOSelectException("Computer", SELECT + WHERE_FILTER_ID + " (id=" + id + ")");
        }
    }

    public int insert(Computer computer) throws DAOInsertException {
        CACHE_COMPANY.clear();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = Connector.getDataSource().getConnection();
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
                    connection.close();
                    LOGGER.info("Success insert computerdao : " + computer);
                    CACHE_COMPANY.clear();
                    return computer.getId();
                } else {
                    // No id returned
                    LOGGER.info("Erreur insert computerdao : " + computer);
                    statement.close();
                    connection.close();
                    throw new Exception();
                }

            } else {
                // Aucune ligne affectée
                LOGGER.info("Erreur insert 2 computerdao : " + computer);
                statement.close();
                connection.close();
                throw new Exception();
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("Erreur 3 insert computerdao : " + computer + " => " + e.getMessage());
        }

        CACHE_COMPANY.clear();
        throw new DAOInsertException(computer);
    }

    public boolean deleteById(int id) throws DAODeleteException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Connector.getDataSource().getConnection();
            statement = connection.prepareStatement(DELETE);
            statement.setInt(1, id);
            int resultExec = statement.executeUpdate();

            statement.close();
            connection.close();
            LOGGER.info("Succes delete " + id + " computerdao");
            return resultExec != 0;
        } catch (Exception e) {
            LOGGER.info("Error delete Computerdao " + id + " : " + e.getMessage());
            e.printStackTrace();
            throw new DAODeleteException("Computer", id);
        }

    }

    public boolean update(Computer computer) throws DAOUpdateException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Connector.getDataSource().getConnection();
            statement = connection.prepareStatement(UPDATE);
            statement.setString(1, computer.getName());
            statement.setDate(2, (computer.getIntroduced() != null ? new java.sql.Date(computer.getIntroduced().getTime()) : null));
            statement.setDate(3, (computer.getDiscontinued() != null ? new java.sql.Date(computer.getDiscontinued().getTime()) : null));
            statement.setString(4, (computer.getCompany() == null ? null : String.valueOf(computer.getCompany().getId())));
            statement.setInt(5, computer.getId());
            int resultExec = statement.executeUpdate();

            statement.close();
            connection.close();
            LOGGER.info("Succes Update Computerdao : " + computer);
            return resultExec != 0;
        } catch (Exception e) {
            System.out.println("Exce : " + e.getMessage());
            LOGGER.info("Error Update Computerdao : " + computer + " => " + e.getMessage());
        }
        throw new DAOUpdateException(computer);
    }


    public Computer getLastComputerInserted() throws DAOSelectException {
        CACHE_COMPANY.clear();
        Connection connection = null;
        ResultSet rs = null;
        Computer result = null;
        try {
            connection = Connector.getDataSource().getConnection();
            rs = connection.prepareStatement(SELECT_LAST_COMPUTER_INSERTED).executeQuery();
            result = MapperComputer.mapResultSetToObject(rs);

            connection.close();
            LOGGER.info("Succes getLastComputerInserted Computerdao");
            CACHE_COMPANY.clear();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("Error Get last computer inserted Computerdao : " + e.getMessage());
        }

        CACHE_COMPANY.clear();
        throw new DAOSelectException("Company", SELECT_LAST_COMPUTER_INSERTED);
    }


    public List<Computer> getPagination(int page, int numberOfResults, String filterName) throws DAOSelectException {
        CACHE_COMPANY.clear();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Computer> res = null;

        try {
            connection = Connector.getDataSource().getConnection();

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
            connection.close();
            LOGGER.info("Succes pagination Computerdao");
            CACHE_COMPANY.clear();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("Error Pagination Computerdao : " + e.getMessage());
        }

        CACHE_COMPANY.clear();
        throw new DAOSelectException("Company", SELECT + LIMIT_PAGE);
    }

    public Integer getCount(String searchByName) throws DAOCountException {
        Connection connection = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        Integer count = null;

        try {
            connection = Connector.getDataSource().getConnection();
            pr = connection.prepareStatement(COUNT_FILTER_NAME);
            pr.setString(1, "%" + (searchByName != null ? searchByName : "") + "%");
            rs = pr.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            connection.close();
            LOGGER.info("Success Count Computerdao ");
            return count;

        } catch (Exception e) {
            LOGGER.info("Error Count Computerdao : " + e.getMessage());
        }
        throw new DAOCountException("Computer");
    }


}
