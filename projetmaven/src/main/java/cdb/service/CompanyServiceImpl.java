package cdb.service;

import cdb.exception.DAOCountException;
import cdb.exception.DAODeleteException;
import cdb.exception.DAOException;
import cdb.exception.DAOSelectException;
import cdb.exception.DAOUpdateException;
import cdb.model.Company;
import cdb.persistence.ICompanyDAO;
import cdb.persistence.IComputerDAO;
import cdb.validator.CompanyValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service()
public class CompanyServiceImpl implements ICompanyService {

    private final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private ICompanyDAO companyDao;

    private IComputerDAO computerDao;

    /**
     * Default constructor.
     *
     * @param computerDao data access object for computers
     * @param companyDao  data access object for companies
     */
    @Autowired
    private CompanyServiceImpl(IComputerDAO computerDao, ICompanyDAO companyDao) {
        this.computerDao = computerDao;
        this.companyDao = companyDao;
    }


    public void setCompanyDao(ICompanyDAO companyDao) {
        this.companyDao = companyDao;
    }

    public void setComputerDao(IComputerDAO computerDao) {
        this.computerDao = computerDao;
    }


    @Override
    public List<Company> getCompanies() {
        try {
            return companyDao.getAll();
        } catch (DAOSelectException e) {
            LOGGER.info("CompanyServiceImpl : error while getting companies");
            throw new RuntimeException("CompanyServiceImpl : Impossible to get companies");
        }
    }

    @Override
    public List<Company> getPagedCompany(int page, int numberItem) {
        try {
            return companyDao.getPagination(page, numberItem);
        } catch (DAOSelectException e) {
            LOGGER.info("CompanyServiceImpl : error while getting paged companies");
            throw new RuntimeException("CompanyServiceImpl : Impossible to get page of companies");
        }
    }

    @Override
    public int getCountCompany() {
        try {
            return companyDao.getCount();
        } catch (DAOCountException e) {
            LOGGER.info("CompanyServiceImpl : Impossible to get companies count");
            throw new RuntimeException("CompanyServiceImpl : Impossible to get companies count");
        }
    }

    @Override
    public Company getCompany(int id) {
        if (id < 0) {
            return null;
        }

        try {
            return companyDao.get(id);
        } catch (DAOSelectException e) {
            LOGGER.info("CompanyServiceImpl : error while getting company with id " + id);
            throw new RuntimeException("CompanyServiceImpl : error while getting company with id " + id);
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
            LOGGER.info("CompanyServiceImpl : error while updating company (" + company + ")");
            throw new RuntimeException("CompanyServiceImpl : error while updating company (" + company + ")");
        }
    }

    @Override
    @Transactional(rollbackFor = {DAOException.class, SQLException.class})
    public void delete(int id) {
        try {
            computerDao.deleteComputerBelongingToCompany(id);
            companyDao.delete(id);
        } catch (DAODeleteException e) {
            LOGGER.info("CompanyServiceImpl : error while deleting company with id : " + id);
            throw new RuntimeException("CompanyServiceImpl : error while deleting company with id " + id + " / Exception detail : " + e.getMessage());
        }
    }

    @Override
    public Company getLastCompanyInserted() {
        try {
            return companyDao.getLastCompanyInserted();
        } catch (DAOSelectException e) {
            LOGGER.info("CompanyServiceImpl : Error while getting the last company");
            throw new RuntimeException("CompanyServiceImpl : Error while getting the last company");
        }
    }

}
