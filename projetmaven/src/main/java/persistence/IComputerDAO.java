package persistence;

import exception.DAOCountException;
import exception.DAODeleteException;
import exception.DAOInsertException;
import exception.DAOSelectException;
import exception.DAOUpdateException;
import model.Computer;
import persistence.filter.FilterSelect;

import java.util.List;

/**
 * Created by vkarassouloff on 26/04/17.
 */
interface IComputerDAO {

    /**
     * Get all records.
     *
     * @return resultset for all recors
     * @throws DAOSelectException if error happens
     */
    List<Computer> getAll() throws DAOSelectException;

    /**
     * Get Computer.
     *
     * @param id of the computer requested
     * @return resultset
     * @throws DAOSelectException if error happens
     */
    Computer getById(int id) throws DAOSelectException;

    /**
     * Insert new record in DB.
     *
     * @param computer contains attributes for representation in DB
     * @return id of new row
     * @throws DAOInsertException if error happens
     */
    int insert(Computer computer) throws DAOInsertException;


    /**
     * Delete computer.
     *
     * @param id of the computer deleted
     * @return success
     * @throws DAODeleteException if error happens
     */
    boolean deleteById(int id) throws DAODeleteException;

    /**
     * Updated computer.
     *
     * @param computer values used to update computer in DB
     * @return success
     * @throws DAOUpdateException if error happens
     */
    boolean update(Computer computer) throws DAOUpdateException;


    /**
     * Get result set for the last computer inserted in the DB.
     *
     * @return resultset
     * @throws DAOSelectException if error happens
     */
    Computer getLastComputerInserted() throws DAOSelectException;

    /**
     * Get Paged result.
     *
     * @param page            requested
     * @param numberOfResults per page
     * @param filterName      filter results by name
     * @return resultset of the page asked
     * @throws DAOSelectException if error happens
     */
    List<Computer> getPagination(int page, int numberOfResults, String filterName) throws DAOSelectException;

    /***
     * Get computer count.
     * @param fs all filter for counting
     * @throws DAOCountException if error happens
     * @return count of computer considering filters
     */
    int getCount(FilterSelect fs) throws DAOCountException;


    /**
     * Get Computer matching filter.
     * @param fs filter
     * @return computers
     * @throws DAOSelectException if an error occurs with DB
     */
    List<Computer> getFromFilter(FilterSelect fs) throws DAOSelectException;

    /**
     * Delete computers belonging to a company.
     * @param idCompany of the computer deleted
     */
    void deleteComputerBelongingToCompany(int idCompany) throws DAODeleteException;

}
