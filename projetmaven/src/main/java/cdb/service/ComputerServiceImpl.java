package cdb.service;

import cdb.dto.ComputerDTO;
import cdb.exception.FormException;
import cdb.exception.InvalidComputerException;
import cdb.exception.MapperException;
import cdb.mapper.MapperComputer;
import cdb.model.Computer;
import cdb.persistence.IComputerDAO;
import cdb.validator.ComputerValidator;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vkarassouloff on 19/04/17.
 */
@Service
@Transactional
public class ComputerServiceImpl implements IComputerService {

    private final Logger LOGGER = LoggerFactory.getLogger(ComputerServiceImpl.class);

    private IComputerDAO computerDao;

    /**
     * Default constructor.
     *
     * @param computerDao data access object for computers
     */
    @Autowired
    public ComputerServiceImpl(IComputerDAO computerDao) {
        this.computerDao = computerDao;
    }


    public IComputerDAO getIComputerDAO() {
        return computerDao;
    }

    public void setIComputerDAO(IComputerDAO computerDao) {
        this.computerDao = computerDao;
    }


    @Override
    public List<Computer> getComputers() {
        try {
            return computerDao.findAll();
        } catch (IllegalArgumentException e) {
            LOGGER.info("ComputerServiceImpl : Error getting all computers" + e.getMessage());
            throw new RuntimeException("ComputerService : Impossible to get all computers" + e.getMessage());
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Computer getComputer(long id) {
        try {
            Computer obj = computerDao.getOne(id);
            Hibernate.initialize(obj);
            return obj;
        } catch (IllegalArgumentException e) {
            LOGGER.info("ComputerService : Impossible to get computer with id " + id + " => " + e.getMessage());
            throw new RuntimeException("ComputerService : Impossible to get computer with id " + id + " => " + e.getMessage());
        }
    }


    @Override
    public void addComputer(Computer computer) throws FormException {
        try {
            ComputerValidator.checkValidity(computer);
        } catch (InvalidComputerException e) {
            LOGGER.info("Computer is not valid (" + computer + ")");
            throw new FormException(e.getMessage());
        }

        try {
            computerDao.saveAndFlush(computer);
        } catch (IllegalArgumentException e) {
            LOGGER.info("ComputerService : Error while inserting computer : (" + computer + ") => " + e.getMessage());
            throw new RuntimeException("ComputerService : Error while inserting computer : (" + computer + ") => " + e.getMessage());
        }
    }

    @Override
    public void updateComputer(Computer computer) throws FormException {
        try {
            ComputerValidator.checkValidity(computer);
        } catch (InvalidComputerException e) {
            LOGGER.info("Computer is not valid (" + computer + ")");
            throw new FormException(e.getMessage());
        }

        try {
            computerDao.saveAndFlush(computer);
        } catch (IllegalArgumentException e) {
            LOGGER.info("ComputerService : Impossible to update computer [" + computer + "] => " + e.getMessage());
            throw new RuntimeException("ComputerService : Impossible to update computer [" + computer + "] => " + e.getMessage());
        }
    }

    @Override
    public void deleteComputer(long... ids) {
        try {
            for (long id : ids) {
                computerDao.delete(id);
            }
            computerDao.flush();
        } catch (RuntimeException e) {
            LOGGER.info("ComputerService : Computer are not deleted (" + ids + ")" + " => " + e.getMessage());
            throw new RuntimeException("ComputerService : Computer are not deleted (" + ids + ")" + " => " + e.getMessage());
        }
    }


    @Override
    public void formUpdateComputer(ComputerDTO form) throws FormException {
        Computer computer = null;
        try {
            computer = MapperComputer.mapDTOToObject(form);
        } catch (MapperException e) {
            LOGGER.info("ComputerService : Mapping error  [" + form + "] " + e.getMessage());
            throw new RuntimeException("ComputerService : Mapping error  [" + form + "] " + e.getMessage());
        }

        try {
            ComputerValidator.checkValidityForUpdate(computer);
        } catch (InvalidComputerException e) {
            throw new FormException("ComputerService : An error occured : " + e.getMessage());
        }

        updateComputer(computer);
    }

    @Override
    public void formAddComputer(ComputerDTO form) throws FormException {
        Computer computer = null;
        try {
            computer = MapperComputer.mapDTOToObject(form);
        } catch (MapperException e) {
            LOGGER.info("ComputerService : Cannot map computer to DTO " + e.getMessage());
            throw new FormException("ComputerService : Cannot map computer to DTO " + e.getMessage());
        }

        try {
            ComputerValidator.checkValidity(computer);
        } catch (InvalidComputerException e) {
            throw new FormException(e.getMessage());
        }
        addComputer(computer);
    }

    @Override
    public Computer getLastComputerInserted() {
        try {
            return computerDao.findFirstByOrderByIdDesc();
        } catch (RuntimeException e) {
            LOGGER.info("ComputerService : Error while getting the last computer");
            throw new RuntimeException("ComputerService : Impossible to get last computer");
        }
    }


    @Override
    public long getCountComputer() {
        try {
            return computerDao.count();
        } catch (RuntimeException e) {
            LOGGER.info("ComputerService : Impossible to computer count => " + e.getMessage());
            throw new RuntimeException("ComputerService : Impossible to computer count => " + e.getMessage());
        }
    }

    @Override
    public long getCountComputer(String nameOrCompanyName) {
        try {
            if (nameOrCompanyName == null) {
                return getCountComputer();
            } else {
                return computerDao.countByNameStartingWithOrCompanyNameStartingWith(nameOrCompanyName, nameOrCompanyName);
            }
        } catch (RuntimeException e) {
            LOGGER.info("ComputerService : Impossible to computer count => " + e.getMessage());
            throw new RuntimeException("ComputerService : Impossible to computer count => " + e.getMessage());
        }
    }


    @Override
    public Page<Computer> getComputers(PageRequest pr) {
        return getPage(pr, null);
    }

    @Override
    public Page<Computer> getPage(Pageable pr, String nameOrCompanyName) {
        try {
            Page<Computer> page = null;

            // Check that we are not asking for a negative page
            if (pr.getPageNumber() < 0) {
                pr = pr.first();
            }

            if (nameOrCompanyName == null) {
                page = computerDao.getComputersBy(pr);
            } else {
                page = computerDao.getComputersByNameStartingWithOrCompanyNameStartingWith(pr, nameOrCompanyName, nameOrCompanyName);
            }

            // Check that user asked for a valid page
            if (page.getContent().isEmpty() && page.getTotalPages() != 0 && page.getTotalElements() != 0) {
                return getPage(new PageRequest(page.getTotalPages() - 1, page.getSize()), nameOrCompanyName);
            }

            return page;
        } catch (RuntimeException e) {
            LOGGER.info("ComputerService : Impossible to computer page => " + e.getMessage());
            throw new RuntimeException("ComputerService : Impossible to computer page => " + e.getMessage());
        }
    }
}
