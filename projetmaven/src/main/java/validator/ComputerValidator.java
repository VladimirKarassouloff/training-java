package validator;

import model.Computer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by vkarassouloff on 18/04/17.
 */
public class ComputerValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerValidator.class);

    /**
     * Check whether a computer is valid or not before being updated / inserted in DB.
     * @param computer being manipulated
     * @return validity
     */
    public static boolean isValid(Computer computer) {
        if (computer == null) {
            LOGGER.info("Computer not valid because null");
            return false;
        } else if (computer.getName() == null || computer.getName().equals("")) {
            LOGGER.info("Computer not valid because name is empty");
            return false;
        } else if (computer.getDiscontinued() != null && computer.getIntroduced() != null && computer.getIntroduced().after(computer.getDiscontinued())) {
            LOGGER.info("Computer not valid because date aren't consistent");
            return false;
        }

        return true;
    }

}
