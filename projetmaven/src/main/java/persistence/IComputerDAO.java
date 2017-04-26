package persistence;

import exception.DAOCountException;
import exception.DAODeleteException;
import exception.DAOInsertException;
import exception.DAOSelectException;
import exception.DAOUpdateException;
import model.Computer;

import java.util.List;

/**
 * Created by vkarassouloff on 26/04/17.
 */
public interface IComputerDAO {

    /**
     * Get all records.
     *
     * @return resultset for all recors
     * @throws DAOSelectException if error happens
     */
    public List<Computer> getAll() throws DAOSelectException;

    /**
     * Get Computer.
     *
     * @param id of the computer requested
     * @return resultset
     * @throws DAOSelectException if error happens
     */
    public Computer getById(int id) throws DAOSelectException;

    /**
     * Insert new record in DB.
     *
     * @param computer contains attributes for representation in DB
     * @return id of new row, -1 if failed
     * @throws DAOInsertException if error happens
     */
    public int insert(Computer computer) throws DAOInsertException;


    /**
     * Delete computer.
     *
     * @param id of the computer deleted
     * @return success
     * @throws DAODeleteException if error happens
     */
    public boolean deleteById(int id) throws DAODeleteException;

    /**
     * Updated computer.
     *
     * @param computer values used to update computer in DB
     * @return success
     * @throws DAOUpdateException if error happens
     */
    public boolean update(Computer computer) throws DAOUpdateException;


    /**
     * Get result set for the last computer inserted in the DB.
     *
     * @return resultset
     * @throws DAOSelectException if error happens
     */
    public Computer getLastComputerInserted() throws DAOSelectException;

    /**
     * Get Paged result.
     *
     * @param page            requested
     * @param numberOfResults per page
     * @param filterName      filter results by name
     * @return resultset of the page asked
     * @throws DAOSelectException if error happens
     */
    public List<Computer> getPagination(int page, int numberOfResults, String filterName) throws DAOSelectException;

    /***
     * Get computer count.
     * @param searchByName nullable parameter to research computer by name
     * @throws DAOCountException if error happens
     * @return count of computer considering filters
     */
    public Integer getCount(String searchByName) throws DAOCountException;





}
