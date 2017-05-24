package cdb.service;

import cdb.model.Company;
import cdb.persistence.ICompanyDAO;
import cdb.persistence.IComputerDAO;
import cdb.validator.CompanyValidator;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.RuntimeException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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
            return companyDao.findAll();
        } catch (RuntimeException e) {
            LOGGER.info("CompanyServiceImpl : error while getting companies" + e.getMessage());
            throw new RuntimeException("CompanyServiceImpl : Impossible to get companies" + e.getMessage());
        }
    }

    @Override
    public Page<Company> getPagedCompany(PageRequest page) {
        try {
            return companyDao.findAll(page);
        } catch (RuntimeException e) {
            LOGGER.info("CompanyServiceImpl : error while getting paged companies" + e.getMessage());
            throw new RuntimeException("CompanyServiceImpl : Impossible to get page of companies" + e.getMessage());
        }
    }

    @Override
    public long getCountCompany() {
        try {
            return companyDao.count();
        } catch (RuntimeException e) {
            LOGGER.info("CompanyServiceImpl : Impossible to get companies count" + e.getMessage());
            throw new RuntimeException("CompanyServiceImpl : Impossible to get companies count" + e.getMessage());
        }
    }

    @Override
    public Company getCompany(long id) {
        if (id < 0) {
            return null;
        }

        try {
            Company obj = companyDao.getOne(id);
            Hibernate.initialize(obj);
            return obj;
        } catch (RuntimeException e) {
            LOGGER.info("CompanyServiceImpl : error while getting company with id " + id);
            throw new RuntimeException("CompanyServiceImpl : error while getting company with id " + id);
        }
    }

    @Override
    public Company updateCompany(Company company) {
        if (!CompanyValidator.isValid(company)) {
            LOGGER.info("CompanyServiceImpl : error company is not valid (" + company + ")");
            throw new RuntimeException("CompanyServiceImpl : error company is not valid");
        }

        try {
            return companyDao.save(company);
        } catch (RuntimeException e) {
            LOGGER.info("CompanyServiceImpl : error while updating company (" + company + ")");
            throw new RuntimeException("CompanyServiceImpl : error while updating company (" + company + ")");
        }
    }

    @Override
    public void delete(long id) {
        try {
            companyDao.delete(id);
            companyDao.flush();
        } catch (RuntimeException e) {
            LOGGER.info("CompanyServiceImpl : error while deleting company with id : " + id);
            throw new RuntimeException("CompanyServiceImpl : error while deleting company with id " + id + " / Exception detail : " + e.getMessage());
        }
    }

    @Override
    public Company getLastCompanyInserted() {
        try {
            return companyDao.findFirstByOrderByIdDesc();
        } catch (RuntimeException e) {
            LOGGER.info("CompanyServiceImpl : Error while getting the last company");
            throw new RuntimeException("CompanyServiceImpl : Error while getting the last company");
        }
    }

}
