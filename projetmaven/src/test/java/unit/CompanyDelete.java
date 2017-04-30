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

import java.sql.SQLException;
import java.sql.Savepoint;

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
    public void mdr2() throws Exception {
        TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
        Savepoint sp = TransactionHolder.get().setSavepoint();
        TransactionHolder.get().setAutoCommit(false);
        TransactionHolder.get().createStatement().execute("INSERT INTO " + SqlNames.COMPANY_TABLE_NAME + "(" + SqlNames.COMPANY_COL_COMPANY_NAME + ") VALUES ('gros lol')" );
        TransactionHolder.get().rollback(sp);
        TransactionHolder.close();
    }

    @Test
    public void mdr() {
        Savepoint sp = null;
        Computer testTransaction = new Computer.Builder()
                .withName("mdr computer de nouvelle comapny")
                .withCompany(newCompany)
                .build();

        try {

            TransactionHolder.set(Connector.getInstance().getDataSource().getConnection());
            TransactionHolder.get().setAutoCommit(false);
            sp = TransactionHolder.get().setSavepoint();
            computerDao.insert(testTransaction);
            System.out.println(testTransaction);
            //TransactionHolder.get().rollback();
            TransactionHolder.get().rollback(sp);
            TransactionHolder.get().commit();
            TransactionHolder.get().close();

            //Connector.getInstance().rollback(TransactionHolder.get());

            //TransactionHolder.get().commit();
        } catch (Exception e) {
            throw new RuntimeException("Qwerty");
        }
        TransactionHolder.close();
        assertEquals(null, computerServices.getComputer(testTransaction.getId()));
        Connector.getInstance().close(TransactionHolder.get());

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

        // Setup mock
        ComputerDAO mockedDao = mock(ComputerDAO.class);
        try {
            Mockito.doThrow(new DAODeleteException(SqlNames.COMPUTER_TABLE_NAME, "Test")).when(mockedDao).deleteComputerBelongingToCompany(newCompany.getId());
            companyServices.setComputerDao(mockedDao);
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
            System.out.println("Suppression des elements de test newComputer1 : " + computerDao.deleteById(newComputer1.getId()) + ", " +
                    "newComputer2 : " + computerDao.deleteById(newComputer2.getId()) + ", " +
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
