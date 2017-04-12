package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import model.Company;

public class CompanyDAO {

	 private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	
	
	private static String TABLE_NAME = "company";
	private static String COL_COMPANY_ID = "id";
	private static String COL_COMPANY_NAME = "name";

	public static List<Company> getAll() {
		List<Company> list = new ArrayList<Company>();

		try {
			String selectSQL = "SELECT * FROM " + CompanyDAO.TABLE_NAME;
			Connector c = Connector.getInstance();
			Connection connec = (Connection) c.getDBConnection();
			PreparedStatement preparedStatement = (PreparedStatement) connec.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery(selectSQL);
			while (rs.next()) {
				list.add(mapResultSetToObject(rs));
			}
			logger.info("Succes getAll CompanyDAO");
			rs.close();
			connec.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("Error getAll CompanyDAO : "+e.getMessage());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("Error getAll CompanyDAO : "+e.getMessage());

		}
		return list;

	}

	public static Company getById(int id) {
		Company obj = null;
		try {
			String selectSQL = "SELECT * FROM " + CompanyDAO.TABLE_NAME + " WHERE " + CompanyDAO.COL_COMPANY_ID + "=?";
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
			if(obj != null) logger.info("Succes getById CompanyDAO => id = "+id);
			else logger.info("Succes getById CompanyDAO => null value => id = "+id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("Error getById CompanyDAO : "+e.getMessage()+" => id = "+id);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("Error getbyId 2 CompanyDAO : "+e.getMessage()+" => id = "+id);
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

			String sqlUpdate = "UPDATE " + TABLE_NAME + " SET " + CompanyDAO.COL_COMPANY_NAME + "='" + company.getName()
					+ "' " + "WHERE " + CompanyDAO.COL_COMPANY_ID + "=" + company.getId();
			Connector c = Connector.getInstance();
			Connection connec = c.getDBConnection();
			PreparedStatement statement = (PreparedStatement) connec.prepareStatement(sqlUpdate);

			int resExec = statement.executeUpdate();
			statement.close();
			connec.close();
			logger.info("Succes Update CompanyDAO "+ company);
			return resExec != 0;
		} catch (Exception e) {
			logger.info("Error Update CompanyDAO : "+e.getMessage()+" "+company);
		}
		return false;
	}

	public static Integer getCount() {
		try {
			String sqlCount = "SELECT Count(*) FROM " + CompanyDAO.TABLE_NAME;
			Connector c = Connector.getInstance();
			Connection connec = c.getDBConnection();
			PreparedStatement statement = (PreparedStatement) connec.prepareStatement(sqlCount);
			ResultSet rs = statement.executeQuery();
			Integer count = null;
			if (rs.next()) {
				count = rs.getInt(1);
				System.out.println();
			}
			rs.close();
			connec.close();
			logger.info("Succes getCOunt CompanyDAO ");
			return count;
		} catch (Exception e) {
			// System.out.println("Lo l"+e.getMessage());
			logger.info("Error getCount CompanyDAO : "+e.getMessage());
		}
		return null;
	}

	public static List<Company> getPagination(int page, int numberOfResults) {
		List<Company> list = new ArrayList<Company>();

		try {
			String selectSQL = "SELECT * FROM " + CompanyDAO.TABLE_NAME + " LIMIT " + numberOfResults + " OFFSET "
					+ (page * numberOfResults);
			Connector c = Connector.getInstance();
			Connection connec = (Connection) c.getDBConnection();
			PreparedStatement preparedStatement = (PreparedStatement) connec.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery(selectSQL);
			while (rs.next()) {
				list.add(mapResultSetToObject(rs));
			}
			logger.info("Succes getPagination CompanyDAO ");

			rs.close();
			connec.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("Error getPagination CompanyDAO : "+e.getMessage());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("Error 2 getPagination CompanyDAO : "+e.getMessage());
		}
		return list;
	}

}
