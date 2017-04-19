package services;

import dto.ComputerDTO;
import exception.*;
import mapper.MapperComputer;
import mapper.MapperComputerDTO;
import model.Computer;
import persistence.ComputerDAO;
import validator.ComputerValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vkarassouloff on 19/04/17.
 */
public class ComputerServices {

    /**
     * Get all the computer from DB.
     *
     * @return computers
     */
    public static List<Computer> getComputers() {
        try {
            return MapperComputer.mapResultSetToObjects(ComputerDAO.getAll());
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return new ArrayList<Computer>();
        }
    }

    /**
     * Get Page of computers.
     *
     * @param page       returned
     * @param numberItem per page
     * @param filterName filter results per computer name
     * @return computers
     */
    public static List<ComputerDTO> getPagedComputerDTO(int page, int numberItem, String filterName) {
        try {
            return MapperComputerDTO.mapResultSetToObjects(ComputerDAO.getPagination(page, numberItem, ("".equals(filterName) ? null : filterName)));
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return new ArrayList<ComputerDTO>();
        }
    }

    /**
     * Get Page of computers.
     *
     * @param page       returned
     * @param numberItem per page
     * @param filterName filter results per computer name
     * @return computers
     */
    public static List<Computer> getPagedComputer(int page, int numberItem, String filterName) {
        try {
            return MapperComputer.mapResultSetToObjects(ComputerDAO.getPagination(page, numberItem, filterName));
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return new ArrayList<Computer>();
        }
    }

    /**
     * Get Page of computers.
     *
     * @param page       returned
     * @param numberItem per page
     * @return computers
     */
    public static List<Computer> getPagedComputer(int page, int numberItem) {
        try {
            return MapperComputer.mapResultSetToObjects(ComputerDAO.getPagination(page, numberItem, null));
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return new ArrayList<Computer>();
        }
    }

    /**
     * Get Page of computersDTO.
     *
     * @param page       returned
     * @param numberItem item per pages
     * @return computersdto
     */
    public static List<ComputerDTO> getPagedComputerDTO(int page, int numberItem) {
        //return MapperComputer.mapResultSetToObjectsDTO(ComputerDAO.getPagination(page, numberItem));
        return null;
    }

    /**
     * Get Total count of the computers in DB having the name like searchByName.
     *
     * @param searchByName get computers having name like %searchByName%
     * @return number of computers matching
     */
    public static int getCountComputer(String searchByName) {
        try {
            return ComputerDAO.getCount(searchByName);
        } catch (DAOCountException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Get Total count of the computers in DB.
     *
     * @return number of computers
     */
    public static int getCountComputer() {
        try {
            return ComputerDAO.getCount(null);
        } catch (DAOCountException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Get specific Computer having this id.
     *
     * @param id of the computer returned
     * @return computer or null
     */
    public static Computer getComputer(int id) {
        try {
            return MapperComputer.mapResultSetToObject(ComputerDAO.getById(id));
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Add Computer to the DB.
     *
     * @param computer added
     * @return int for the id generated of the new computer, or -1 if insert failed
     */
    public static int addComputer(Computer computer) {
        if (!ComputerValidator.isValid(computer)) {
            return -1;
        }
        try {
            return ComputerDAO.insert(computer);
        } catch (DAOInsertException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Update computer from DB.
     *
     * @param computer updated
     * @return success query
     */
    public static boolean updateComputer(Computer computer) {
        if (!ComputerValidator.isValid(computer)) {
            return false;
        }
        try {
            return ComputerDAO.update(computer);
        } catch (DAOUpdateException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete computer of the DB having the id equals of the computer id param.
     *
     * @param comp deleted
     * @return success query
     */
    public static boolean deleteComputer(Computer comp) {
        try {
            return ComputerDAO.deleteById(comp.getId());
        } catch (DAODeleteException e) {
            e.printStackTrace();
            return false;
        }
    }

}