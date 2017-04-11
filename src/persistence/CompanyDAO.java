package persistence;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import model.Company;

public class CompanyDAO {

	private static String TABLE_NAME = "company";
	private static String COL_COMPANY_ID = "id";
	private static String COL_COMPANY_NAME = "name";
	
	public static List<Company> getAll() {
		List<Company> list = new ArrayList<Company>();
		
		try {
			String selectSQL = "SELECT * FROM "+CompanyDAO.TABLE_NAME;
			Connector c = Connector.getInstance();
			Connection connec = (Connection) c.getDBConnection();
			PreparedStatement preparedStatement = (PreparedStatement) connec.prepareStatement(selectSQL);
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
	
	public static Company getById(int id) {
		Company obj = null;
		try {
			String selectSQL = "SELECT * FROM "+CompanyDAO.TABLE_NAME+" WHERE "
					+CompanyDAO.COL_COMPANY_ID+"=?";
			// System.out.println(selectSQL);
			Connector c = Connector.getInstance();
			Connection connec = (Connection) c.getDBConnection();
			PreparedStatement preparedStatement = (PreparedStatement) connec.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				obj = mapResultSetToObject(rs);
			}
			rs.close();
			connec.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	
	
	
	public static Company mapResultSetToObject(ResultSet rs) throws SQLException {
		int companyId = rs.getInt(CompanyDAO.COL_COMPANY_ID);
		String companyName = rs.getString(CompanyDAO.COL_COMPANY_NAME);
		return new Company(companyId, companyName);
	}

	public static boolean update(Company company) {
		try {
				
			String sqlUpdate = "UPDATE "+TABLE_NAME+" SET "+CompanyDAO.COL_COMPANY_NAME+"='"+company.getName()+"' "
					+ "WHERE "+CompanyDAO.COL_COMPANY_ID+"="+company.getId();
			Connector c = Connector.getInstance();
			Connection connec = (Connection) c.getDBConnection();
			PreparedStatement statement = (PreparedStatement) connec.prepareStatement(sqlUpdate);
			
			return statement.executeUpdate() != 0;
		} catch(Exception e) {
			System.out.println("Exce : "+ e.getMessage());
		}
		return false;
	}
	
	
}
