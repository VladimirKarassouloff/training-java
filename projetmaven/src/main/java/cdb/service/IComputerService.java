package cdb.service;

import cdb.dto.ComputerDTO;
import cdb.exception.FormException;
import cdb.model.Computer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
     * Get page of computer.
     *
     * @param pr request
     * @return result set
     */
    Page<Computer> getComputers(PageRequest pr);

    /**
     * Get Total count of the computers in DB.
     *
     * @return number of computers
     */
    long getCountComputer();

    /**
     * Get Total count of the computers in DB.
     *
     * @param nameOrCompanyName filter
     * @return number of computers
     */
    long getCountComputer(String nameOrCompanyName);

    /**
     * Get specific Computer having this id.
     *
     * @param id of the computer returned
     * @return computer or null
     */
    Computer getComputer(long id);

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
     */
    void updateComputer(Computer computer) throws FormException;

    /**
     * Delete computer of the DB having the id equals of the computer id param.
     *
     * @param ids deleted
     */
    void deleteComputer(long... ids);


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
     *
     * @return last computer inserted
     */
    Computer getLastComputerInserted();

    /**
     * Get a result set.
     *
     * @param pr                page request
     * @param nameOrCompanyName filters
     * @return result
     */
    Page<Computer> getPage(Pageable pr, String nameOrCompanyName);

}
