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
import model.FilterSelect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.ComputerDAO;
import validator.ComputerValidator;

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
            return computerDao.getAll();
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<ComputerDTO> getPagedComputerDTO(int page, int numberItem, String filterName) {
        try {
            return MapperComputer.toDTOs(computerDao.getPagination(page, numberItem, ("".equals(filterName) ? null : filterName)));
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<ComputerDTO> getPagedComputerDTO(FilterSelect filter) {
        try {
            return MapperComputer.toDTOs(computerDao.getFromFilter(filter));
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Computer> getPagedComputer(int page, int numberItem, String filterName) {
        try {
            return computerDao.getPagination(page, numberItem, filterName);
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Computer> getPagedComputer(int page, int numberItem) {
        try {
            return computerDao.getPagination(page, numberItem, null);
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<ComputerDTO> getPagedComputerDTO(int page, int numberItem) {
        return null;
    }

    @Override
    public int getCountComputer(FilterSelect filter) {
        try {
            return computerDao.getCount(filter);
        } catch (DAOCountException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getCountComputer() {
        try {
            return computerDao.getCount(new FilterSelect());
        } catch (DAOCountException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Computer getComputer(int id) {
        try {
            return computerDao.getById(id);
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ComputerDTO getComputerDTO(int id) {
        try {
            return MapperComputer.toDTO(computerDao.getById(id));
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addComputer(Computer computer) {
        try {
            ComputerValidator.checkValidity(computer);
            return computerDao.insert(computer);
        } catch (DAOInsertException e) {
            e.printStackTrace();
        } catch (InvalidComputerException e) {
            LOGGER.info("Computer is not valid (" + computer + ")");
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean updateComputer(Computer computer) {
        try {
            ComputerValidator.checkValidity(computer);
            return computerDao.update(computer);
        } catch (DAOUpdateException e) {
            e.printStackTrace();
        } catch (InvalidComputerException e) {
            e.printStackTrace();
            LOGGER.info("Computer is not valid (" + computer + ")");
        }
        return false;
    }

    @Override
    public boolean deleteComputer(Computer comp) {
        try {
            return computerDao.deleteById(comp.getId());
        } catch (DAODeleteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void formUpdateComputer(ComputerDTO form) throws Exception {
        try {
            Computer computer = MapperComputer.mapDTOToObject(form);
            ComputerValidator.checkValidityForUpdate(computer);
            if (!updateComputer(computer)) {
                throw new FormException("An error occured");
            }
        } catch (MapperException e) {
            throw new FormException(e.getMessage());
        }
    }

    @Override
    public void formAddComputer(ComputerDTO form) throws Exception {
        try {
            Computer computer = MapperComputer.mapDTOToObject(form);
            ComputerValidator.checkValidity(computer);
            if (addComputer(computer) == -1) {
                throw new FormException("An error occured");
            }
        } catch (InvalidComputerException e) {
            throw new FormException(e.getMessage());
        } catch (Exception e) {
            LOGGER.info("Generic exception " + e.getMessage());
            throw new FormException();
        }
    }

    @Override
    public Computer getLastComputerInserted() {
        try {
            return computerDao.getLastComputerInserted();
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return null;
        }
    }


}
