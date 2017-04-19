package services;

import dto.ComputerDTO;
import exception.DAOCountException;
import exception.DAOSelectException;
import exception.DAOUpdateException;
import exception.DAODeleteException;
import exception.DAOInsertException;
import mapper.MapperCompany;
import mapper.MapperComputer;
import mapper.MapperComputerDTO;
import model.Company;
import model.Computer;
import persistence.CompanyDAO;
import persistence.ComputerDAO;
import validator.CompanyValidator;
import validator.ComputerValidator;

import java.util.ArrayList;
import java.util.List;

public class CommonServices {


    ///// COMPANY

    /**
     * Get all companies in DB.
     *
     * @return companies
     */
    public static List<Company> getCompanies() {
        try {
            return MapperCompany.mapResultSetToObjects(CompanyDAO.getAll());
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return new ArrayList<Company>();
        }
    }

    /**
     * Get Page of campanies.
     *
     * @param page       number of page
     * @param numberItem number of result per page
     * @return companies
     */
    public static List<Company> getPagedCompany(int page, int numberItem) {
        try {
            return MapperCompany.mapResultSetToObjects(CompanyDAO.getPagination(page, numberItem));
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return new ArrayList<Company>();
        }
    }

    /**
     * Get the total count of companies in DB.
     *
     * @return number of companoes
     */
    public static int getCountCompany() {
        try {
            return CompanyDAO.getCount();
        } catch (DAOCountException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Get a specific company.
     *
     * @param id of the company returned
     * @return company having the id or null
     */
    public static Company getCompany(int id) {
        try {
            return MapperCompany.mapResultSetToObject(CompanyDAO.getById(id));
        } catch (DAOSelectException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Update the company in DB with the value of the company parameter.
     *
     * @param company updated
     * @return boolean for success
     */
    public static boolean updateCompany(Company company) {
        if (!CompanyValidator.isValid(company)) {
            return false;
        }
        try {
            return CompanyDAO.update(company);
        } catch (DAOUpdateException e) {
            e.printStackTrace();
            return false;
        }
    }


    ////COMPUTER

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
            return MapperComputerDTO.mapResultSetToObjects(ComputerDAO.getPagination(page, numberItem, filterName));
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
