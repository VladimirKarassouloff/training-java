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


    @Test
    public void testValidityBadDates() {
        Computer computer = new Computer(null, "mdr", MapperDate.dateFromString("2017-04-05"), MapperDate.dateFromString("2017-04-04"));
        assertEquals(false, ComputerValidator.isValid(computer));
    }

    @Test
    public void testValidityBadName() {
        Computer computer = new Computer(null, "", null, null);
        Computer computer2 = new Computer(null, null, null, null);
        assertEquals(false, ComputerValidator.isValid(computer) && ComputerValidator.isValid(computer2));
    }

    @Test
    public void testValidity() {
        Computer computer = new Computer(null, "mdr", MapperDate.dateFromString("2017-04-03"), MapperDate.dateFromString("2017-04-04"));
        Computer computer2 = new Computer(new Company(0, "lol mdr"), "mdr", MapperDate.dateFromString("2017-04-03"), MapperDate.dateFromString("2017-04-04"));
        Computer computer3 = new Computer(new Company(0, "lol mdr"), "mdr", null, MapperDate.dateFromString("2017-04-04"));
        Computer computer4 = new Computer(new Company(0, "lol mdr"), "mdr", MapperDate.dateFromString("2017-04-03"), null);
        Computer computer5 = new Computer(new Company(0, "lol mdr"), "mdr", null, null);
        assertEquals(true, ComputerValidator.isValid(computer) && ComputerValidator.isValid(computer2)
                && ComputerValidator.isValid(computer3) && ComputerValidator.isValid(computer4) && ComputerValidator.isValid(computer5));
    }


}
