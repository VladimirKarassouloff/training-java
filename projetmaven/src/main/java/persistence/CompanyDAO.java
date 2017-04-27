package persistence;

import exception.DAOCountException;
import exception.DAOSelectException;
import exception.DAOUpdateException;
import mapper.MapperCompany;
import model.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CompanyDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

    public static final String TABLE_NAME = "company";
    public static final String COL_COMPANY_ID = "id";
    public static final String COL_COMPANY_NAME = "name";


    /////////////////////////////////////////////////////////
    /////Query parts

    public static final String SELECT = "SELECT * FROM " + CompanyDAO.TABLE_NAME;

    public static final String COUNT = "SELECT Count(*) FROM " + CompanyDAO.TABLE_NAME;

    public static final String WHERE_FILTER_ID = " WHERE " + CompanyDAO.COL_COMPANY_ID + "= ?";

    public static final String UPDATE = "UPDATE " + TABLE_NAME + " SET " + CompanyDAO.COL_COMPANY_NAME + "= ?  WHERE "
            + CompanyDAO.COL_COMPANY_ID + "= ? ";

    ///////////////////
    //////////////////////////////////////////////////////////



    public static CompanyDAO dao = new CompanyDAO();

    public Connector connector;

    private CompanyDAO() {
        connector = Connector.getInstance();
    }

    public static CompanyDAO getInstance() {
        return dao;
    }

    public List<Company> getAll() throws DAOSelectException {
        Connection connection = null;
        ResultSet rs = null;
        List<Company> result = null;

        try {
            connection = connector.getDataSource().getConnection();
            rs = connection.prepareStatement(SELECT).executeQuery();
            connection.commit();
            LOGGER.info("Succes getAll CompanyDAO");
            result = MapperCompany.mapResultSetToObjects(rs);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info("Error getAll CompanyDAO : " + e.getMessage());
            connector.rollback(connection);
            throw new DAOSelectException("Company", SELECT);
        }
    }

    public Company getById(int id) throws DAOSelectException {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Company result = null;

        try {
            connection = connector.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement(SELECT + WHERE_FILTER_ID);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            result = MapperCompany.mapResultSetToObject(rs);
            connection.commit();

            preparedStatement.close();
            connection.close();
            LOGGER.info("Error getById CompanyDAO => null value => id = " + id);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info("Error getById CompanyDAO : " + e.getMessage() + " => id = " + id);
            connector.rollback(connection);
        }

        return null;
    }

    public boolean update(Company company) throws DAOUpdateException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connector.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setInt(2, company.getId());
            connection.commit();
            int resExec = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
            LOGGER.info("Succes Update CompanyDAO " + company);
            return resExec != 0;
        } catch (SQLException e) {
            LOGGER.info("Error Update CompanyDAO : " + e.getMessage() + " " + company);
            connector.rollback(connection);
            throw new DAOUpdateException(company);
        }
    }

    public Integer getCount() throws DAOCountException {
        Connection connection = null;
        ResultSet rs = null;
        Integer count = null;

        try {
            connection = connector.getDataSource().getConnection();
            rs = connection.prepareStatement(COUNT).executeQuery();
            connection.commit();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            connection.close();
            LOGGER.info("Succes getCount CompanyDAO ");
            return count;
        } catch (Exception e) {
            LOGGER.info("Error getCount CompanyDAO : " + e.getMessage());
            connector.rollback(connection);
            throw new DAOCountException("Company");
        }
    }

    public List<Company> getPagination(int page, int numberOfResults) throws DAOSelectException {
        Connection connection = null;
        ResultSet rs = null;
        List<Company> res = null;

        try {
            connection = connector.getDataSource().getConnection();
            rs = connection.prepareStatement(SELECT + " LIMIT " + numberOfResults + " OFFSET " + (page * numberOfResults)).executeQuery();
            connection.commit();
            res = MapperCompany.mapResultSetToObjects(rs);

            connection.close();
            LOGGER.info("Succes getPagination CompanyDAO ");
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info("Error getPagination CompanyDAO : " + e.getMessage());
            connector.rollback(connection);
            throw new DAOSelectException("Company", SELECT + " LIMIT " + numberOfResults + " OFFSET " + (page * numberOfResults));
        }
    }

}
