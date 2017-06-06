package cdb.service;

import cdb.dto.CompanyDTO;
import cdb.dto.ComputerDTO;
import cdb.exception.FormException;
import cdb.model.Computer;
import cdb.model.RestResponsePage;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Service
public class WSClient {

    private Client client;
    private WebTarget base = null;

    /**
     * Default constructor.
     */
    public WSClient() {
        ClientConfig clientConfig = new ClientConfig();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("root", "root");
        clientConfig.register(feature);
        //clientConfig.register(JacksonJsonProvider.class);
        client = ClientBuilder.newClient();
        client.register(feature);
        base = client.target("http://localhost:8080/mydeployyy/api");
    }

    /**
     * Get company by id.
     *
     * @param id of the company
     * @return company
     */
    public CompanyDTO getCompanyWithId(long id) {
        Invocation.Builder invocationBuilder = base.path("companies/" + id).request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        if (response.getStatus() == 200) {
            return response.readEntity(CompanyDTO.class);
        }
        throw new RuntimeException("WSClient : unable to get company with id " + id);
    }

    /**
     * Get all companies.
     *
     * @return list of companies
     */
    public List<CompanyDTO> getCompanies() {
        Invocation.Builder invocationBuilder = base.path("companies").request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<List<CompanyDTO>>() {
            });
        }
        throw new RuntimeException("WSClient : unable to get companies");
    }

    /**
     * Get all companies.
     *
     * @return list of companies
     */
    public RestResponsePage<CompanyDTO> getCompanies(PageRequest pr) {
        Invocation.Builder invocationBuilder = base.path("companies/page")
                .queryParam("page",pr.getPageNumber())
                .queryParam("size",pr.getPageSize())
                .request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<RestResponsePage<CompanyDTO>>() {
            });
        }
        throw new RuntimeException("WSClient : unable to get company");
    }

    /**
     * Get all computers.
     *
     * @return list of computers
     */
    public List<ComputerDTO> getComputers() {
        Invocation.Builder invocationBuilder = base.path("computers").request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<List<ComputerDTO>>() {
            });
        }
        throw new RuntimeException("WSClient : unable to get computers");
    }

    /**
     * Get computers page.
     *
     * @param pr pagerequest
     * @return computers
     */
    public RestResponsePage<ComputerDTO> getComputers(PageRequest pr) {
        String property = null, directionStr = null;
        if (pr.getSort() != null && pr.getSort().iterator().hasNext()) {
            Sort.Order order = pr.getSort().iterator().next();
            property = order.getProperty();
            directionStr = order.getDirection().name();
        } else {
            property = Computer.colToProperty(0);
            directionStr = "ASC";
        }

        Invocation.Builder invocationBuilder = base.path("computers/page")
                .queryParam("page",pr.getPageNumber())
                .queryParam("size",pr.getPageSize())
                .queryParam("property",property)
                .queryParam("direction",directionStr)
                .request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<RestResponsePage<ComputerDTO>>() {
            });
        }
        throw new RuntimeException("WSClient : unable to get computers");
    }


    /**
     * Get computer by id.
     *
     * @param id of the computer
     * @return computer
     * @throws NotFoundException if computer does not exist
     */
    public ComputerDTO getComputer(long id) throws NotFoundException {
        Invocation.Builder invocationBuilder = base.path("computers/" + id).request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        if (response.getStatus() == 404) {
            throw new NotFoundException("Computer with id " + id + " not found");
        } else {
            return response.readEntity(ComputerDTO.class);
        }
    }

    /**
     * Add computer
     *
     * @param computer form
     * @return computer added
     * @throws FormException if error in computer fields
     */
    public ComputerDTO addComputer(ComputerDTO computer) throws FormException {
        Invocation.Builder invocationBuilder = base.path("computer").request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(computer, MediaType.APPLICATION_JSON));

        try {
            return response.readEntity(ComputerDTO.class);
        } catch (BadRequestException e) {
            throw new FormException(response.readEntity(String.class));
        }
    }

    /**
     * Edit computer
     *
     * @param computer form
     * @return computer edited
     * @throws FormException     if error in computer fields
     * @throws NotFoundException if computer does not exist
     */
    public ComputerDTO editComputer(ComputerDTO computer) throws FormException, NotFoundException {
        if (computer.getId() == null) {
            throw new RuntimeException("WSClient : id for editing computer is null");
        }

        Invocation.Builder invocationBuilder = base.path("computers/" + computer.getId()).request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(computer, MediaType.APPLICATION_JSON));

        try {
            if (response.getStatus() == 404) {
                throw new NotFoundException("Computer not found");
            }
            return response.readEntity(ComputerDTO.class);
        } catch (BadRequestException e) {
            throw new FormException(response.readEntity(String.class));
        }
    }

    /**
     * Delete computer by id.
     *
     * @param id of the computer
     */
    public void deleteComputer(long id) {
        base.path("computers/" + id).request(MediaType.APPLICATION_JSON).delete();
    }


}
