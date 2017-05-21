package unit;

import cdb.exception.DAODeleteException;
import cdb.exception.DAOInsertException;
import cdb.model.Company;
import cdb.model.Computer;
import cdb.persistence.CompanyDAOImpl;
import cdb.persistence.ComputerDAOImpl;
import cdb.service.ICompanyService;
import cdb.service.IComputerService;
import cdb.utils.SqlNames;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;


@ContextConfiguration(locations = {"/applicationContextTest.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CompanyDeleteTest {

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IComputerService computerService;

    @Autowired
    private CompanyDAOImpl companyDaoImpl;

    @Autowired
    private ComputerDAOImpl computerDaoImpl;

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
            companyDaoImpl.insert(newCompany);
            newCompany.setId(companyDaoImpl.getLastCompanyInserted().getId());
            computerDaoImpl.insert(newComputer1);
            newComputer1 = computerDaoImpl.getLastComputerInserted();
            computerDaoImpl.insert(newComputer2);
            newComputer2 = computerDaoImpl.getLastComputerInserted();
        } catch (DAOInsertException e) {
            throw new RuntimeException("Cannot insert data for test");
        }

    }


    @Test
    public void testDeleteCompany() {
        assertEquals(true, companyService.getCompany(newCompany.getId()) != null);
        assertEquals(true, computerService.getComputer(newComputer1.getId()) != null);
        assertEquals(true, computerService.getComputer(newComputer2.getId()) != null);

        companyService.delete(newCompany.getId());

        try {
            companyService.getCompany(newCompany.getId());
            fail("Should have failed");
        } catch (Exception e) {
            System.out.println("Success delete company");
        }

        try {
            computerService.getComputer(newComputer1.getId());
            fail("Should have failed");
        } catch (Exception e) {
            System.out.println("Success delete newComputer1");
        }

        try {
            computerService.getComputer(newComputer2.getId());
            fail("Should have failed");
        } catch (Exception e) {
            System.out.println("Success delete newComputer2");
        }

    }

    @Test
    public void testDeleteCompanyRollback() {
        // Assert that got inserted
        assertEquals(true, companyService.getCompany(newCompany.getId()) != null);
        assertEquals(true, computerService.getComputer(newComputer1.getId()) != null);
        assertEquals(true, computerService.getComputer(newComputer2.getId()) != null);

        try {
            // Setup mock
            ComputerDAOImpl mockedDao = mock(ComputerDAOImpl.class);
            Mockito.doThrow(new DAODeleteException(SqlNames.COMPUTER_TABLE_NAME, "MOCKED OBJECT THROW EXCEPTION")).when(mockedDao).deleteComputerBelongingToCompany(newCompany.getId());
            companyService.setComputerDaoImpl(mockedDao);

            // Test it
            companyService.delete(newCompany.getId());
        } catch (DAODeleteException | RuntimeException e) {
            e.printStackTrace();
        }

        // Assert that none got deleted
        try {
            assertEquals(true, computerService.getComputer(newComputer1.getId()) != null);
        } catch (Exception e) {
            fail("Error while selecting rollback data");
        }

        try {
            assertEquals(true, computerService.getComputer(newComputer2.getId()) != null);
        } catch (Exception e) {
            fail("Error while selecting rollback data");
        }

        try {
            assertEquals(true, companyService.getCompany(newCompany.getId()) != null);
        } catch (Exception e) {
            fail("Error while selecting rollback data");
        }

    }

    @After
    public void tearDown() throws Exception {

        try {
            System.out.println("Suppression des elements de test newComputer1 & newComputer2 : " +
                    computerDaoImpl.delete(newComputer1.getId(), newComputer2.getId()) + ", " +
                    "newCompany : " + companyDaoImpl.delete(newCompany.getId()));
        } catch (DAODeleteException e) {
            System.err.println("Error happened during cleanup");
        }

    }


}
