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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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


    ////////////////
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
    ///////////////////
    ///////////////////


    // Comme on construit l'object company pour chaque computer, on évite d'aller en base récuperer une Company pour chaque Computer et on fait
    // pointer les objets computer sur les meme objets company
    public static final HashMap<Integer, Company> CACHE_COMPANY = new HashMap<>();

    /**
     * Get all records.
     *
     * @return resultset for all recors
     * @throws DAOSelectException if error happens
     */
    public static List<Computer> getAll() throws DAOSelectException {
        CACHE_COMPANY.clear();
        try {
            ResultSet rs = Connector.getInstance().preparedStatement(SELECT).executeQuery();
            LOGGER.info("Succes getAll computerdao");
            CACHE_COMPANY.clear();
            return MapperComputer.mapResultSetToObjects(rs);
        } catch (Exception e) {
            LOGGER.info("Erreur getAll computerdao : " + e.getMessage());
            e.printStackTrace();
            CACHE_COMPANY.clear();
            throw new DAOSelectException("Computer", SELECT);
        }
    }

    /**
     * Get Computer.
     *
     * @param id of the computer requested
     * @return resultset
     * @throws DAOSelectException if error happens
     */
    public static Computer getById(int id) throws DAOSelectException {
        CACHE_COMPANY.clear();
        ResultSet obj = null;

        try {
            PreparedStatement preparedStatement = Connector.getInstance().preparedStatement(SELECT + WHERE_FILTER_ID);
            preparedStatement.setInt(1, id);
            obj = preparedStatement.executeQuery();
            LOGGER.info("Succes getbyid computerdao");
            CACHE_COMPANY.clear();
            return MapperComputer.mapResultSetToObject(obj);
        } catch (Exception e) {
            LOGGER.info("Erreur sql get by id : " + e.getMessage());
            e.printStackTrace();
            CACHE_COMPANY.clear();
            throw new DAOSelectException("Computer", SELECT + WHERE_FILTER_ID + " (id=" + id + ")");
        }
    }

    /**
     * Insert new record in DB.
     *
     * @param computer contains attributes for representation in DB
     * @return id of new row, -1 if failed
     * @throws DAOInsertException if error happens
     */
    public static int insert(Computer computer) throws DAOInsertException {
        try {
            CACHE_COMPANY.clear();

            PreparedStatement statement = Connector.getInstance().preparedStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, computer.getName());
            statement.setDate(2, (computer.getIntroduced() != null ? new java.sql.Date(computer.getIntroduced().getTime()) : null));
            statement.setDate(3, (computer.getDiscontinued() != null ? new java.sql.Date(computer.getDiscontinued().getTime()) : null));
            statement.setString(4, computer.getCompany() != null ? String.valueOf(computer.getCompany().getId()) : null);

            if (statement.executeUpdate() != 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int newIdGenerated = (int) generatedKeys.getLong(1);
                    computer.setId(newIdGenerated);
                    statement.close();
                    LOGGER.info("Success insert computerdao : " + computer);
                    CACHE_COMPANY.clear();
                    return newIdGenerated;
                } else {
                    LOGGER.info("Erreur insert computerdao : " + computer);
                    statement.close();
                    CACHE_COMPANY.clear();
                    throw new Exception();
                }

            } else {
                // Aucune ligne affectée
                LOGGER.info("Erreur insert 2 computerdao : " + computer);
                statement.close();
                CACHE_COMPANY.clear();
                throw new Exception();
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("Erreur 3 insert computerdao : " + computer + " => " + e.getMessage());
        }

        CACHE_COMPANY.clear();
        throw new DAOInsertException(computer);
    }

    /**
     * Delete computer.
     *
     * @param id of the computer deleted
     * @return success
     * @throws DAODeleteException if error happens
     */
    public static boolean deleteById(int id) throws DAODeleteException {

        try {
            PreparedStatement statement = Connector.getInstance().preparedStatement(DELETE);
            statement.setInt(1, id);
            int resultExec = statement.executeUpdate();
            statement.close();
            LOGGER.info("Succes delete " + id + " computerdao");
            return resultExec != 0;
        } catch (Exception e) {
            LOGGER.info("Error delete Computerdao " + id + " : " + e.getMessage());
            e.printStackTrace();
            throw new DAODeleteException("Computer", id);
        }

    }

    /**
     * Updated computer.
     *
     * @param computer values used to update computer in DB
     * @return success
     * @throws DAOUpdateException if error happens
     */
    public static boolean update(Computer computer) throws DAOUpdateException {
        try {
            PreparedStatement statement = Connector.getInstance().preparedStatement(UPDATE);
            statement.setString(1, computer.getName());
            statement.setDate(2, (computer.getIntroduced() != null ? new java.sql.Date(computer.getIntroduced().getTime()) : null));
            statement.setDate(3, (computer.getDiscontinued() != null ? new java.sql.Date(computer.getDiscontinued().getTime()) : null));
            statement.setString(4, (computer.getCompany() == null ? null : String.valueOf(computer.getCompany().getId())));
            statement.setInt(5, computer.getId());

            int resultExec = statement.executeUpdate();
            statement.close();
            LOGGER.info("Succes Update Computerdao : " + computer);
            return resultExec != 0;
        } catch (Exception e) {
            System.out.println("Exce : " + e.getMessage());
            LOGGER.info("Error Update Computerdao : " + computer + " => " + e.getMessage());
        }
        throw new DAOUpdateException(computer);
    }


    /**
     * Get result set for the last computer inserted in the DB.
     *
     * @return resultset
     * @throws DAOSelectException if error happens
     */
    public static Computer getLastComputerInserted() throws DAOSelectException {
        CACHE_COMPANY.clear();
        try {
            ResultSet rs = null;
            rs = Connector.getInstance().preparedStatement(SELECT_LAST_COMPUTER_INSERTED).executeQuery();
            LOGGER.info("Succes getLastComputerInserted Computerdao");
            CACHE_COMPANY.clear();
            return MapperComputer.mapResultSetToObject(rs);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("Error Get last computer inserted Computerdao : " + e.getMessage());
        }

        CACHE_COMPANY.clear();
        throw new DAOSelectException("Company", SELECT_LAST_COMPUTER_INSERTED);
    }


    /**
     * Get Paged result.
     *
     * @param page            requested
     * @param numberOfResults per page
     * @param filterName      filter results by name
     * @return resultset of the page asked
     * @throws DAOSelectException if error happens
     */
    public static List<Computer> getPagination(int page, int numberOfResults, String filterName) throws DAOSelectException {
        CACHE_COMPANY.clear();
        String limitPage = " LIMIT " + numberOfResults + " OFFSET " + (page * numberOfResults);

        try {
            ResultSet rs = null;
            if (filterName == null) {
                rs = Connector.getInstance().preparedStatement(SELECT + limitPage).executeQuery();
            } else {
                PreparedStatement ps = Connector.getInstance().preparedStatement(SELECT_FILTER_NAME + limitPage);
                ps.setString(1, "%" + filterName + "%");
                rs = ps.executeQuery();
            }
            LOGGER.info("Succes pagination Computerdao");
            CACHE_COMPANY.clear();
            return MapperComputer.mapResultSetToObjects(rs);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("Error Pagination Computerdao : " + e.getMessage());
        }

        CACHE_COMPANY.clear();
        throw new DAOSelectException("Company", SELECT + limitPage);
    }

    /***
     * Get computer count.
     * @param searchByName nullable parameter to research computer by name
     * @throws DAOCountException if error happens
     * @return count of computer considering filters
     */
    public static Integer getCount(String searchByName) throws DAOCountException {
        try {
            PreparedStatement pr = Connector.getInstance().preparedStatement(COUNT_FILTER_NAME);
            pr.setString(1, "%" + (searchByName != null ? searchByName : "") + "%");
            ResultSet rs = pr.executeQuery();

            Integer count = null;
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            LOGGER.info("Success Count Computerdao ");
            return count;

        } catch (Exception e) {
            LOGGER.info("Error Count Computerdao : " + e.getMessage());
        }
        throw new DAOCountException("Computer");
    }


}
