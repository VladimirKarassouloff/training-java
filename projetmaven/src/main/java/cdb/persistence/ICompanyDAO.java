package cdb.persistence;

import cdb.exception.DAOCountException;
import cdb.exception.DAODeleteException;
import cdb.exception.DAOInsertException;
import cdb.exception.DAOSelectException;
import cdb.exception.DAOUpdateException;
import cdb.model.Company;

import java.util.List;

/**
 * Created by vkarassouloff on 26/04/17.
 */
interface ICompanyDAO {

    /**
     * Get all records.
     *
     * @return resultsetcli
     * @throws DAOSelectException if errors happened
     */
    List<Company> getAll() throws DAOSelectException;

    /**
     * Get specific record from DB.
     *
     * @param id of the record returned
     * @return resultset
     * @throws DAOSelectException if error happens
     */
    Company getById(int id) throws DAOSelectException;

    /**
     * Update the company having id = company.id.
     *
     * @param company attributes used for updating
     * @return success
     * @throws DAOUpdateException if error happens
     */
    boolean update(Company company) throws DAOUpdateException;

    /**
     * Get the number of companies.
     *
     * @return number
     * @throws DAOCountException if error happens while getting row count
     */
    Integer getCount() throws DAOCountException;

    /**
     * Result set of results.
     *
     * @param page            asked
     * @param numberOfResults per page
     * @return resultset
     * @throws DAOSelectException if error happens
     */
    List<Company> getPagination(int page, int numberOfResults) throws DAOSelectException;


    /**
     * Insert new record in DB.
     *
     * @param company contains attributes for representation in DB
     * @throws DAOInsertException if error happens
     */
    void insert(Company company) throws DAOInsertException;

    /**
     * Delete company.
     *
     * @param id of the company deleted
     * @return success
     * @throws DAODeleteException if error happens
     */
    boolean delete(int id) throws DAODeleteException;

}
