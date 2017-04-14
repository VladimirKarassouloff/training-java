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
		Computer c = mapResultSetToObjectAux(rs);
		try {
			rs.close();
			return c;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static List<Computer> mapResultSetToObjects(ResultSet rs) {
		List<Computer> list = new ArrayList<Computer>();
		try {
			while (rs.next()) {
				list.add(mapResultSetToObjectAux(rs));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	private static Computer mapResultSetToObjectAux(ResultSet rs) {
		try {
			int computerId = rs.getInt(ComputerDAO.COL_COMPUTER_ID);
			int companyId = rs.getInt(ComputerDAO.COL_COMPUTER_COMPANY_ID);
			String computerName = rs.getString(ComputerDAO.COL_COMPUTER_NAME);
			Date introduced = rs.getDate(ComputerDAO.COL_COMPUTER_INTRODUCED);
			Date discontinued = rs.getDate(ComputerDAO.COL_COMPUTERDISCONTINUED);

			Company company;
			if (companyId == 0) {
				company = null;
			} else if (ComputerDAO.cacheCompany.get(companyId) != null) {
				company = ComputerDAO.cacheCompany.get(companyId);
			} else {
				company = MapperCompany.mapResultSetToObject(CompanyDAO.getById(companyId));
				ComputerDAO.cacheCompany.put(companyId, company);
			}
			return new Computer(computerId, company, computerName, introduced, discontinued);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
