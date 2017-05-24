package cdb.mapper;

import cdb.dto.ComputerDTO;
import cdb.utils.SqlNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MapperComputerDTO {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapperComputerDTO.class);


    /**
     * Map resultset to computer.
     *
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
            LOGGER.info("MapperComputerDTO : error while mapping from resultset : " + e.getMessage());
        }
        return null;
    }

    /**
     * Map resultset to computers.
     *
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
            LOGGER.info("MapperComputerDTO : error while mapping from resultset : " + e.getMessage());
        }
        return list;
    }

    /**
     * Mapp Result set to computer without closing the resultset.
     *
     * @param rs result set
     * @return computer
     */
    public static ComputerDTO mapResultSetToObjectAux(ResultSet rs) {
        try {
            long computerId = rs.getLong(SqlNames.COMPUTER_COL_COMPUTER_ID);
            long companyId = rs.getLong(SqlNames.COMPUTER_COL_COMPUTER_COMPANY_ID);
            String computerName = rs.getString(SqlNames.COMPUTER_COL_COMPUTER_NAME);
            Date introduced = rs.getDate(SqlNames.COMPUTER_COL_COMPUTER_INTRODUCED);
            Date discontinued = rs.getDate(SqlNames.COMPUTER_COL_COMPUTERDISCONTINUED);
            String companyName = rs.getString(SqlNames.COMPUTER_COL_JOINED_COMPANY_NAME);
            return new ComputerDTO(computerName, computerId, MapperDate.formatDate(introduced), MapperDate.formatDate(discontinued), companyName, companyId);
        } catch (SQLException e) {
            LOGGER.info("MapperComputerDTO : error while mapping from resultset : " + e.getMessage());
        }
        return null;
    }


}
