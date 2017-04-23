package mapper;

import dto.ComputerDTO;
import exception.MapperException;
import model.Company;
import model.Computer;
import persistence.ComputerDAO;
import utils.Format;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapperComputer {


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
            rs.next();
            Computer c = mapResultSetToObjectAux(rs);
            rs.close();
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
            rs.close();
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
            int computerId = rs.getInt(ComputerDAO.COL_COMPUTER_ID);
            int companyId = rs.getInt(ComputerDAO.COL_COMPUTER_COMPANY_ID);
            String computerName = rs.getString(ComputerDAO.COL_COMPUTER_NAME);
            Date introduced = rs.getDate(ComputerDAO.COL_COMPUTER_INTRODUCED);
            Date discontinued = rs.getDate(ComputerDAO.COL_COMPUTERDISCONTINUED);
            String companyName = rs.getString(ComputerDAO.COL_JOINED_COMPANY_NAME);
            Company company;
            if (companyId == 0) {
                company = null;
            } else if (ComputerDAO.CACHE_COMPANY.get(companyId) != null) {
                company = ComputerDAO.CACHE_COMPANY.get(companyId);
            } else {
                company = new Company(companyId, companyName);
                ComputerDAO.CACHE_COMPANY.put(companyId, company);
            }
            return new Computer(computerId, company, computerName, introduced, discontinued);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
