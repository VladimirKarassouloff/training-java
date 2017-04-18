import model.Company;
import org.junit.Test;
import validator.CompanyValidator;

import static org.junit.Assert.assertEquals;

/**
 * Created by vkarassouloff on 18/04/17.
 */
public class CompanyValidityTest {

    @Test
    public void testValidityNullName() {
        Company company = new Company(0, null);
        assertEquals(false, CompanyValidator.isValid(company));
    }

    @Test
    public void testValidityEmptyName() {
        Company company = new Company(0,"");
        assertEquals(false, CompanyValidator.isValid(company));
    }

    @Test
    public void testValidity() {
        Company company = new Company(0, "Ok super");
        assertEquals(true, CompanyValidator.isValid(company));
    }

}
