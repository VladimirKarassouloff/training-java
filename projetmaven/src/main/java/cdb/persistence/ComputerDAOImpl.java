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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


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

    private final MapperComputer mapper;
    private final JdbcTemplate jdbcTemplate;


    public static final int[] TYPES_INSERT = new int[]{Types.LONGVARCHAR, Types.DATE, Types.DATE, Types.INTEGER};
    public static final int[] TYPES_UPDATE = new int[]{Types.VARCHAR, Types.DATE, Types.DATE, Types.INTEGER, Types.INTEGER};


    /**
     * Default constructor.
     *
     * @param dataSource from cdb DB
     * @param mapper     row mapper
     */
    @Autowired
    public ComputerDAOImpl(DataSource dataSource, MapperComputer mapper) {
        this.mapper = mapper;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Computer> getAll() throws DAOSelectException {
        try {
            return jdbcTemplate.query(SELECT, mapper);
        } catch (DataAccessException e) {
            LOGGER.info("Erreur getAll computerdao : " + e.getMessage());
            throw new DAOSelectException(SqlNames.COMPUTER_TABLE_NAME, SELECT);
        }
    }

    @Override
    public List<Computer> getFromFilter(FilterSelect fs) throws DAOSelectException {
        StringBuilder query = new StringBuilder(SELECT);

        // If we have at least one column filtered
        Iterator<String> it = fs.getFilteredColumns().iterator();
        if (it.hasNext()) {
            query.append(" WHERE ");
            while (it.hasNext()) {
                String col = it.next();
                query.append(col).append(" ").append(fs.getFilterValue(col).getOperator()).append(" ? ");
                if (it.hasNext()) {
                    query.append(" OR ");
                }
            }
        }

        // Looking for order
        if (fs.getOrderOnColumn() != null) {
            query.append(" ORDER BY ").append(fs.getOrderOnColumn()).append(" ").append((fs.isAsc() ? " ASC " : " DESC "));
        }

        // Looking for pagination
        if (fs.isPaginated()) {
            query.append(" LIMIT ").append(fs.getNumberOfResult()).append(" OFFSET ").append((fs.getPage() * fs.getNumberOfResult())).append(" ");
        }

        try {
            return jdbcTemplate.query(query.toString(), mapper, fs.getFilterValues().stream().map(Filter::getValue).collect(Collectors.toList()).toArray());
        } catch (DataAccessException e) {
            LOGGER.info("Erreur getFromFilter ComputerDAOImpl : " + e.getMessage() + " query built : " + query.toString());
            throw new DAOSelectException(SqlNames.COMPUTER_TABLE_NAME, "Computer Select From Filter Exception");
        }
    }

    @Override
    public Computer get(int id) throws DAOSelectException {
        try {
            return jdbcTemplate.queryForObject(SELECT + WHERE_FILTER_ID, new Object[]{id}, mapper);
        } catch (DataAccessException e) {
            LOGGER.info("Erreur sql get by id : " + e.getMessage());
            throw new DAOSelectException(SqlNames.COMPUTER_TABLE_NAME, SELECT + WHERE_FILTER_ID + " (id=" + id + ")");
        }
    }

    @Override
    public void insert(Computer computer) throws DAOInsertException {

        try {
            Object[] params = new Object[]{
                    computer.getName(),
                    (computer.getIntroduced() != null ? new java.sql.Date(computer.getIntroduced().getTime()) : null),
                    (computer.getDiscontinued() != null ? new java.sql.Date(computer.getDiscontinued().getTime()) : null),
                    (computer.getCompany() != null ? computer.getCompany().getId() : null)
            };

            jdbcTemplate.update(INSERT, params, TYPES_INSERT);
        } catch (DataAccessException e) {
            LOGGER.info("ComputerDAO : Erreur insert SQL computerdao : " + computer + " => " + e.getMessage());
            throw new DAOInsertException(computer);
        }
    }

    @Override
    public boolean delete(int... ids) throws DAODeleteException {
        if (ids == null || ids.length == 0) {
            LOGGER.info("Computerdao : Error delete nothing to delete");
            return false;
        }

        // Build query
        StringBuilder queryDelete = new StringBuilder(DELETE);
        for (int i = 0; i < ids.length; i++) {
            queryDelete.append(" ").append(SqlNames.COMPUTER_TABLE_NAME).append(".").append(SqlNames.COMPUTER_COL_COMPUTER_ID).append("=").append(ids[i]);
            if (i < ids.length - 1) {
                queryDelete.append(" OR ");
            }
        }

        // Exec query
        try {
            return jdbcTemplate.update(queryDelete.toString()) != 0;
        } catch (DataAccessException e) {
            LOGGER.info("Computerdao : Error delete " + ids + " : " + e.getMessage());
            throw new DAODeleteException(SqlNames.COMPUTER_TABLE_NAME, ids);
        }
    }

    @Override
    public boolean update(Computer computer) throws DAOUpdateException {

        try {
            Object[] params = new Object[]{
                    computer.getName(),
                    (computer.getIntroduced() != null ? new java.sql.Date(computer.getIntroduced().getTime()) : null),
                    (computer.getDiscontinued() != null ? new java.sql.Date(computer.getDiscontinued().getTime()) : null),
                    (computer.getCompany() != null ? computer.getCompany().getId() : null),
                    computer.getId()
            };

            return jdbcTemplate.update(UPDATE, params, TYPES_UPDATE) != 0;
        } catch (DataAccessException e) {
            LOGGER.info("ComputerDAO : Erreur insert SQL computerdao : " + computer + " => " + e.getMessage());
            throw new DAOUpdateException(computer);
        }
    }

    @Override
    public Computer getLastComputerInserted() throws DAOSelectException {
        try {
            return jdbcTemplate.queryForObject(SELECT_LAST_COMPUTER_INSERTED, mapper);
        } catch (DataAccessException e) {
            LOGGER.info("Error Get last computer inserted Computerdao : " + e.getMessage());
            throw new DAOSelectException(SqlNames.COMPUTER_TABLE_NAME, SELECT_LAST_COMPUTER_INSERTED);
        }
    }


    @Override
    public int getCount(FilterSelect fs) throws DAOCountException {
        StringBuilder query = new StringBuilder();
        query.append(COUNT);

        try {
            // If we have at least one column filtered
            Iterator<String> it = fs.getFilteredColumns().iterator();
            if (it.hasNext()) {
                query.append(" WHERE  ");
                while (it.hasNext()) {
                    String col = it.next();
                    query.append(col).append(" ").append(fs.getFilterValue(col).getOperator()).append(" ? ");
                    if (it.hasNext()) {
                        query.append(" OR  ");
                    }
                }
            }

            return jdbcTemplate.queryForObject(query.toString(), fs.getFilterValues().stream().map(Filter::getValue).collect(Collectors.toList()).toArray(), Integer.class);
        } catch (DataAccessException e) {
            LOGGER.info("Erreur count getFromFilter ComputerDAOImpl : " + e.getMessage() + " query built : " + query.toString());
            throw new DAOCountException("Computer");
        }
    }


    @Override
    public void deleteComputerBelongingToCompany(int idCompany) throws DAODeleteException {
        try {
            jdbcTemplate.update(DELETE_COMPUTER_OF_COMPANY, idCompany);
        } catch (DataAccessException e) {
            LOGGER.info("Error deleting computers of company " + idCompany + " : " + e.getMessage());
            throw new DAODeleteException(SqlNames.COMPUTER_TABLE_NAME, DELETE_COMPUTER_OF_COMPANY + " (idCompany = " + idCompany + ")");
        }
    }


}
