package persistence;

import com.mysql.jdbc.PreparedStatement;
import exception.DAOCountException;
import exception.DAOSelectException;
import exception.DAOUpdateException;
import model.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

    public static final String TABLE_NAME = "company";
    public static final String COL_COMPANY_ID = "id";
    public static final String COL_COMPANY_NAME = "name";

    ////////////////
    /////Query parts

    public static final String SELECT = "SELECT * FROM " + CompanyDAO.TABLE_NAME;

    public static final String COUNT = "SELECT Count(*) FROM " + CompanyDAO.TABLE_NAME;

    public static final String WHERE_FILTER_ID = " WHERE " + CompanyDAO.COL_COMPANY_ID + "= ?";

    public static final String UPDATE = "UPDATE " + TABLE_NAME + " SET " + CompanyDAO.COL_COMPANY_NAME + "= ?  WHERE "
            + CompanyDAO.COL_COMPANY_ID + "= ? ";

    ///////////////////
    ///////////////////

    /**
     * Get all records.
     *
     * @return resultsetcli
     * @throws DAOSelectException if errors happened
     */
    public static ResultSet getAll() throws DAOSelectException {
        try {
            ResultSet rs = Connector.getInstance().preparedStatement(SELECT).executeQuery();
            LOGGER.info("Succes getAll CompanyDAO");
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("Error getAll CompanyDAO : " + e.getMessage());
            throw new DAOSelectException("Company", SELECT);
        }
    }

    /**
     * Get specific record from DB.
     *
     * @param id of the record returned
     * @return resultset
     * @throws DAOSelectException if error happens
     */
    public static ResultSet getById(int id) throws DAOSelectException {
        ResultSet obj = null;

        try {
            PreparedStatement preparedStatement = Connector.getInstance().preparedStatement(SELECT + WHERE_FILTER_ID);
            preparedStatement.setInt(1, id);
            obj = preparedStatement.executeQuery();
            if (obj != null) {
                LOGGER.info("Succes getById CompanyDAO => id = " + id);
            } else {
                LOGGER.info("Error getById CompanyDAO => null value => id = " + id);
                throw new DAOSelectException("Company", SELECT + WHERE_FILTER_ID + " (id = " + id + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info("Error getById CompanyDAO : " + e.getMessage() + " => id = " + id);
        }

        return obj;
    }

    /**
     * Update the company having id = company.id.
     *
     * @param company attributes used for updating
     * @return success
     * @throws DAOUpdateException if error happens
     */
    public static boolean update(Company company) throws DAOUpdateException {
        try {
            PreparedStatement preparedStatement = Connector.getInstance().preparedStatement(UPDATE);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setInt(2, company.getId());

            int resExec = preparedStatement.executeUpdate();
            preparedStatement.close();
            LOGGER.info("Succes Update CompanyDAO " + company);
            return resExec != 0;
        } catch (Exception e) {
            LOGGER.info("Error Update CompanyDAO : " + e.getMessage() + " " + company);
            throw new DAOUpdateException(company);
        }
    }

    /**
     * Get the number of companies.
     *
     * @return number
     * @throws DAOCountException if error happens while getting row count
     */
    public static Integer getCount() throws DAOCountException {
        try {
            ResultSet rs = Connector.getInstance().preparedStatement(COUNT).executeQuery();
            Integer count = null;

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            LOGGER.info("Succes getCount CompanyDAO ");
            return count;
        } catch (Exception e) {
            LOGGER.info("Error getCount CompanyDAO : " + e.getMessage());
            throw new DAOCountException("Company");
        }
    }

    /**
     * Result set of results.
     *
     * @param page            asked
     * @param numberOfResults per page
     * @return resultset
     * @throws DAOSelectException if error happens
     */
    public static ResultSet getPagination(int page, int numberOfResults) throws DAOSelectException {
        try {
            ResultSet rs = Connector.getInstance().preparedStatement(SELECT + " LIMIT " + numberOfResults + " OFFSET " + (page * numberOfResults)).executeQuery();
            LOGGER.info("Succes getPagination CompanyDAO ");
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info("Error getPagination CompanyDAO : " + e.getMessage());
            throw new DAOSelectException("Company", SELECT + " LIMIT " + numberOfResults + " OFFSET " + (page * numberOfResults));
        }
    }

}
