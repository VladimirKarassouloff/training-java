package cdb.ws;

import cdb.dto.CompanyDTO;
import cdb.dto.ComputerDTO;
import cdb.exception.FormException;
import cdb.mapper.MapperCompany;
import cdb.mapper.MapperComputer;
import cdb.model.RestResponsePage;
import cdb.service.ICompanyService;
import cdb.service.IComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class RestWebserviceController {

    private IComputerService computerService;

    private ICompanyService companyService;

    private MapperComputer mapperComputer;

    private MapperCompany mapperCompany;

    /**
     * Default const.
     *
     * @param s1             service
     * @param s2             service
     * @param mapperCompany  mapper
     * @param mapperComputer mapper
     */
    @Autowired
    public RestWebserviceController(IComputerService s1, ICompanyService s2, MapperComputer mapperComputer, MapperCompany mapperCompany) {
        this.computerService = s1;
        this.companyService = s2;
        this.mapperComputer = mapperComputer;
        this.mapperCompany = mapperCompany;
    }


    @RequestMapping(path = "/computers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ComputerDTO> getComputers() {
        return mapperComputer.toDTOs(computerService.getComputers());
    }

    @RequestMapping(path = "/computers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ComputerDTO> getComputer(@PathVariable("id") long id) {
        ComputerDTO comp = mapperComputer.toDTO(computerService.getComputer(id));
        if (comp != null) {
            return ResponseEntity.ok(comp);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/computers/page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponsePage<ComputerDTO>> getComputer(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("property") String property,
            @RequestParam("direction") String direction) {

        return ResponseEntity.ok(new RestResponsePage<>(computerService.getComputers(
                new PageRequest(page, size, (direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC), property))
                .map(mapperComputer::toDTO)));
    }

    @RequestMapping(path = "/computers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addComputer(@RequestBody ComputerDTO computerDTO) {
        try {
            return ResponseEntity.ok(mapperComputer.toDTO(computerService.formAddComputer(computerDTO)));
        } catch (FormException e) {
            return new ResponseEntity<>("aze", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/computers/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editComputer(@PathVariable("id") long id, @RequestBody ComputerDTO computerDTO) {
        try {
            if (computerService.getComputer(id) != null) {
                return ResponseEntity.ok(mapperComputer.toDTO(computerService.formUpdateComputer(computerDTO)));
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } catch (FormException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @RequestMapping(path = "/computers/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteComputer(@PathVariable("id") long id) {
        computerService.deleteComputer(id);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(path = "/companies/page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponsePage<CompanyDTO>> getCompanies(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size) {

        return ResponseEntity.ok(new RestResponsePage<>(companyService.getPagedCompany(new PageRequest(page, size)).map(mapperCompany::toDto)));
    }

    @RequestMapping(path = "/companies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompanyDTO> getCompanies() {
        return mapperCompany.toDtos(companyService.getCompanies());
    }

    @RequestMapping(path = "/companies/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable("id") int id) {
        CompanyDTO comp = mapperCompany.toDto(companyService.getCompany(id));
        if (comp != null) {
            return new ResponseEntity<>(comp, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
