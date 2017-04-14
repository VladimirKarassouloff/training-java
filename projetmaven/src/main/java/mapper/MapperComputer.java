package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Company;
import model.Computer;
import persistence.CompanyDAO;
import persistence.ComputerDAO;

public class MapperComputer {

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
			} else if (ComputerDAO.cacheCompany.get(companyId) != null) {
				company = ComputerDAO.cacheCompany.get(companyId);
			} else {
				company = new Company(companyId,companyName);
				ComputerDAO.cacheCompany.put(companyId, company);
			}
			return new Computer(computerId, company, computerName, introduced, discontinued);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
