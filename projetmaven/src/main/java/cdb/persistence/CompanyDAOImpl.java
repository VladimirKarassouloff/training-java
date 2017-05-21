package cdb.persistence;

import cdb.exception.DAOCountException;
import cdb.exception.DAODeleteException;
import cdb.exception.DAOInsertException;
import cdb.exception.DAOSelectException;
import cdb.exception.DAOUpdateException;
import cdb.mapper.MapperCompany;
import cdb.model.Company;
import cdb.utils.SqlNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;

@Repository()
public class CompanyDAOImpl implements ICompanyDAO {


    /////////////////////////////////////////////////////////
    /////Query parts

    public static final String SELECT = "SELECT * FROM " + SqlNames.COMPANY_TABLE_NAME;

    public static final String SELECT_LAST_COMPANY_INSERTED = SELECT + " ORDER BY " + SqlNames.COMPANY_TABLE_NAME + "." + SqlNames.COMPANY_COL_COMPANY_ID + " DESC LIMIT 1";

    public static final String COUNT = "SELECT Count(*) FROM " + SqlNames.COMPANY_TABLE_NAME;

    public static final String WHERE_FILTER_ID = " WHERE " + SqlNames.COMPANY_COL_COMPANY_ID + "= ?";

    public static final String UPDATE = "UPDATE " + SqlNames.COMPANY_TABLE_NAME + " SET " + SqlNames.COMPANY_COL_COMPANY_NAME + "= ?  WHERE "
            + SqlNames.COMPANY_COL_COMPANY_ID + "= ? ";

    public static final String INSERT = "INSERT INTO " + SqlNames.COMPANY_TABLE_NAME + "(" + SqlNames.COMPANY_COL_COMPANY_NAME + ") "
            + "VALUES (?)";

    public static final String DELETE = "DELETE FROM " + SqlNames.COMPANY_TABLE_NAME + " WHERE " + SqlNames.COMPANY_TABLE_NAME + "." + SqlNames.COMPANY_COL_COMPANY_ID + "=?";

    ///////////////////
    //////////////////////////////////////////////////////////

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAOImpl.class);

    public static final int[] TYPES_UPDATE = new int[]{Types.VARCHAR, Types.INTEGER};
    public static final int[] TYPES_INSERT = new int[]{Types.VARCHAR};
    public static final int[] TYPES_DELETE = new int[]{Types.INTEGER};


    private final JdbcTemplate jdbcTemplate;
    private final MapperCompany mapper;

    /**
     * Default constructor.
     *
     * @param dataSource from the DB
     * @param mapper used for turning resultsset into companies
     */
    @Autowired
    public CompanyDAOImpl(DataSource dataSource, MapperCompany mapper) {
        this.mapper = mapper;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Company> getAll() throws DAOSelectException {
        try {
            return jdbcTemplate.query(SELECT, mapper);
        } catch (DataAccessException e) {
            LOGGER.info("CompanyDAOImpl : Error getAll companyDao : " + e.getMessage());
            throw new DAOSelectException(SqlNames.COMPANY_TABLE_NAME, SELECT);
        }
    }

    @Override
    public Company get(int id) throws DAOSelectException {
        try {
            return jdbcTemplate.queryForObject(SELECT + WHERE_FILTER_ID, new Object[]{id}, mapper);
        } catch (DataAccessException e) {
            LOGGER.info("CompanyDAOImpl : Error sql get by id : " + e.getMessage());
            throw new DAOSelectException(SqlNames.COMPUTER_TABLE_NAME, SELECT + WHERE_FILTER_ID + " (id=" + id + ") => " + e.getMessage());
        }
    }

    @Override
    public boolean update(Company company) throws DAOUpdateException {
        try {
            Object[] params = new Object[]{
                    company.getName(),
                    company.getId()
            };

            return jdbcTemplate.update(UPDATE, params, TYPES_UPDATE) != 0;
        } catch (DataAccessException e) {
            LOGGER.info("CompanyDAO : Error insert SQL : " + company + " => " + e.getMessage());
            throw new DAOUpdateException(company);
        }
    }

    @Override
    public Integer getCount() throws DAOCountException {
        try {
            return jdbcTemplate.queryForObject(COUNT, Integer.class);
        } catch (DataAccessException e) {
            LOGGER.info("CompanyDAOImpl : Error getCount => " + e.getMessage());
            throw new DAOCountException("Company");
        }
    }

    @Override
    public List<Company> getPagination(int page, int numberOfResults) throws DAOSelectException {
        StringBuilder sb = new StringBuilder(SELECT);
        sb.append(" LIMIT ").append(numberOfResults).append(" OFFSET ").append((page * numberOfResults));

        try {
            return jdbcTemplate.query(sb.toString(), mapper);
        } catch (DataAccessException e) {
            LOGGER.info("CompanyDAOImpl : Error getPagination => " + e.getMessage());
            throw new DAOSelectException("Company", sb.toString());
        }
    }


    @Override
    public void insert(Company company) throws DAOInsertException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(INSERT, new Object[]{company.getName()}, TYPES_INSERT);
        } catch (DataAccessException e) {
            LOGGER.info("CompanyDAOImpl : Error insert SQL : " + company + " => " + e.getMessage());
            throw new DAOInsertException(company);
        }
    }


    @Override
    public boolean delete(int id) throws DAODeleteException {
        try {
            return jdbcTemplate.update(DELETE, new Object[]{id}, TYPES_DELETE) != 0;
        } catch (DataAccessException e) {
            LOGGER.info("CompanyDAOImpl : Error delete " + id + " => " + e.getMessage());
            throw new DAODeleteException(SqlNames.COMPANY_TABLE_NAME, id);
        }
    }

    @Override
    public Company getLastCompanyInserted() throws DAOSelectException {
        try {
            return jdbcTemplate.queryForObject(SELECT_LAST_COMPANY_INSERTED, mapper);
        } catch (DataAccessException e) {
            LOGGER.info("CompanyDAOImpl : Error Get last company inserted : " + e.getMessage());
            throw new DAOSelectException(SqlNames.COMPANY_TABLE_NAME, SELECT_LAST_COMPANY_INSERTED);
        }
    }

}
