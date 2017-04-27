package services;

import model.Company;

import java.util.List;

/**
 * Created by vkarassouloff on 26/04/17.
 */
interface ICompanyServices {

    /**
     * Get all companies in DB.
     * @return companies
     */
    List<Company> getCompanies();

    /**
     * Get Page of campanies.
     * @param page       number of page
     * @param numberItem number of result per page
     * @return companies
     */
    List<Company> getPagedCompany(int page, int numberItem);

    /**
     * Get the total count of companies in DB.
     *
     * @return number of companoes
     */
    int getCountCompany();


    /**
     * Get a specific company.
     *
     * @param id of the company returned
     * @return company having the id or null
     */
    Company getCompany(int id);

    /**
     * Update the company in DB with the value of the company parameter.
     *
     * @param company updated
     * @return boolean for success
     */
    boolean updateCompany(Company company);
}
