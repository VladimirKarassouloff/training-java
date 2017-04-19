package services;

import exception.DAOCountException;
import exception.DAOSelectException;
import exception.DAOUpdateException;
import mapper.MapperCompany;
import model.Company;
import persistence.CompanyDAO;
import validator.CompanyValidator;

import java.util.ArrayList;
import java.util.List;

public class CompanyServices {

    /**
     * Get all companies in DB.
     * @return companies
     */
    public static List<Company> getCompanies() {
        try {
            return MapperCompany.mapResultSetToObjects(CompanyDAO.getAll());
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return new ArrayList<Company>();
        }
    }

    /**
     * Get Page of campanies.
     * @param page       number of page
     * @param numberItem number of result per page
     * @return companies
     */
    public static List<Company> getPagedCompany(int page, int numberItem) {
        try {
            return MapperCompany.mapResultSetToObjects(CompanyDAO.getPagination(page, numberItem));
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return new ArrayList<Company>();
        }
    }

    /**
     * Get the total count of companies in DB.
     *
     * @return number of companoes
     */
    public static int getCountCompany() {
        try {
            return CompanyDAO.getCount();
        } catch (DAOCountException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Get a specific company.
     *
     * @param id of the company returned
     * @return company having the id or null
     */
    public static Company getCompany(int id) {
        try {
            return MapperCompany.mapResultSetToObject(CompanyDAO.getById(id));
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Update the company in DB with the value of the company parameter.
     *
     * @param company updated
     * @return boolean for success
     */
    public static boolean updateCompany(Company company) {
        if (!CompanyValidator.isValid(company)) {
            return false;
        }
        try {
            return CompanyDAO.update(company);
        } catch (DAOUpdateException e) {
            e.printStackTrace();
            return false;
        }
    }

}
