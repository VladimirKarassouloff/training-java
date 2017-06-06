package exclusion;

import cdb.dto.CompanyDTO;
import cdb.dto.ComputerDTO;
import cdb.exception.FormException;
import cdb.model.Company;
import cdb.model.Computer;
import cdb.model.RestResponsePage;
import cdb.persistence.ICompanyDAO;
import cdb.persistence.IComputerDAO;
import cdb.service.ICompanyService;
import cdb.service.IComputerService;
import cdb.service.WSClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@ContextConfiguration(locations = {"/applicationContextTest2.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class WSClientTest {

    @Autowired
    public WSClient client;

    @Before
    public void setUp() throws Exception {

    }


    @Test
    public void GROSTEST() {
        System.out.println(client.getComputers());
        RestResponsePage<ComputerDTO> comp = client.getComputers(new PageRequest(0, 10));
        RestResponsePage<CompanyDTO> compa = client.getCompanies(new PageRequest(0, 5));
        System.out.println(compa.getSize());
        System.out.println(comp.getSize());
    }

    @Test
    public void test() {
        ComputerDTO cdto = null;
        try {
            System.out.println(cdto = client.addComputer(new ComputerDTO.Builder().withIntroducedDate("2016-04-04")
                    .withDiscontinuedDate("2017-05-05")
                    .withName("mdr le nom")
                    .build()));
        } catch (FormException e) {
            throw new RuntimeException("Failed to add computer");
        }

        try {
            String newName = "mdr le nom2";
            cdto.setName(newName);
            System.out.println(cdto = client.editComputer(cdto));
            assertEquals(newName.equals(cdto.getName()), true);
        } catch (FormException e) {
            throw new RuntimeException("Failed to edit computer");
        }

        client.deleteComputer(cdto.getId());

        try {
            client.getComputer(cdto.getId());
            fail("Should have failed");
        } catch (Exception e) {
            System.out.println("OK");
        }

        try {
            client.editComputer(cdto);
            fail("Should have failed");
        } catch (Exception e) {
            System.out.println("OK");
        }
        System.out.println();

    }


    @After
    public void tearDown() throws Exception {
    }

}
