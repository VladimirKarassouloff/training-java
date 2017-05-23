package cdb.service;

import cdb.dto.ComputerDTO;
import cdb.exception.DAOCountException;
import cdb.exception.DAODeleteException;
import cdb.exception.DAOInsertException;
import cdb.exception.DAOSelectException;
import cdb.exception.DAOUpdateException;
import cdb.exception.FormException;
import cdb.exception.InvalidComputerException;
import cdb.exception.MapperException;
import cdb.mapper.MapperComputer;
import cdb.model.Computer;
import cdb.model.ComputerPage;
import cdb.persistence.IComputerDAO;
import cdb.persistence.filter.FilterSelect;
import cdb.persistence.filter.FilterSelectComputer;
import cdb.validator.ComputerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by vkarassouloff on 19/04/17.
 */
@Service()
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
            return computerDao.getAll();
        } catch (DAOSelectException e) {
            LOGGER.info("ComputerServiceImpl : Error getting all computers");
            throw new RuntimeException("ComputerService : Impossible to get all computers");
        }
    }

    @Override
    public List<ComputerDTO> getPagedComputerDTO(FilterSelect filter) {
        try {
            return MapperComputer.toDTOs(computerDao.getFromFilter(filter));
        } catch (DAOSelectException e) {
            LOGGER.info("ComputerServiceImpl : Error getting paged computer");
            throw new RuntimeException("ComputerService : Impossible to get last computerDto");
        }
    }

    @Override
    public List<Computer> getPagedComputer(FilterSelect filter) {
        try {
            return computerDao.getFromFilter(filter);
        } catch (DAOSelectException e) {
            LOGGER.info("ComputerServiceImpl : Error get paged computer");
            throw new RuntimeException("ComputerService : Impossible to get page of computers");
        }
    }


    @Override
    public int getCountComputer() {
        return this.getCountComputer(new FilterSelectComputer());
    }

    @Override
    public int getCountComputer(FilterSelect filter) {
        try {
            return computerDao.getCount(filter);
        } catch (DAOCountException e) {
            LOGGER.info("ComputerServiceImpl : Error getting count computers");
            throw new RuntimeException("ComputerService : Impossible to get count of computer");
        }
    }


    @Override
    public Computer getComputer(int id) {
        try {
            return computerDao.get(id);
        } catch (DAOSelectException e) {
            LOGGER.info("ComputerServiceImpl : Error getting all computers");
            throw new RuntimeException("ComputerService : Impossible to get computer with id " + id);
        }
    }

    @Override
    public ComputerDTO getComputerDTO(int id) {
        try {
            return MapperComputer.toDTO(computerDao.get(id));
        } catch (DAOSelectException e) {
            LOGGER.info("ComputerServiceImpl : Error getting all computers");
            throw new RuntimeException("ComputerService : Impossible to get computerDto");
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
            computerDao.insert(computer);
        } catch (DAOInsertException e) {
            LOGGER.info("ComputerServiceImpl : Error while inserting computer : (" + computer + ")");
            throw new RuntimeException("ComputerService : Impossible to get add computer");
        }
    }

    @Override
    public boolean updateComputer(Computer computer) throws FormException {
        try {
            ComputerValidator.checkValidity(computer);
        } catch (InvalidComputerException e) {
            LOGGER.info("Computer is not valid (" + computer + ")");
            throw new FormException(e.getMessage());
        }

        try {
            return computerDao.update(computer);
        } catch (DAOUpdateException e) {
            LOGGER.info("ComputerServiceImpl : Error while trying to update computer [" + computer + "]");
            throw new RuntimeException("ComputerService : Impossible to update computer");
        }
    }

    @Override
    public void deleteComputer(int... ids) {
        try {
            computerDao.delete(ids);
        } catch (DAODeleteException e) {
            LOGGER.info("ComputerServiceImpl : Computer are not deleted (" + ids + ")" + " => " + e.getMessage());
            throw new RuntimeException("ComputerService : Impossible to delete  computer => " + ids);
        }
    }


    @Override
    public void formUpdateComputer(ComputerDTO form) throws FormException {
        Computer computer = null;
        try {
            computer = MapperComputer.mapDTOToObject(form);
        } catch (MapperException e) {
            LOGGER.info("ComputerServiceImpl : Mapping error " + e.getMessage());
            throw new RuntimeException("Impossible de cdb.mapper [" + form + "] en computer");
        }

        try {
            ComputerValidator.checkValidityForUpdate(computer);
        } catch (InvalidComputerException e) {
            throw new FormException("An error occured : " + e.getMessage());
        }

        updateComputer(computer);
    }

    @Override
    public void formAddComputer(ComputerDTO form) throws FormException {
        Computer computer = null;
        try {
            computer = MapperComputer.mapDTOToObject(form);
        } catch (MapperException e) {
            LOGGER.info("ComputerServiceImpl : Cannot map computer to DTO " + e.getMessage());
            throw new FormException("Cannot map computer to DTO");
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
            return computerDao.getLastComputerInserted();
        } catch (DAOSelectException e) {
            LOGGER.info("ComputerServiceImpl : Error while getting the last computer");
            throw new RuntimeException("ComputerService : Impossible to get last computer");
        }
    }


    @Override
    public ComputerPage getPage(FilterSelect filter) {
        ComputerPage cp = new ComputerPage.Builder()
                .withCount(getCountComputer(filter))
                .withLengthPage(filter.getNumberOfResult())
                .withDisplayedPage(filter.getPage())
                .build();


        // Check user is at a valid page
        double calc = ((double) cp.getTotalCount() / (double) cp.getLengthPage());
        if (filter.getPage() < 0) {
            cp.setDisplayedPage(0);
        } else if ((double) filter.getPage() >= calc) {
            cp.setDisplayedPage((calc % 1 == 0) ? (int) calc : (int) (calc - 1));
        }
        filter.setPage(cp.getDisplayedPage());

        // Get the page asked
        cp.setResults(getPagedComputerDTO(filter));
        return cp;

    }

}
