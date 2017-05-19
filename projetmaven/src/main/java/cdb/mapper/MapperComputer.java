package cdb.mapper;

import cdb.dto.ComputerDTO;
import cdb.exception.MapperException;
import cdb.model.Company;
import cdb.model.Computer;
import cdb.utils.Format;
import cdb.utils.SqlNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapperComputer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapperComputer.class);


    /**
     * Build computer from computerdto.
     *
     * @param computerDTO build from
     * @return computer
     * @throws MapperException if id point on non valid objects
     */
    public static Computer mapDTOToObject(ComputerDTO computerDTO) throws MapperException {

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
     * Map resultset to computer.
     *
     * @param rs resultset
     * @return computer
     */
    public static Computer mapResultSetToObject(ResultSet rs) {

        try {
            Computer c = null;
            if (rs.next()) {
                c = mapResultSetToObjectAux(rs);
            }
            return c;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Map resultset to computers.
     *
     * @param rs resultset
     * @return computers
     */
    public static List<Computer> mapResultSetToObjects(ResultSet rs) {
        List<Computer> list = new ArrayList<Computer>();
        try {
            while (rs.next()) {
                list.add(MapperComputer.mapResultSetToObjectAux(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Mapp Result set to computer without closing the resultset.
     *
     * @param rs result set
     * @return computer
     */
    public static Computer mapResultSetToObjectAux(ResultSet rs) {
        try {
            int computerId = rs.getInt(SqlNames.COMPUTER_COL_COMPUTER_ID);
            int companyId = rs.getInt(SqlNames.COMPUTER_COL_COMPUTER_COMPANY_ID);
            String computerName = rs.getString(SqlNames.COMPUTER_COL_COMPUTER_NAME);
            Date introduced = rs.getDate(SqlNames.COMPUTER_COL_COMPUTER_INTRODUCED);
            Date discontinued = rs.getDate(SqlNames.COMPUTER_COL_COMPUTERDISCONTINUED);
            String companyName = rs.getString(SqlNames.COMPUTER_COL_JOINED_COMPANY_NAME);

            Company company;
            if (companyId == 0) {
                company = null;
            } else {
                company = new Company(companyId, companyName);
            }
            return new Computer(computerId, company, computerName, introduced, discontinued);
        } catch (SQLException e) {
            LOGGER.info("MapperComputer : error while mapping from resultset : " + e.getMessage());
        }
        return null;
    }


    /**
     * Take a computer and map it to computer cdb.dto.
     *
     * @param computer that you want to map
     * @return computerDto
     */
    public static ComputerDTO toDTO(Computer computer) {
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
    public static List<ComputerDTO> toDTOs(List<Computer> computers) {
        List<ComputerDTO> mapped = new ArrayList<>();
        for (Computer comp : computers) {
            mapped.add(MapperComputer.toDTO(comp));
        }
        return mapped;

    }
}
