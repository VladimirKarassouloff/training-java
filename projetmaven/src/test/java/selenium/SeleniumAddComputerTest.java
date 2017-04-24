package selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import mapper.MapperDate;
import model.Computer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import services.ComputerServices;
import validator.CompanyValidator;

public class SeleniumAddComputerTest {

    private WebDriver driver;
    private JavascriptExecutor jse;

    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();

    private static final String NAME_COMPUTER_ID = "computerName";
    private static final String INTRODUCED_ID = "introduced";
    private static final String DISCONTINUED_ID = "discontinued";
    private static final String COMPANY_ID = "companyId";
    private static final String SUBMIT_ID = "submit-button";

    private WebElement nameComputer, introducedDate, discontinuedDate, submitButton, body;
    private Select company;

    @Before
    public void setUp() throws Exception {
        /*
        System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver");
        driver = new FirefoxDriver();
        */
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        driver = new ChromeDriver();
        baseUrl = "http://localhost:8080/mydeploy/computer";
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        jse = (JavascriptExecutor) driver;

    }

    @Test
    public void testAddComputer() throws Exception {
        driver.get(baseUrl);

        // Grab inputs and submit
        getAllFormElement();

        // Datas used for the test
        int countBeforeSubmit, countAfterSubmit;
        Date d = new Date();
        String formNameComputer = "MDR TEST SELENIUM " + d.getTime();
        String formDateIntro = "1999-04-04";
        String formDateDisc = "1999-04-05";
        String formCompanyId;
        countBeforeSubmit = ComputerServices.getCountComputer();
        System.out.println(countBeforeSubmit + " row before form post");

        // Post form
        introducedDate.sendKeys();
        nameComputer.sendKeys(formNameComputer);
        introducedDate.sendKeys(formDateIntro);
        discontinuedDate.sendKeys(formDateDisc);
        company.selectByIndex(1);
        formCompanyId = company.getFirstSelectedOption().getAttribute("value");
        submitButton.click();

        // Test result
        System.out.println(countBeforeSubmit + " row before form post");
        countAfterSubmit = ComputerServices.getCountComputer();

        assertEquals(countBeforeSubmit + 1, countAfterSubmit);
        System.out.println("Count is ok");
        Computer computerInserted = ComputerServices.getLastComputerInserted();
        System.out.println("Comparing now value of form vs inserted");
        assertEquals(formNameComputer.equals(computerInserted.getName()), true);
        assertEquals(MapperDate.dateFromString(formDateIntro).getTime() == computerInserted.getIntroduced().getTime(), true);
        assertEquals(MapperDate.dateFromString(formDateDisc).getTime() == computerInserted.getDiscontinued().getTime(), true);
        assertEquals(Integer.parseInt(formCompanyId) == computerInserted.getCompany().getId(), true);
        System.out.println("Form ok");
        // assertEquals(true, true);
    }

    @Test
    public void testEditComputer() throws Exception {
        testAddComputer();
        Computer computer = ComputerServices.getLastComputerInserted();
        driver.get(baseUrl + "?id=" + computer.getId());

    }


    /**
     * Grab reference to DOM element required for the form.
     */
    private void getAllFormElement() {
        body = driver.findElement(By.tagName("body"));
        submitButton = driver.findElement(By.id(SUBMIT_ID));
        nameComputer = driver.findElement(By.id(NAME_COMPUTER_ID));
        introducedDate = driver.findElement(By.id(INTRODUCED_ID));
        discontinuedDate = driver.findElement(By.id(DISCONTINUED_ID));
        company = new Select(driver.findElement(By.id(COMPANY_ID)));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}