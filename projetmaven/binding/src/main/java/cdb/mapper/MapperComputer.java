package cdb.mapper;

import cdb.dto.ComputerDTO;
import cdb.exception.MapperException;
import cdb.model.Company;
import cdb.model.Computer;
import cdb.utils.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MapperComputer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapperComputer.class);

    /**
     * Build computer from computerdto.
     *
     * @param computerDTO build from
     * @return computer
     * @throws MapperException if id point on non valid objects
     */
    public Computer mapDTOToObject(ComputerDTO computerDTO) throws MapperException {
        if (computerDTO == null) {
            return null;
        }

        Date dateDiscontinued = null, dateIntroduced = null;
        if (!computerDTO.getIntroduced().isEmpty() && (dateIntroduced = MapperDate.dateFromString(computerDTO.getIntroduced())) == null) {
            throw new MapperException("Something is wrong with introduction date");
        } else if (!computerDTO.getDiscontinued().isEmpty() && (dateDiscontinued = MapperDate.dateFromString(computerDTO.getDiscontinued())) == null) {
            throw new MapperException("Something is wrong with discontinued date");
        }

        return new Computer.Builder()
                .withId(computerDTO.getId() == null ? 0 : computerDTO.getId())
                .withName(Format.protectAgainstInjection(computerDTO.getName()))
                .withCompany(computerDTO.getCompanyId() == null ? null : new Company(computerDTO.getCompanyId(), null))
                .withDiscontinued(dateDiscontinued)
                .withIntroduced(dateIntroduced)
                .build();
    }


    /**
     * Take a computer and map it to computer cdb.cdb.dto.dto.
     *
     * @param computer that you want to map
     * @return computerDto
     */
    public ComputerDTO toDTO(Computer computer) {
        if (computer == null) {
            return null;
        }

        return new ComputerDTO.Builder()
                .withId(computer.getId())
                .withName(computer.getName())
                .withCompanyId(computer.getCompany() != null ? computer.getCompany().getId() : null)
                .withCompanyName(computer.getCompany() != null ? computer.getCompany().getName() : "")
                .withIntroducedDate(MapperDate.formatDate(computer.getIntroduced()))
                .withDiscontinuedDate(MapperDate.formatDate(computer.getDiscontinued()))
                .build();
    }

    /**
     * Take list of computer, and map them to computerdto.
     *
     * @param computers that you want to convert to DTOs
     * @return list of computer
     */
    public List<ComputerDTO> toDTOs(List<Computer> computers) {
        List<ComputerDTO> mapped = new ArrayList<>();
        for (Computer comp : computers) {
            mapped.add(toDTO(comp));
        }
        return mapped;

    }
}
