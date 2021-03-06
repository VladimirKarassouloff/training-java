package selenium;

import cdb.mapper.MapperDate;
import cdb.model.Computer;
import cdb.service.ComputerServiceImpl;
import cdb.service.IComputerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@ContextConfiguration(locations = {"/applicationContextTest.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SeleniumComputerTest {

    private WebDriver driver;

    @Autowired
    private IComputerService computerService;

    private static final String BASEURL = "http://localhost:8080/mydeployyy/";
    private static final String BASEURLADDEDIT = BASEURL + "computer";
    private static final String BASEURLREMOVE = BASEURL;
    private StringBuffer verificationErrors = new StringBuffer();

    /////////////////////////////////////////
    // IDs of element adding/editing computer
    private static final String NAME_COMPUTER_ID = "computerName";
    private static final String INTRODUCED_ID = "introduced";
    private static final String DISCONTINUED_ID = "discontinued";
    private static final String COMPANY_ID = "companyId";
    private static final String SUBMIT_ID = "submit-button";

    /// Web Elements for adding / editing computer
    private WebElement nameWE, introducedWE, discontinuedWE, companyWE, submitWE, bodyWE;
    private Select companySelect;
    ////////////////////////////////

    /////////////////////////////////

    //private static final String SUBMIT_DELETION_ID = "deleteSelected";
    //private static final String SUBMIT_DELETION_ID = "deleteSelected";
    // Web Elements in dashboard
    //private WebElement selectAllWE, submitDeletionWE;
    ////////////////////////////////////////////////
    @Before
    public void setUp() throws Exception {
        /*
        System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver");
        driver = new FirefoxDriver();
        */
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @Test
    public void testAddComputer() throws Exception {
        driver.get(BASEURLADDEDIT);

        // Grab inputs and submit
        getAllFormElement();

        // Default datas used for the test
        TestValues testValues = new Builder()
                .withName("MDR SELE ADD")
                .withCountBefore(computerService.getCountComputer())
                .build();

        System.out.println(testValues.countBeforeSubmit + " row before form post");

        // Post form
        fillFormAddEditInputs(testValues);

        submitWE.click();

        // Check if there is a new Computer
        testValues.countAfterSubmit = computerService.getCountComputer();
        assertEquals(testValues.countBeforeSubmit + 1, testValues.countAfterSubmit);
        System.out.println("Count is ok");

        // Test if all values inserted are correct
        Computer computerInserted = computerService.getPage(new PageRequest(0, 1),
                testValues.getNameComputer()
        ).getContent().get(0);
        System.out.println("Comparing now value of form vs inserted");
        assertEquals(testValues.nameComputer.equals(computerInserted.getName()), true);
        assertEquals(MapperDate.dateFromString(testValues.dateIntro).getTime(), computerInserted.getIntroduced().getTime());
        assertEquals(MapperDate.dateFromString(testValues.dateDisc).getTime(), computerInserted.getDiscontinued().getTime());
        assertEquals(Integer.parseInt(testValues.companyId), computerInserted.getCompany().getId());
        System.out.println("Form inputs values are ok");

    }

    @Test
    public void testEditComputer() throws Exception {
        testAddComputer();
        Computer computerJustAdded = computerService.getLastComputerInserted();
        driver.get(BASEURLADDEDIT + "?id=" + computerJustAdded.getId());
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);


        // Datas used for the test
        TestValues testValues = new Builder()
                .withName("MDR SELE MODIF")
                .withDateDisc("")
                .withDateIntro("1999-06-08")
                .withCompanyId("")
                .withCountBefore(computerService.getCountComputer())
                .build();

        // Taking count of computer before test
        System.out.println(testValues.countBeforeSubmit + " row before form post");

        // Get all references
        getAllFormElement();

        // Check if all input are preselected in inputs
        assertEquals(computerJustAdded.getName().equals(nameWE.getAttribute("value")), true);
        assertEquals(MapperDate.formatDate(computerJustAdded.getIntroduced()).equals(introducedWE.getAttribute("value")), true);
        assertEquals(MapperDate.formatDate(computerJustAdded.getDiscontinued()).equals(discontinuedWE.getAttribute("value")), true);
        if (computerJustAdded.getCompany() == null) {
            assertEquals(companySelect.getFirstSelectedOption().getAttribute("value").equals(""), true);
        } else {
            assertEquals(Integer.parseInt(companySelect.getFirstSelectedOption().getAttribute("value")), computerJustAdded.getCompany().getId());
        }
        System.out.println("All inputs are preselected");

        // Now we change values of inputs
        fillFormAddEditInputs(testValues);
        submitWE.click();

        // Asserting no computer got insert
        testValues.countAfterSubmit = computerService.getCountComputer();
        assertEquals(testValues.countAfterSubmit, testValues.countBeforeSubmit);
        System.out.println("Computer count is ok");

        // Comparing now the values
        Computer computerEdited = computerService.getComputer(computerJustAdded.getId());
        assertEquals(computerEdited.getName().equals(testValues.nameComputer), true);
        assertEquals(computerEdited.getIntroduced().getTime(), MapperDate.dateFromString(testValues.dateIntro).getTime());
        assertEquals(computerEdited.getDiscontinued(), null);
        assertEquals(computerEdited.getCompany(), null);
        System.out.println("New values for edit computer are ok");

    }


    /**
     * Post test values in web elements.
     */
    private void fillFormAddEditInputs(TestValues test) {
        nameWE.clear();
        nameWE.sendKeys(test.nameComputer);
        introducedWE.clear();
        introducedWE.sendKeys(test.dateIntro);
        discontinuedWE.clear();
        discontinuedWE.sendKeys(test.dateDisc);
        companySelect.selectByValue(test.companyId);
    }

    /**
     * Grab reference to DOM element required for the form.
     */
    private void getAllFormElement() {
        bodyWE = driver.findElement(By.tagName("body"));
        submitWE = driver.findElement(By.id(SUBMIT_ID));
        nameWE = driver.findElement(By.id(NAME_COMPUTER_ID));
        introducedWE = driver.findElement(By.id(INTRODUCED_ID));
        discontinuedWE = driver.findElement(By.id(DISCONTINUED_ID));
        companyWE = driver.findElement(By.id(COMPANY_ID));
        companySelect = new Select(driver.findElement(By.id(COMPANY_ID)));
    }


    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }


    /**
     * Class containing form values for the test.
     */
    public static class TestValues {

        private long countBeforeSubmit;
        private long countAfterSubmit;
        private Date d;
        private String nameComputer;
        private String dateIntro;
        private String dateDisc;
        private String companyId;

        /**
         * Default values for form
         */
        public TestValues() {
            d = new Date();
            dateIntro = "1999-04-04";
            dateDisc = "1999-04-05";
            companyId = "1";
        }

        public long getCountBeforeSubmit() {
            return countBeforeSubmit;
        }

        public void setCountBeforeSubmit(int countBeforeSubmit) {
            this.countBeforeSubmit = countBeforeSubmit;
        }

        public long getCountAfterSubmit() {
            return countAfterSubmit;
        }

        public void setCountAfterSubmit(int countAfterSubmit) {
            this.countAfterSubmit = countAfterSubmit;
        }

        public Date getD() {
            return d;
        }

        public void setD(Date d) {
            this.d = d;
        }

        public String getNameComputer() {
            return nameComputer;
        }

        public void setNameComputer(String nameComputer) {
            this.nameComputer = nameComputer + d.getTime();
        }

        public void setForcedNameComputer(String nameComputer) {
            this.nameComputer = nameComputer;
        }

        public String getFormDateIntro() {
            return dateIntro;
        }

        public void setDateIntro(String dateIntro) {
            this.dateIntro = dateIntro;
        }

        public String getFormDateDisc() {
            return dateDisc;
        }

        public void setDateDisc(String dateDisc) {
            this.dateDisc = dateDisc;
        }

        public String getFormCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }
    }

    /**
     * Builder for the TestValues.
     */
    public static class Builder {
        private TestValues test;

        public Builder() {
            test = new TestValues();
        }

        public TestValues build() {
            return test;
        }

        public Builder withName(String s) {
            test.setNameComputer(s);
            return this;
        }

        public Builder withDateIntro(String s) {
            test.dateIntro = s;
            return this;
        }

        public Builder withDateDisc(String s) {
            test.dateDisc = s;
            return this;
        }

        public Builder withCompanyId(String s) {
            test.companyId = s;
            return this;
        }

        public Builder withCountBefore(long i) {
            test.countBeforeSubmit = i;
            return this;
        }

        public Builder withCountAfter(int i) {
            test.countAfterSubmit = i;
            return this;
        }
    }

}