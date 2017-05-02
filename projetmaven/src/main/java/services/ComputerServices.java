package services;

import dto.ComputerDTO;
import exception.DAOCountException;
import exception.DAODeleteException;
import exception.DAOInsertException;
import exception.DAOSelectException;
import exception.DAOUpdateException;
import exception.FormException;
import exception.InvalidComputerException;
import exception.MapperException;
import mapper.MapperComputer;
import model.Computer;
import model.ComputerPage;
import persistence.filter.FilterSelect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.ComputerDAO;
import persistence.Connector;
import persistence.filter.FilterSelectComputer;
import validator.ComputerValidator;

import java.sql.SQLException;
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
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            List<Computer> result = computerDao.getAll();
            TransactionHolder.get().commit();
            return result;
        } catch (SQLException | DAOSelectException e) {
            e.printStackTrace();
            Connector.getInstance().rollback(TransactionHolder.get());
            LOGGER.info("Error getting all computers");
            return new ArrayList<>();
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
        }
    }

    @Override
    public List<ComputerDTO> getPagedComputerDTO(FilterSelect filter) {
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            List<ComputerDTO> result = MapperComputer.toDTOs(computerDao.getFromFilter(filter));
            TransactionHolder.get().commit();
            return result;
        } catch (SQLException | DAOSelectException e) {
            e.printStackTrace();
            Connector.getInstance().rollback(TransactionHolder.get());
            LOGGER.info("Error getting paged computer");
            return new ArrayList<>();
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
        }
    }

    @Override
    public List<Computer> getPagedComputer(FilterSelect filter) {
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            List<Computer> result = computerDao.getFromFilter(filter);
            TransactionHolder.get().commit();
            return result;
        } catch (SQLException | DAOSelectException e) {
            e.printStackTrace();
            Connector.getInstance().rollback(TransactionHolder.get());
            LOGGER.info("Error get paged computer");
            return new ArrayList<>();
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
        }
    }


    @Override
    public int getCountComputer() {
        return this.getCountComputer(new FilterSelectComputer());
    }

    @Override
    public int getCountComputer(FilterSelect filter) {
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            int result = computerDao.getCount(filter);
            TransactionHolder.get().commit();
            return result;
        } catch (SQLException | DAOCountException e) {
            e.printStackTrace();
            Connector.getInstance().rollback(TransactionHolder.get());
            LOGGER.info("Error getting count computers");
            return 0;
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
        }
    }


    @Override
    public Computer getComputer(int id) {
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            Computer result = computerDao.getById(id);
            TransactionHolder.get().commit();
            return result;
        } catch (SQLException | DAOSelectException e) {
            e.printStackTrace();
            Connector.getInstance().rollback(TransactionHolder.get());
            LOGGER.info("Error getting all computers");
            return null;
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
        }
    }

    @Override
    public ComputerDTO getComputerDTO(int id) {
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            ComputerDTO result = MapperComputer.toDTO(computerDao.getById(id));
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
    public int addComputer(Computer computer) throws FormException {
        try {
            ComputerValidator.checkValidity(computer);
        } catch (InvalidComputerException e) {
            LOGGER.info("Computer is not valid (" + computer + ")");
            e.printStackTrace();
            throw new FormException(e.getMessage());
        }

        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            int result = computerDao.insert(computer);
            TransactionHolder.get().commit();
            return result;
        } catch (SQLException | DAOInsertException e) {
            Connector.getInstance().rollback(TransactionHolder.get());
            e.printStackTrace();
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
        }
        return -1;
    }

    @Override
    public boolean updateComputer(Computer computer) throws FormException {
        try {
            ComputerValidator.checkValidity(computer);
        } catch (InvalidComputerException e) {
            e.printStackTrace();
            LOGGER.info("Computer is not valid (" + computer + ")");
            throw new FormException(e.getMessage());
        }

        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            boolean result = computerDao.update(computer);
            TransactionHolder.get().commit();
            return result;
        } catch (SQLException | DAOUpdateException e) {
            e.printStackTrace();
            LOGGER.info("Error while trying to update computer [" + computer + "]");
            Connector.getInstance().rollback(TransactionHolder.get());
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
        }
        return false;
    }

    @Override
    public boolean deleteComputer(List<Integer> ids) {
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            boolean result = computerDao.deleteById(ids);
            TransactionHolder.get().commit();
            return result;
        } catch (SQLException | DAODeleteException e) {
            e.printStackTrace();
            LOGGER.info("Computer are not deleted (" + ids + ")");
            Connector.getInstance().rollback(TransactionHolder.get());
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
        }
        return false;
    }


    @Override
    public void formUpdateComputer(ComputerDTO form) throws FormException {
        Computer computer = null;
        try {
            computer = MapperComputer.mapDTOToObject(form);
        } catch (MapperException e) {
            e.printStackTrace();
            LOGGER.info("Mapping error " + e.getMessage());
            throw new RuntimeException("Impossible de mapper [" + form + "] en computer");
        }

        try {
            ComputerValidator.checkValidityForUpdate(computer);
        } catch (InvalidComputerException e) {
            e.printStackTrace();
            throw new FormException("An error occured : " + e.getMessage());
        }

        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            updateComputer(computer);
            TransactionHolder.get().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info("Error while trying to update computer");
            Connector.getInstance().rollback(TransactionHolder.get());
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
        }
    }

    @Override
    public void formAddComputer(ComputerDTO form) throws FormException {
        Computer computer = null;
        try {
            computer = MapperComputer.mapDTOToObject(form);
        } catch (MapperException e) {
            e.printStackTrace();
            LOGGER.info("Cannot map computer to DTO " + e.getMessage());
            throw new RuntimeException("Cannot map computer to DTO");
        }

        try {
            ComputerValidator.checkValidity(computer);
        } catch (InvalidComputerException e) {
            throw new FormException(e.getMessage());
        }

        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            addComputer(computer);
            TransactionHolder.get().commit();
        } catch (SQLException e) {
            LOGGER.info("Exception while trying to add computer " + e.getMessage());
            Connector.getInstance().rollback(TransactionHolder.get());
        } catch (FormException e) {
            LOGGER.info("Form exception while trying to add computer " + e.getMessage());
            throw new FormException(e.getMessage());
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
        }
    }

    @Override
    public Computer getLastComputerInserted() {
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            Computer result = computerDao.getLastComputerInserted();
            TransactionHolder.get().commit();
            return result;
        } catch (SQLException | DAOSelectException e) {
            e.printStackTrace();
            LOGGER.info("Error while getting the last computer");
            return null;
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
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
            cp.setDisplayedPage((calc % 1 == 0) ? (int) calc - 1 : (int) calc);
        }
        filter.setPage(cp.getDisplayedPage());

        // Get the page asked
        cp.setResults(getPagedComputerDTO(filter));
        return cp;
    }
}
