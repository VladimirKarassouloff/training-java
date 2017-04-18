package services;

import dto.ComputerDTO;
import mapper.MapperCompany;
import mapper.MapperComputer;
import mapper.MapperComputerDTO;
import model.Company;
import model.Computer;
import persistence.CompanyDAO;
import persistence.ComputerDAO;

import java.util.List;

public class CommonServices {


    ///// COMPANY

    /**
     * Get all companies in DB.
     * @return companies
     */
    public static List<Company> getCompanies() {
        return MapperCompany.mapResultSetToObjects(CompanyDAO.getAll());
    }

    /**
     * Get Page of campanies.
     * @param page number of page
     * @param numberItem number of result per page
     * @return companies
     */
    public static List<Company> getPagedCompany(int page, int numberItem) {
        return MapperCompany.mapResultSetToObjects(CompanyDAO.getPagination(page, numberItem));
    }

    /**
     * Get the total count of companies in DB.
     * @return number of companoes
     */
    public static int getCountCompany() {
        return CompanyDAO.getCount();
    }

    /**
     * Get a specific company.
     * @param id of the company returned
     * @return company having the id or null
     */
    public static Company getCompany(int id) {
        return MapperCompany.mapResultSetToObject(CompanyDAO.getById(id));
    }

    /**
     * Update the company in DB with the value of the company parameter.
     * @param company updated
     * @return boolean for success
     */
    public static boolean updateCompany(Company company) {
        return CompanyDAO.update(company);
    }


    ////COMPUTER

    /**
     * Get all the computer from DB.
     * @return computers
     */
    public static List<Computer> getComputers() {
        return MapperComputer.mapResultSetToObjects(ComputerDAO.getAll());
    }

    /**
     * Get Page of computers.
     * @param page returned
     * @param numberItem per page
     * @param filterName filter results per computer name
     * @return computers
     */
    public static List<ComputerDTO> getPagedComputerDTO(int page, int numberItem, String filterName) {
        return MapperComputerDTO.mapResultSetToObjects(ComputerDAO.getPagination(page, numberItem, filterName));
    }

    /**
     * Get Page of computers.
     * @param page returned
     * @param numberItem per page
     * @param filterName filter results per computer name
     * @return computers
     */
    public static List<Computer> getPagedComputer(int page, int numberItem, String filterName) {
        return MapperComputer.mapResultSetToObjects(ComputerDAO.getPagination(page, numberItem, filterName));
    }

    /**
     * Get Page of computers.
     * @param page returned
     * @param numberItem per page
     * @return computers
     */
    public static List<Computer> getPagedComputer(int page, int numberItem) {
        return MapperComputer.mapResultSetToObjects(ComputerDAO.getPagination(page, numberItem, null));
    }

    /**
     * Get Page of computersDTO.
     * @param page returned
     * @param numberItem item per pages
     * @return computersdto
     */
    public static List<ComputerDTO> getPagedComputerDTO(int page, int numberItem) {
        //return MapperComputer.mapResultSetToObjectsDTO(ComputerDAO.getPagination(page, numberItem));
        return null;
    }

    /**
     * Get Total count of the computers in DB having the name like searchByName.
     * @param searchByName get computers having name like %searchByName%
     * @return number of computers matching
     */
    public static int getCountComputer(String searchByName) {
        return ComputerDAO.getCount(searchByName);
    }

    /**
     * Get Total count of the computers in DB.
     * @return number of computers
     */
    public static int getCountComputer() {
        return ComputerDAO.getCount(null);
    }

    /**
     * Get specific Computer having this id.
     * @param id of the computer returned
     * @return computer or null
     */
    public static Computer getComputer(int id) {
        return MapperComputer.mapResultSetToObject(ComputerDAO.getById(id));
    }

    /**
     * Add Computer to the DB.
     * @param computer added
     * @return int for the id generated of the new computer, or -1 if insert failed
     */
    public static int addComputer(Computer computer) {
        return ComputerDAO.insert(computer);
    }

    /**
     * Update computer from DB.
     * @param computer updated
     * @return success query
     */
    public static boolean updateComputer(Computer computer) {
        return ComputerDAO.update(computer);
    }

    /**
     * Delete computer of the DB having the id equals of the computer id param.
     * @param comp deleted
     * @return success query
     */
    public static boolean deleteComputer(Computer comp) {
        return ComputerDAO.deleteById(comp.getId());
    }

}
