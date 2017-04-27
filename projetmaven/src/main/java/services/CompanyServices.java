package services;

import exception.DAOCountException;
import exception.DAOSelectException;
import exception.DAOUpdateException;
import model.Company;
import persistence.CompanyDAO;
import validator.CompanyValidator;

import java.util.ArrayList;
import java.util.List;

public class CompanyServices implements ICompanyServices {


    private static CompanyServices service = new CompanyServices();

    private CompanyDAO companyDao;

    /**
     * Get singleton.
     */
    private CompanyServices() {
        companyDao = CompanyDAO.getInstance();
    }

    public static CompanyServices getInstance() {
        return service;
    }

    @Override
    public List<Company> getCompanies() {
        try {
            return companyDao.getAll();
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Company> getPagedCompany(int page, int numberItem) {
        try {
            return companyDao.getPagination(page, numberItem);
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public int getCountCompany() {
        try {
            return companyDao.getCount();
        } catch (DAOCountException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Company getCompany(int id) {
        try {
            return companyDao.getById(id);
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateCompany(Company company) {
        if (!CompanyValidator.isValid(company)) {
            return false;
        }
        try {
            return companyDao.update(company);
        } catch (DAOUpdateException e) {
            e.printStackTrace();
            return false;
        }
    }

}
