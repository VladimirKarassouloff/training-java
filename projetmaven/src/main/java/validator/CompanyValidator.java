package validator;

import model.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by vkarassouloff on 18/04/17.
 */
public class CompanyValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyValidator.class);

    /**
     * Tell whether a company is valid or not before inserting / updating in database.
     * @param company being manipulated
     * @return validity
     */
    public static boolean isValid(Company company) {
        if (company == null) {
            LOGGER.info("Company is not valid because null");
            return false;
        } else if (company.getName() == null || company.getName().equals("")) {
            LOGGER.info("Company is not valid because name is empty");
            return false;
        }

        return true;
    }

}
