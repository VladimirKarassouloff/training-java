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
     * Get singleton.
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
    public List<ComputerDTO> getPagedComputerDTO(int page, int numberItem, String filterName) {
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            List<ComputerDTO> result = MapperComputer.toDTOs(computerDao.getPagination(page, numberItem, ("".equals(filterName) ? null : filterName)));
            TransactionHolder.get().commit();
            return result;
        } catch (SQLException | DAOSelectException e) {
            e.printStackTrace();
            Connector.getInstance().rollback(TransactionHolder.get());
            LOGGER.info("Error getting paged computers dto");
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
    public List<Computer> getPagedComputer(int page, int numberItem, String filterName) {
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            List<Computer> result = computerDao.getPagination(page, numberItem, filterName);
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
    public List<Computer> getPagedComputer(int page, int numberItem) {
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            List<Computer> result = computerDao.getPagination(page, numberItem, null);
            TransactionHolder.get().commit();
            return result;
        } catch (SQLException | DAOSelectException e) {
            e.printStackTrace();
            Connector.getInstance().rollback(TransactionHolder.get());
            LOGGER.info("Error getPagedComputer");
            return new ArrayList<>();
        } finally {
            Connector.getInstance().close(TransactionHolder.get());
            TransactionHolder.set(null);
        }
    }

    @Override
    public List<ComputerDTO> getPagedComputerDTO(int page, int numberItem) {
        return null;
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
    public int getCountComputer() {
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            int result = computerDao.getCount(new FilterSelectComputer());
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
            Connector.getInstance().rollback(TransactionHolder.get());
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
    public boolean deleteComputer(Computer comp) {
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            boolean result = computerDao.deleteById(comp.getId());
            TransactionHolder.get().commit();
            return result;
        } catch (SQLException | DAODeleteException e) {
            e.printStackTrace();
            LOGGER.info("Computer is not delete computer");
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

}
