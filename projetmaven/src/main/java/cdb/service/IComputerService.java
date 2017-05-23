package cdb.service;

import cdb.dto.ComputerDTO;
import cdb.exception.FormException;
import cdb.model.Computer;
import cdb.model.ComputerPage;
import cdb.persistence.filter.FilterSelect;
import cdb.persistence.filter.FilterSelectComputer;

import java.util.List;

/**
 * Created by vkarassouloff on 26/04/17.
 */
public interface IComputerService {

    /**
     * Get all the computer from DB.
     *
     * @return computers
     */
    List<Computer> getComputers();


    /**
     * Get Page of computers.
     *
     * @param filter constraints
     * @return computers
     */
    List<Computer> getPagedComputer(FilterSelect filter);

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
     */
    void addComputer(Computer computer) throws FormException;

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
     * @param ids deleted
     */
    void deleteComputer(int... ids);


    /**
     * Try updating a computer.
     *
     * @param form from user sent back from jsp
     * @throws FormException error during validation
     */
    void formUpdateComputer(ComputerDTO form) throws FormException;

    /**
     * Try inserting new computer.
     *
     * @param form from user sent back from jsp
     * @throws FormException error during validation
     */
    void formAddComputer(ComputerDTO form) throws FormException;


    /**
     * Return the last computer inserted in DB.
     * @return last computer inserted
     */
    Computer getLastComputerInserted();

    /**
     * Get set of result, with total count, and the 'real' page displayed in case of wrong requirement.
     * @param filter constraints
     * @return db-results
     */
    ComputerPage getPage(FilterSelect filter);
}
