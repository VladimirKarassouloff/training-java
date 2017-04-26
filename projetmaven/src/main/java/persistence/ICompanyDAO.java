package persistence;

import exception.DAOCountException;
import exception.DAOSelectException;
import exception.DAOUpdateException;
import model.Company;

import java.util.List;

/**
 * Created by vkarassouloff on 26/04/17.
 */
public interface ICompanyDAO {

    /**
     * Get all records.
     *
     * @return resultsetcli
     * @throws DAOSelectException if errors happened
     */
    public List<Company> getAll() throws DAOSelectException;

    /**
     * Get specific record from DB.
     *
     * @param id of the record returned
     * @return resultset
     * @throws DAOSelectException if error happens
     */
    public Company getById(int id) throws DAOSelectException;

    /**
     * Update the company having id = company.id.
     *
     * @param company attributes used for updating
     * @return success
     * @throws DAOUpdateException if error happens
     */
    public boolean update(Company company) throws DAOUpdateException;

    /**
     * Get the number of companies.
     *
     * @return number
     * @throws DAOCountException if error happens while getting row count
     */
    public Integer getCount() throws DAOCountException;

    /**
     * Result set of results.
     *
     * @param page            asked
     * @param numberOfResults per page
     * @return resultset
     * @throws DAOSelectException if error happens
     */
    public List<Company> getPagination(int page, int numberOfResults) throws DAOSelectException;




}
