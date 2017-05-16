package cdb.services;

import cdb.exception.DAOCountException;
import cdb.exception.DAODeleteException;
import cdb.exception.DAOSelectException;
import cdb.exception.DAOUpdateException;
import cdb.model.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cdb.persistence.CompanyDAO;
import cdb.persistence.ComputerDAO;
import cdb.persistence.Connector;
import cdb.validator.CompanyValidator;

import java.util.ArrayList;
import java.util.List;

public class CompanyServices implements ICompanyServices {

    private final Logger LOGGER = LoggerFactory.getLogger(CompanyServices.class);

    private static CompanyServices service = new CompanyServices();

    private Connector connector;
    private CompanyDAO companyDao;
    private ComputerDAO computerDao;

    /**
     * Get singleton.
     */
    private CompanyServices() {
        companyDao = CompanyDAO.getInstance();
        computerDao = ComputerDAO.getInstance();
        connector = Connector.getInstance();
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
            return companyDao.getAll();
        } catch (DAOSelectException e) {
            LOGGER.info("CompanyServices : error while getting companies");
            return new ArrayList<>();
        }
    }

    @Override
    public List<Company> getPagedCompany(int page, int numberItem) {
        try {
            return companyDao.getPagination(page, numberItem);
        } catch (DAOSelectException e) {
            LOGGER.info("CompanyServices : error while getting paged companies");
            return new ArrayList<>();
        }
    }

    @Override
    public int getCountCompany() {
        try {
            return companyDao.getCount();
        } catch (DAOCountException e) {
            return 0;
        }
    }

    @Override
    public Company getCompany(int id) {
        if (id < 0) {
            return null;
        }

        try {
            return companyDao.getById(id);
        } catch (DAOSelectException e) {
            LOGGER.info("CompanyServices : error while getting company with id " + id);
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
            LOGGER.info("CompanyServices : error while updating company (" + company + ")");
            return false;
        }
    }

    @Override
    public void delete(int id) {
        try {
            connector.startTransaction();
            computerDao.deleteComputerBelongingToCompany(id);
            companyDao.delete(id);
            connector.commit();
            connector.closeConnection();
        } catch (DAODeleteException e) {
            LOGGER.info("CompanyServices : error while deleting company with id : " + id);
            connector.closeConnection();
        }
    }


}
