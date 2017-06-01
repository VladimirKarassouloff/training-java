package unit;

import cdb.model.Company;
import cdb.model.Computer;
import cdb.persistence.ICompanyDAO;
import cdb.persistence.IComputerDAO;
import cdb.service.ICompanyService;
import cdb.service.IComputerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


@ContextConfiguration(locations = {"/applicationContextTest.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CompanyDeleteTest {

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IComputerService computerService;

    @Autowired
    private ICompanyDAO companyDaoImpl;

    @Autowired
    private IComputerDAO computerDaoImpl;

    private Company newCompany;
    private Computer newComputer1, newComputer2;

    @Before
    public void setUp() throws Exception {

        newCompany = new Company("mdr nouvelle company");
        newComputer1 = new Computer.Builder()
                .withName("mdr computer de nouvelle comapny")
                .withCompany(newCompany)
                .build();
        newComputer2 = new Computer.Builder()
                .withName("mdr computer de nouvelle comapny2")
                .withCompany(newCompany)
                .build();


        try {
            companyDaoImpl.save(newCompany);
            newCompany.setId(companyDaoImpl.findFirstByOrderByIdDesc().getId());
            computerDaoImpl.save(newComputer1);
            newComputer1 = computerDaoImpl.findFirstByOrderByIdDesc();
            computerDaoImpl.save(newComputer2);
            newComputer2 = computerDaoImpl.findFirstByOrderByIdDesc();
        } catch (DataAccessException e) {
            throw new RuntimeException("Cannot insert data for test");
        }

    }


    @Test
    public void testDeleteCompany() {
        assertEquals(true, companyService.getCompany(newCompany.getId()) != null);
        assertEquals(true, computerService.getComputer(newComputer1.getId()) != null);
        assertEquals(true, computerService.getComputer(newComputer2.getId()) != null);

        companyService.delete(newCompany.getId());

        if (companyService.getCompany(newCompany.getId()) != null) {
            fail("Should have failed");
        }
        if (computerService.getComputer(newComputer1.getId()) != null) {

            fail("Should have failed");
        }
        if (computerService.getComputer(newComputer2.getId()) != null) {
            fail("Should have failed");
        }

    }


    @After
    public void tearDown() throws Exception {

        try {
            System.out.println("Suppression des elements de test newComputer1 & newComputer2 & newCompany");
        } catch (Exception e) {
            System.out.println("Error cleanning up data from CompanyDeleteTest : " + e.getMessage());
        }
        try {
            computerService.deleteComputer(newComputer1.getId(), newComputer2.getId());
        } catch (Exception e) {
            System.out.println("Error cleanning up data from CompanyDeleteTest : " + e.getMessage());
        }
        try {
            companyDaoImpl.delete(newCompany.getId());
        } catch (RuntimeException e) {
            System.out.println("Error cleanning up data from CompanyDeleteTest : " + e.getMessage());
        }
    }

}
