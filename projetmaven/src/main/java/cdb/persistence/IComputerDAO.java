package cdb.persistence;

import cdb.exception.DAOCountException;
import cdb.exception.DAODeleteException;
import cdb.exception.DAOInsertException;
import cdb.exception.DAOSelectException;
import cdb.exception.DAOUpdateException;
import cdb.model.Computer;
import cdb.persistence.filter.FilterSelect;

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
    Computer get(int id) throws DAOSelectException;

    /**
     * Insert new record in DB.
     *
     * @param computer contains attributes for representation in DB
     * @throws DAOInsertException if error happens
     */
    void insert(Computer computer) throws DAOInsertException;


    /**
     * Delete computer.
     *
     * @param ids of the computer deleted
     * @return success
     * @throws DAODeleteException if error happens
     */
    boolean delete(int... ids) throws DAODeleteException;

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


    /***
     * Get computer count.
     * @param fs all filter for counting
     * @throws DAOCountException if error happens
     * @return count of computer considering filters
     */
    int getCount(FilterSelect fs) throws DAOCountException;


    /**
     * Get Computer matching filter.
     *
     * @param fs filter
     * @return computers
     * @throws DAOSelectException if an error occurs with DB
     */
    List<Computer> getFromFilter(FilterSelect fs) throws DAOSelectException;

    /**
     * Delete computers belonging to a company.
     * @param idCompany of the computer deleted
     * @throws DAODeleteException if error happens
     */
    void deleteComputerBelongingToCompany(int idCompany) throws DAODeleteException;

}
