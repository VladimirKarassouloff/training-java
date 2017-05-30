package cdb.service;

import cdb.model.Company;
import cdb.persistence.IComputerDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by vkarassouloff on 26/04/17.
 */
public interface ICompanyService {

    /**
     * Get all companies in DB.
     *
     * @return companies
     */
    List<Company> getCompanies();

    /**
     * é
     * Get Page of campanies.
     *
     * @param page       number of page
     * @return companies
     */
    Page<Company> getPagedCompany(PageRequest page);

    /**
     * Get the total count of companies in DB.
     *
     * @return number of companoes
     */
    long getCountCompany();


    /**
     * Get a specific company.
     *
     * @param id of the company returned
     * @return company having the id or null
     */
    Company getCompany(long id);

    /**
     * Update the company in DB with the value of the company parameter.
     *
     * @param company updated
     * @return boolean for success
     */
    Company updateCompany(Company company);

    /**
     * Delete a companny and all her belongings computers.
     *
     * @param id of the company
     */
    void delete(long id);

    /**
     * Change DAO source for object.
     *
     * @param dao source
     */
    void setComputerDao(IComputerDAO dao);

    /**
     * Get the last inserted record.
     *
     * @return last record
     */
    Company getLastCompanyInserted();
}
