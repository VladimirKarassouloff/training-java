package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Company;

public class CompanyDAO {

	public static String TABLE_NAME = "company";
	public static String COL_COMPANY_ID = "id";
	public static String COL_COMPANY_NAME = "name";
	
	public static List<Company> getAll() {
		List<Company> list = new ArrayList<Company>();
		
		try {
			String selectSQL = "SELECT * FROM "+CompanyDAO.TABLE_NAME;
			Connector c = Connector.getInstance();
			Connection connec = c.getDBConnection();
			PreparedStatement preparedStatement = connec.prepareStatement(selectSQL);
			// preparedStatement.setInt(1, 1001);
			ResultSet rs = preparedStatement.executeQuery(selectSQL);
			while (rs.next()) {
				list.add(mapResultSetToObject(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	
	
	public static Company mapResultSetToObject(ResultSet rs) throws SQLException {
		int companyId = rs.getInt(CompanyDAO.COL_COMPANY_ID);
		String companyName = rs.getString(CompanyDAO.COL_COMPANY_NAME);
		return new Company(companyId, companyName);
	}
	
	
}
