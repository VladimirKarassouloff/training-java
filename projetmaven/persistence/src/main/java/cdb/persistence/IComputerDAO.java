package cdb.persistence;

import cdb.model.Computer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vkarassouloff on 26/04/17.
 */
public interface IComputerDAO extends JpaRepository<Computer, Long> {


    /**
     * Get result set for the last computer inserted in the DB.
     *
     * @return resultset
     */
    Computer findFirstByOrderByIdDesc();

    /***
     * Get computer count.
     * @param name constraint
     * @param companyName constraint
     * @return count of computer considering filters
     */
    int countByNameStartingWithOrCompanyNameStartingWith(String name, String companyName);

    /**
     * Get Computer matching filter.
     *
     * @param pageRequest offset/limit
     * @param name        constraint
     * @param companyName constraint
     * @return computers with DB
     */
    Page<Computer> getComputersByNameStartingWithOrCompanyNameStartingWith(Pageable pageRequest, String name, String companyName);

    /**
     * Get Computer matching filter.
     *
     * @param pageRequest offset/limit
     * @return computers with DB
     */
    Page<Computer> getComputersBy(Pageable pageRequest);


}
