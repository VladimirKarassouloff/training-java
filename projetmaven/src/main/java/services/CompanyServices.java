package services;

import exception.DAOCountException;
import exception.DAOSelectException;
import exception.DAOUpdateException;
import model.Company;
import persistence.CompanyDAO;
import persistence.Connector;
import validator.CompanyValidator;

import java.sql.SQLException;
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
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            return companyDao.getAll();
        } catch (SQLException | DAOSelectException e) {
            e.printStackTrace();
            Connector.getInstance().rollback(TransactionHolder.get());
            return new ArrayList<>();
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
        }
    }

    @Override
    public List<Company> getPagedCompany(int page, int numberItem) {
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            return companyDao.getPagination(page, numberItem);
        } catch (SQLException | DAOSelectException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
        }
    }

    @Override
    public int getCountCompany() {
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            return companyDao.getCount();
        } catch (SQLException | DAOCountException e) {
            e.printStackTrace();
            return 0;
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
        }
    }

    @Override
    public Company getCompany(int id) {
        if (id < 0) {
            return null;
        }

        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            return companyDao.getById(id);
        } catch (SQLException | DAOSelectException e) {
            e.printStackTrace();
            return null;
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
        }
    }

    @Override
    public boolean updateCompany(Company company) {
        if (!CompanyValidator.isValid(company)) {
            return false;
        }
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            return companyDao.update(company);
        } catch (SQLException | DAOUpdateException e) {
            e.printStackTrace();
            return false;
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
        }
    }

}
