package cdb.persistence;

import cdb.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by vkarassouloff on 26/04/17.
 */
public interface ICompanyDAO extends JpaRepository<Company, Long> {

    /**
     * Get the last inserted record.
     *
     * @return last inserted company
     */
    Company findFirstByOrderByIdDesc();

}
