package cdb.validator;

import cdb.exception.InvalidComputerException;
import cdb.model.Computer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cdb.utils.UtilsSql.MOST_ANCIENT_DATE;

/**
 * Created by vkarassouloff on 18/04/17.
 */
public class ComputerValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerValidator.class);


    /**
     * Check whether a computer is valid or not before being updated / inserted in DB.
     *
     * @param computer being manipulated
     * @throws InvalidComputerException if computer is not valid for sql query
     */
    public static void checkValidity(Computer computer) throws InvalidComputerException {
        if (computer == null) {
            LOGGER.info("Computer not valid because null");
            throw new InvalidComputerException("Computer not valid because null");
        } else if (computer.getName() == null || computer.getName().equals("")) {
            LOGGER.info("Computer not valid because name is empty");
            throw new InvalidComputerException("Computer not valid because name is empty");
        } else if (computer.getDiscontinued() != null && computer.getIntroduced() != null && computer.getIntroduced().after(computer.getDiscontinued())) {
            LOGGER.info("Computer not valid because date aren't consistent");
            throw new InvalidComputerException("Computer not valid because date aren't consistent");
        } else if (computer.getDiscontinued() != null && !computer.getDiscontinued().after(MOST_ANCIENT_DATE)) {
            LOGGER.info("Computer not valid because discontinued date is before 1970-01-01");
            throw new InvalidComputerException("Computer not valid because discontinued date is before 1970-01-01");
        } else if (computer.getIntroduced() != null && !computer.getIntroduced().after(MOST_ANCIENT_DATE)) {
            LOGGER.info("Computer not valid because introduced date is before 1970-01-01");
            throw new InvalidComputerException("Computer not valid because introduced date is before 1970-01-01");
        }

    }

    /**
     * Check if computer has valid id, and if company pointed on exist (if any).
     *
     * @param computer checked
     * @throws InvalidComputerException if computer is not valid for sql query
     */
    public static void checkValidityForUpdate(Computer computer) throws InvalidComputerException {
        if (computer == null) {
            LOGGER.info("Computer not valid because of null value");
            throw new InvalidComputerException("Computer not valid because of null value");
        } else if (computer.getId() == null || computer.getId() == 0) {
            LOGGER.info("Computer not valid because id should not be null");
            throw new InvalidComputerException("Computer not valid because id should not be null");
        }
        checkValidity(computer);
    }

}
