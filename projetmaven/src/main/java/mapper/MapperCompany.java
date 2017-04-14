package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Company;
import persistence.CompanyDAO;

public class MapperCompany {

	
	public static Company mapResultSetToObject(ResultSet rs)  {
		try {
			Company com = mapResultSetToObjectAux(rs);
			rs.close();
			return com;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Company> mapResultSetToObjects(ResultSet rs) {
		List<Company> list = new ArrayList<>();
		try {
			while(rs.next()) {
				list.add(mapResultSetToObjectAux(rs));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static Company mapResultSetToObjectAux(ResultSet rs)  {
		int companyId;
		try {
			companyId = rs.getInt(CompanyDAO.COL_COMPANY_ID);
			String companyName = rs.getString(CompanyDAO.COL_COMPANY_NAME);
			return new Company(companyId, companyName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
