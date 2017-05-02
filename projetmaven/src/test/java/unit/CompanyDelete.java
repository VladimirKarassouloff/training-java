package unit;

import exception.DAODeleteException;
import model.Company;
import model.Computer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import persistence.CompanyDAO;
import persistence.ComputerDAO;
import persistence.Connector;
import services.CompanyServices;
import services.ComputerServices;
import services.TransactionHolder;
import utils.SqlNames;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Created by vkara on 30/04/2017.
 */
public class CompanyDelete {
    private CompanyServices companyServices;
    private ComputerServices computerServices;

    private CompanyDAO companyDao;
    private ComputerDAO computerDao;

    private Company newCompany;
    private Computer newComputer1, newComputer2;

    @Before
    public void setUp() throws Exception {
        companyServices = CompanyServices.getInstance();
        computerServices = ComputerServices.getInstance();

        companyDao = CompanyDAO.getInstance();
        computerDao = ComputerDAO.getInstance();

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
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get connection to DB");
        }

        try {
            companyDao.insert(newCompany);
            computerDao.insert(newComputer1);
            computerDao.insert(newComputer2);
            TransactionHolder.get().commit();
            TransactionHolder.close();
        } catch (Exception e) {
            throw new RuntimeException("Cannot insert data for test");
        }

    }


    @Test
    public void testDeleteCompany() {
        assertEquals(true, companyServices.getCompany(newCompany.getId()) != null);
        assertEquals(true, computerServices.getComputer(newComputer1.getId()) != null);
        assertEquals(true, computerServices.getComputer(newComputer2.getId()) != null);

        companyServices.delete(newCompany.getId());

        assertEquals(null, companyServices.getCompany(newCompany.getId()));
        assertEquals(null, computerServices.getComputer(newComputer1.getId()));
        assertEquals(null, computerServices.getComputer(newComputer2.getId()));
    }

    @Test
    public void testDeleteCompanyRollback() {
        // Assert that got inserted
        assertEquals(true, companyServices.getCompany(newCompany.getId()) != null);
        assertEquals(true, computerServices.getComputer(newComputer1.getId()) != null);
        assertEquals(true, computerServices.getComputer(newComputer2.getId()) != null);

        try {
            // Setup mock
            ComputerDAO mockedDao = mock(ComputerDAO.class);
            Mockito.doThrow(new DAODeleteException(SqlNames.COMPUTER_TABLE_NAME, "MOCKED OBJECT THROW EXCEPTION")).when(mockedDao).deleteComputerBelongingToCompany(newCompany.getId());
            companyServices.setComputerDao(mockedDao);
            // Test it
            companyServices.delete(newCompany.getId());
        } catch (DAODeleteException e) {
            e.printStackTrace();
        }

        // Assert that none got deleted
        assertEquals(true, computerServices.getComputer(newComputer1.getId()) != null);
        assertEquals(true, computerServices.getComputer(newComputer2.getId()) != null);
        assertEquals(true, companyServices.getCompany(newCompany.getId()) != null);
    }

    @After
    public void tearDown() throws Exception {
        // Cleanning up created entities
        try {
            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get connection to DB");
        }

        try {
            System.out.println("Suppression des elements de test newComputer1 & newComputer2 : " +
                    computerDao.deleteById(Arrays.asList(newComputer1.getId(),newComputer2.getId())) + ", " +
                    "newCompany : " + companyDao.delete(newCompany.getId()));
        } catch (DAODeleteException e) {
            System.err.println("Error happened during cleanup");
        }

        try {
            TransactionHolder.get().commit();
            TransactionHolder.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Couldn't commit delete for clean up");
        }
    }


}
