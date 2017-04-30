package services;

import exception.*;
import model.Company;
import persistence.CompanyDAO;
import persistence.ComputerDAO;
import persistence.Connector;
import validator.CompanyValidator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyServices implements ICompanyServices {


    private static CompanyServices service = new CompanyServices();

    private CompanyDAO companyDao;
    private ComputerDAO computerDao;
    /**
     * Get singleton.
     */
    private CompanyServices() {
        companyDao = CompanyDAO.getInstance();
        computerDao = ComputerDAO.getInstance();
    }

    public static CompanyServices getInstance() {
        return service;
    }

    public void setComputerDao(ComputerDAO computerDao) {
        this.computerDao = computerDao;
    }

    @Override
    public List<Company> getCompanies() {
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            List<Company> results = companyDao.getAll();
            TransactionHolder.get().commit();
            return results;
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
            List<Company> results = companyDao.getPagination(page, numberItem);
            TransactionHolder.get().commit();
            return results;
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
    public int getCountCompany() {
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            int result = companyDao.getCount();
            TransactionHolder.get().commit();
            return result;
        } catch (SQLException | DAOCountException e) {
            e.printStackTrace();
            Connector.getInstance().rollback(TransactionHolder.get());
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
            Company result = companyDao.getById(id);
            TransactionHolder.get().commit();
            return result;
        } catch (SQLException | DAOSelectException e) {
            e.printStackTrace();
            Connector.getInstance().rollback(TransactionHolder.get());
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
            boolean result = companyDao.update(company);
            TransactionHolder.get().commit();
            return result;
        } catch (SQLException | DAOUpdateException e) {
            e.printStackTrace();
            Connector.getInstance().rollback(TransactionHolder.get());
            return false;
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
        }
    }

    public void delete(int id) {
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            companyDao.delete(id);
            computerDao.deleteComputerBelongingToCompany(id);
            TransactionHolder.get().commit();
        } catch (SQLException | DAODeleteException e) {
            e.printStackTrace();
            Connector.getInstance().rollback(TransactionHolder.get());
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
        }
    }



}
