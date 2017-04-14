package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import mapper.MapperCompany;
import model.Company;

public class CompanyDAO {

	private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	public static String TABLE_NAME = "company";
	public static String COL_COMPANY_ID = "id";
	public static String COL_COMPANY_NAME = "name";

	public static ResultSet getAll() {

		try {

			String selectSQL = "SELECT * FROM " + CompanyDAO.TABLE_NAME;
			Connector c = Connector.getInstance();
			Connection connec = (Connection) c.getDBConnection();
			PreparedStatement preparedStatement = (PreparedStatement) connec.prepareStatement(selectSQL);		
			ResultSet rs = preparedStatement.executeQuery(selectSQL);


			logger.info("Succes getAll CompanyDAO");
			return rs;

		} catch (SQLException e) {

			e.printStackTrace();
			logger.info("Error getAll CompanyDAO : " + e.getMessage());

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			logger.info("Error getAll CompanyDAO : " + e.getMessage());

		}
		return null;

	}

	public static ResultSet getById(int id) {
		ResultSet obj = null;
		try {
			String selectSQL = "SELECT * FROM " + CompanyDAO.TABLE_NAME + " WHERE " + CompanyDAO.COL_COMPANY_ID + "= ?";
			
			Connector c = Connector.getInstance();
			Connection connec = (Connection) c.getDBConnection();
			
			PreparedStatement preparedStatement = (PreparedStatement) connec.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				obj = rs;
			}


			if (obj != null)
				logger.info("Succes getById CompanyDAO => id = " + id);
			else
				logger.info("Succes getById CompanyDAO => null value => id = " + id);

		} catch (SQLException e) {

			e.printStackTrace();
			logger.info("Error getById CompanyDAO : " + e.getMessage() + " => id = " + id);

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			logger.info("Error getbyId 2 CompanyDAO : " + e.getMessage() + " => id = " + id);

		}

		return obj;
	}

	public static boolean update(Company company) {

		try {
			String sqlUpdate = "UPDATE " + TABLE_NAME + " SET " + CompanyDAO.COL_COMPANY_NAME + "= ?  WHERE "
					+ CompanyDAO.COL_COMPANY_ID + "= ? ";
			
			Connector c = Connector.getInstance();
			Connection connec = c.getDBConnection();
			
			PreparedStatement statement = (PreparedStatement) connec.prepareStatement(sqlUpdate);
			statement.setString(1, company.getName());
			statement.setInt(2, company.getId());

			int resExec = statement.executeUpdate();
			statement.close();
			logger.info("Succes Update CompanyDAO " + company);

			return resExec != 0;
		} catch (Exception e) {
			logger.info("Error Update CompanyDAO : " + e.getMessage() + " " + company);
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
			}

			logger.info("Succes getCount CompanyDAO ");
			return count;

		} catch (Exception e) {
			logger.info("Error getCount CompanyDAO : " + e.getMessage());
		}

		return null;
	}

	public static ResultSet getPagination(int page, int numberOfResults) {
		
		try {
			String selectSQL = "SELECT * FROM " + CompanyDAO.TABLE_NAME + " LIMIT " + numberOfResults + " OFFSET "
					+ (page * numberOfResults);

			Connector c = Connector.getInstance();
			Connection connec = (Connection) c.getDBConnection();
			PreparedStatement preparedStatement = (PreparedStatement) connec.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery(selectSQL);
			
			logger.info("Succes getPagination CompanyDAO ");
			return rs;
		} catch (SQLException e) {

			e.printStackTrace();
			logger.info("Error getPagination CompanyDAO : " + e.getMessage());

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			logger.info("Error 2 getPagination CompanyDAO : " + e.getMessage());

		}

		return null;
	}

}
