package cdb.services;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cdb.persistence.ComputerDAO;
import cdb.persistence.filter.FilterSelect;
import cdb.persistence.filter.FilterSelectComputer;
import cdb.validator.ComputerValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vkarassouloff on 19/04/17.
 */
public class ComputerServices implements IComputerServices {

    private final Logger LOGGER = LoggerFactory.getLogger(ComputerServices.class);

    private static ComputerServices service = new ComputerServices();

    private ComputerDAO computerDao;

    /**
     * Construct singleton.
     */
    private ComputerServices() {
        computerDao = ComputerDAO.getInstance();
    }

    public static ComputerServices getInstance() {
        return service;
    }

    @Override
    public List<Computer> getComputers() {
        try {
            return computerDao.getAll();
        } catch (DAOSelectException e) {
            LOGGER.info("ComputerServices : Error getting all computers");
            return new ArrayList<>();
        }
    }

    @Override
    public List<ComputerDTO> getPagedComputerDTO(FilterSelect filter) {
        try {
            return MapperComputer.toDTOs(computerDao.getFromFilter(filter));
        } catch (DAOSelectException e) {
            LOGGER.info("ComputerServices : Error getting paged computer");
            return new ArrayList<>();
        }
    }

    @Override
    public List<Computer> getPagedComputer(FilterSelect filter) {
        try {
            return computerDao.getFromFilter(filter);
        } catch (DAOSelectException e) {
            LOGGER.info("ComputerServices : Error get paged computer");
            return new ArrayList<>();
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
            LOGGER.info("ComputerServices : Error getting count computers");
            return 0;
        }
    }


    @Override
    public Computer getComputer(int id) {
        try {
            return computerDao.getById(id);
        } catch (DAOSelectException e) {
            LOGGER.info("ComputerServices : Error getting all computers");
            return null;
        }
    }

    @Override
    public ComputerDTO getComputerDTO(int id) {
        try {
            return MapperComputer.toDTO(computerDao.getById(id));
        } catch (DAOSelectException e) {
            LOGGER.info("ComputerServices : Error getting all computers");
            return null;
        }
    }

    @Override
    public int addComputer(Computer computer) throws FormException {
        try {
            ComputerValidator.checkValidity(computer);
        } catch (InvalidComputerException e) {
            LOGGER.info("Computer is not valid (" + computer + ")");
            throw new FormException(e.getMessage());
        }

        try {
            return computerDao.insert(computer);
        } catch (DAOInsertException e) {
            LOGGER.info("ComputerServices : Error while inserting computer : (" + computer + ")");
        }
        return -1;
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
            LOGGER.info("ComputerServices : Error while trying to update computer [" + computer + "]");
        }
        return false;
    }

    @Override
    public boolean deleteComputer(List<Integer> ids) {
        try {
            return computerDao.deleteById(ids);
        } catch (DAODeleteException e) {
            LOGGER.info("ComputerServices : Computer are not deleted (" + ids + ")");
        }
        return false;
    }


    @Override
    public void formUpdateComputer(ComputerDTO form) throws FormException {
        Computer computer = null;
        try {
            computer = MapperComputer.mapDTOToObject(form);
        } catch (MapperException e) {
            LOGGER.info("ComputerServices : Mapping error " + e.getMessage());
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
            LOGGER.info("ComputerServices : Cannot map computer to DTO " + e.getMessage());
            throw new RuntimeException("Cannot map computer to DTO");
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
            LOGGER.info("ComputerServices : Error while getting the last computer");
            return null;
        }
    }


    @Override
    public ComputerPage getPage(FilterSelectComputer filter) {
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
