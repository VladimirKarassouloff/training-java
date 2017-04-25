package unit;

import exception.InvalidComputerException;
import mapper.MapperDate;
import model.Company;
import model.Computer;
import org.junit.Test;
import utils.Format;
import validator.ComputerValidator;

import static org.junit.Assert.assertEquals;

/**
 * Created by vkarassouloff on 18/04/17.
 */
public class ComputerValidityTest {

    //@Test(expected=IllegalArgumentException.class)

    @Test(expected=InvalidComputerException.class)
    public void testValidityBadDates() throws InvalidComputerException {
        Computer computer = new Computer(null, "mdr", MapperDate.dateFromString("2017-04-05"), MapperDate.dateFromString("2017-04-04"));
        ComputerValidator.checkValidity(computer);
    }

    @Test(expected=InvalidComputerException.class)
    public void testValidityBadName() throws InvalidComputerException {
        Computer computer = new Computer(null, "", null, null);
        ComputerValidator.checkValidity(computer);
    }

    @Test(expected=InvalidComputerException.class)
    public void testValidityBadName2() throws InvalidComputerException {
        Computer computer = new Computer(null, null, null, null);
        ComputerValidator.checkValidity(computer);
    }

    @Test
    public void testValidity() throws InvalidComputerException {
        Computer computer = new Computer(null, "mdr", MapperDate.dateFromString("2017-04-03"), MapperDate.dateFromString("2017-04-04"));
        Computer computer2 = new Computer(new Company(0, "lol mdr"), "mdr", MapperDate.dateFromString("2017-04-03"), MapperDate.dateFromString("2017-04-04"));
        Computer computer3 = new Computer(new Company(0, "lol mdr"), "mdr", null, MapperDate.dateFromString("2017-04-04"));
        Computer computer4 = new Computer(new Company(0, "lol mdr"), "mdr", MapperDate.dateFromString("2017-04-03"), null);
        Computer computer5 = new Computer(new Company(0, "lol mdr"), "mdr", null, null);
        ComputerValidator.checkValidity(computer);
        ComputerValidator.checkValidity(computer2);
        ComputerValidator.checkValidity(computer3);
        ComputerValidator.checkValidity(computer4);
        ComputerValidator.checkValidity(computer5);
    }


}
