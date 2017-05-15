package persistence;

import exception.DAOCountException;
import exception.DAODeleteException;
import exception.DAOInsertException;
import exception.DAOSelectException;
import exception.DAOUpdateException;
import mapper.MapperCompany;
import model.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SqlNames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CompanyDAO implements ICompanyDAO {


    /////////////////////////////////////////////////////////
    /////Query parts

    public static final String SELECT = "SELECT * FROM " + SqlNames.COMPANY_TABLE_NAME;

    public static final String COUNT = "SELECT Count(*) FROM " + SqlNames.COMPANY_TABLE_NAME;

    public static final String WHERE_FILTER_ID = " WHERE " + SqlNames.COMPANY_COL_COMPANY_ID + "= ?";

    public static final String UPDATE = "UPDATE " + SqlNames.COMPANY_TABLE_NAME + " SET " + SqlNames.COMPANY_COL_COMPANY_NAME + "= ?  WHERE "
            + SqlNames.COMPANY_COL_COMPANY_ID + "= ? ";

    public static final String INSERT = "INSERT INTO " + SqlNames.COMPANY_TABLE_NAME + "(" + SqlNames.COMPANY_COL_COMPANY_NAME + ") "
            + "VALUES (?)";

    public static final String DELETE = "DELETE FROM " + SqlNames.COMPANY_TABLE_NAME + " WHERE " + SqlNames.COMPANY_TABLE_NAME + "." + SqlNames.COMPANY_COL_COMPANY_ID + "=?";

    ///////////////////
    //////////////////////////////////////////////////////////

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

    private static CompanyDAO dao = new CompanyDAO();

    private Connector connector;

    /**
     * Get singleton.
     */
    private CompanyDAO() {
        connector = Connector.getInstance();
    }

    public static CompanyDAO getInstance() {
        return dao;
    }

    @Override
    public List<Company> getAll() throws DAOSelectException {
        Connection connection = null;
        ResultSet rs = null;
        List<Company> result = null;

        try {
            connection = connector.getConnection();
            rs = connection.prepareStatement(SELECT).executeQuery();
            LOGGER.info("Succes getAll CompanyDAO");
            result = MapperCompany.mapResultSetToObjects(rs);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info("Error getAll CompanyDAO : " + e.getMessage());
            throw new DAOSelectException("Company", SELECT);
        } finally {
            connector.closeIfNotTransactionnal();
        }
    }

    @Override
    public Company getById(int id) throws DAOSelectException {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Company result = null;

        try {
            connection = connector.getConnection();
            preparedStatement = connection.prepareStatement(SELECT + WHERE_FILTER_ID);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            result = MapperCompany.mapResultSetToObject(rs);
            preparedStatement.close();
            LOGGER.info("Error getById CompanyDAO => null value => id = " + id);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info("Error getById CompanyDAO : " + e.getMessage() + " => id = " + id);
        } finally {
            connector.closeIfNotTransactionnal();
        }

        return null;
    }

    @Override
    public boolean update(Company company) throws DAOUpdateException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connector.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setInt(2, company.getId());
            int resExec = preparedStatement.executeUpdate();
            preparedStatement.close();
            LOGGER.info("Succes Update CompanyDAO " + company);
            return resExec != 0;
        } catch (SQLException e) {
            LOGGER.info("Error Update CompanyDAO : " + e.getMessage() + " " + company);
            throw new DAOUpdateException(company);
        } finally {
            connector.closeIfNotTransactionnal();
        }
    }

    @Override
    public Integer getCount() throws DAOCountException {
        Connection connection = null;
        ResultSet rs = null;
        Integer count = null;

        try {
            connection = connector.getConnection();
            rs = connection.prepareStatement(COUNT).executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            LOGGER.info("Succes getCount CompanyDAO ");
            return count;
        } catch (Exception e) {
            LOGGER.info("Error getCount CompanyDAO : " + e.getMessage());
            throw new DAOCountException("Company");
        } finally {
            connector.closeIfNotTransactionnal();
        }
    }

    @Override
    public List<Company> getPagination(int page, int numberOfResults) throws DAOSelectException {
        Connection connection = null;
        ResultSet rs = null;
        List<Company> res = null;

        try {
            connection = connector.getConnection();
            rs = connection.prepareStatement(SELECT + " LIMIT " + numberOfResults + " OFFSET " + (page * numberOfResults)).executeQuery();
            res = MapperCompany.mapResultSetToObjects(rs);
            LOGGER.info("Succes getPagination CompanyDAO ");
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info("Error getPagination CompanyDAO : " + e.getMessage());
            throw new DAOSelectException("Company", SELECT + " LIMIT " + numberOfResults + " OFFSET " + (page * numberOfResults));
        } finally {
            connector.closeIfNotTransactionnal();
        }
    }


    @Override
    public void insert(Company company) throws DAOInsertException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = connector.getConnection();
            statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, company.getName());


            if (statement.executeUpdate() != 0) {
                generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    company.setId((int) generatedKeys.getLong(1));
                    statement.close();
                    LOGGER.info("Success insert companydao : " + company);
                } else {
                    // No id returned
                    LOGGER.info("Erreur insert companydao : " + company);
                    statement.close();
                    throw new DAOInsertException(company);
                }

            } else {
                // No row affected
                LOGGER.info("Erreur insert 2 companydao : " + company);
                statement.close();
                throw new DAOInsertException(company);
            }
        } catch (SQLException e2) {
            LOGGER.info("Erreur 4 insert SQL companydao : " + company + " => " + e2.getMessage());
            throw new DAOInsertException(company);
        } finally {
            connector.closeIfNotTransactionnal();
        }

    }

    @Override
    public boolean delete(int id) throws DAODeleteException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connector.getConnection();
            statement = connection.prepareStatement(DELETE);
            statement.setInt(1, id);
            int resultExec = statement.executeUpdate();
            statement.close();
            LOGGER.info("Succes delete " + id + " CompanyDao");
            return resultExec != 0;
        } catch (SQLException e) {
            LOGGER.info("Error delete CompanyDao " + id + " : " + e.getMessage());
            e.printStackTrace();
            throw new DAODeleteException(SqlNames.COMPANY_TABLE_NAME, id);
        } finally {
            connector.closeIfNotTransactionnal();
        }
    }
}
