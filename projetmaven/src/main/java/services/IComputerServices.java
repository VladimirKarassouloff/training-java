package services;

import dto.ComputerDTO;
import exception.FormException;
import model.Computer;
import model.FilterSelect;

import java.util.List;

/**
 * Created by vkarassouloff on 26/04/17.
 */
interface IComputerServices {

    /**
     * Get all the computer from DB.
     *
     * @return computers
     */
    List<Computer> getComputers();

    /**
     * Get Page of computers.
     *
     * @param page       returned
     * @param numberItem per page
     * @param filterName filter results per computer name
     * @return computers
     */
    List<ComputerDTO> getPagedComputerDTO(int page, int numberItem, String filterName);

    /**
     * Get Page of computers.
     *
     * @param page       returned
     * @param numberItem per page
     * @param filterName filter results per computer name
     * @return computers
     */
    List<Computer> getPagedComputer(int page, int numberItem, String filterName);

    /**
     * Get Page of computers.
     *
     * @param page       returned
     * @param numberItem per page
     * @return computers
     */
    List<Computer> getPagedComputer(int page, int numberItem);

    /**
     * Get Page of computersDTO.
     *
     * @param page       returned
     * @param numberItem item per pages
     * @return computersdto
     */
    List<ComputerDTO> getPagedComputerDTO(int page, int numberItem);

    /**
     * Get computerdto matching filter.
     * @param filter for your select
     * @return computer matching filter
     */
    List<ComputerDTO> getPagedComputerDTO(FilterSelect filter);

    /**
     * Get Total count of the computers in DB having the name like searchByName.
     *
     * @param fs filters
     * @return number of computers matching
     */
    int getCountComputer(FilterSelect fs);

    /**
     * Get Total count of the computers in DB.
     *
     * @return number of computers
     */
    int getCountComputer();

    /**
     * Get specific Computer having this id.
     *
     * @param id of the computer returned
     * @return computer or null
     */
    Computer getComputer(int id);

    /**
     * Get specific ComputerDTO having this id.
     *
     * @param id of the computer returned
     * @return computer or null
     */
    ComputerDTO getComputerDTO(int id);

    /**
     * Add Computer to the DB.
     *
     * @param computer added
     * @throws FormException if computer is not valid
     * @return int for the id generated of the new computer, or -1 if insert failed
     */
    int addComputer(Computer computer) throws FormException;

    /**
     * Update computer from DB.
     *
     * @param computer updated
     * @throws FormException if computer is not valid
     * @return success query
     */
    boolean updateComputer(Computer computer) throws FormException;

    /**
     * Delete computer of the DB having the id equals of the computer id param.
     *
     * @param comp deleted
     * @return success query
     */
    boolean deleteComputer(Computer comp);

    /**
     * Try updating a computer.
     *
     * @param form from user sent back from jsp
     * @throws Exception error during validation
     */
    void formUpdateComputer(ComputerDTO form) throws Exception;

    /**
     * Try inserting new computer.
     *
     * @param form from user sent back from jsp
     * @throws Exception error during validation
     */
    void formAddComputer(ComputerDTO form) throws Exception;


    /**
     * Return the last computer inserted in DB.
     * @return last computer inserted
     */
    Computer getLastComputerInserted();


}
