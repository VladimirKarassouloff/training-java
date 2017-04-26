package mapper;

import dto.ComputerDTO;
import persistence.ComputerDAO;
import utils.Format;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vkarassouloff on 18/04/17.
 */
public class MapperComputerDTO {

    /**
     * Map resultset to computer.
     * @param rs resultset
     * @return computer
     */
    public static ComputerDTO mapResultSetToObject(ResultSet rs) {

        try {
            rs.next();
            ComputerDTO c = mapResultSetToObjectAux(rs);
            rs.close();
            return c;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Map resultset to computers.
     * @param rs resultset
     * @return computers
     */
    public static List<ComputerDTO> mapResultSetToObjects(ResultSet rs) {
        List<ComputerDTO> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(MapperComputerDTO.mapResultSetToObjectAux(rs));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Mapp Result set to computer without closing the resultset.
     * @param rs result set
     * @return computer
     */
    public static ComputerDTO mapResultSetToObjectAux(ResultSet rs) {
        try {
            int computerId = rs.getInt(ComputerDAO.COL_COMPUTER_ID);
            int companyId = rs.getInt(ComputerDAO.COL_COMPUTER_COMPANY_ID);
            String computerName = rs.getString(ComputerDAO.COL_COMPUTER_NAME);
            Date introduced = rs.getDate(ComputerDAO.COL_COMPUTER_INTRODUCED);
            Date discontinued = rs.getDate(ComputerDAO.COL_COMPUTERDISCONTINUED);
            String companyName = rs.getString(ComputerDAO.COL_JOINED_COMPANY_NAME);
            return new ComputerDTO(computerName, computerId, MapperDate.formatDate(introduced), MapperDate.formatDate(discontinued), companyName, companyId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
