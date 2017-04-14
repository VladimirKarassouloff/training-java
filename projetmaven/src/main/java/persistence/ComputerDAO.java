package persistence;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import mapper.MapperComputer;
import model.Company;
import model.Computer;
import utils.Format;

public class ComputerDAO {

	 private static final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	
	private static String TABLE_NAME = "computer";
	public static String COL_COMPUTER_ID = "id";
	public static String COL_COMPUTER_NAME = "name";
	public static String COL_COMPUTER_INTRODUCED = "introduced";
	public static String COL_COMPUTERDISCONTINUED = "discontinued";
	public static String COL_COMPUTER_COMPANY_ID = "company_id";

	// Comme on construit l'object company pour chaque computer, on évite d'aller en base récuperer une Company pour chaque Computer
	public static HashMap<Integer, Company> cacheCompany = new HashMap<>();
	
	public static ResultSet getAll() {
		cacheCompany.clear();
		try {

			String selectSQL = "SELECT * FROM " + ComputerDAO.TABLE_NAME;
			Connector c = Connector.getInstance();
			Connection connec = c.getDBConnection();
			PreparedStatement preparedStatement = (PreparedStatement) connec.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery(selectSQL);
			
			
			logger.info("Succes getAll computerdao");
			return rs;
		} catch (SQLException e) {

			logger.info("Erreur getAll computerdao : "+ e.getMessage());
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {

			logger.info("Erreur getAll computerdao : "+ e.getMessage());
			e.printStackTrace();
			
		}
		cacheCompany.clear();
		return null;

	}

	public static ResultSet getById(int id) {
		
		cacheCompany.clear();
		ResultSet obj = null;
		
		try {
			
			String selectSQL = "SELECT * FROM " + ComputerDAO.TABLE_NAME + " WHERE " + ComputerDAO.TABLE_NAME + "."
					+ ComputerDAO.COL_COMPUTER_ID + "=" + id;
			
			Connector c = Connector.getInstance();
			Connection connec = c.getDBConnection();
			PreparedStatement preparedStatement = (PreparedStatement) connec.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery(selectSQL);
			
			if (rs.next()) {
				obj = rs;
			}
			
			logger.info("Succes getbyid computerdao");			
		} catch (SQLException e) {
			
        	logger.info("Erreur sql get by id : "+ e.getMessage());
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			
        	logger.info("Erreur get by id computerdao : "+ e.getMessage());
			e.printStackTrace();
			
		}
		cacheCompany.clear();
		return obj;
	}


	public static int insert(Computer computer) {
		try {
			cacheCompany.clear();
			String insertSQL = "INSERT INTO " + ComputerDAO.TABLE_NAME + "(" + COL_COMPUTER_NAME + ","
					+ COL_COMPUTER_INTRODUCED + "," + COL_COMPUTERDISCONTINUED + "," + COL_COMPUTER_COMPANY_ID + ") "
					+ "VALUES ('" + computer.getName() + "',"+Format.quotedOrNull(computer.getIntroduced())+","
					+ Format.quotedOrNull(computer.getDiscontinued())+ "," + (computer.getCompany() != null ? Format.quotedOrNull(computer.getCompany().getId()) : "NULL")
					+ ")";

			Connector c = Connector.getInstance();
			Connection connec = c.getDBConnection();
			PreparedStatement statement = (PreparedStatement) connec.prepareStatement(insertSQL,Statement.RETURN_GENERATED_KEYS);
			
			if(statement.executeUpdate() != 0) {
				ResultSet generatedKeys = statement.getGeneratedKeys();
				if(generatedKeys.next()) {
					int newIdGenerated = (int)generatedKeys.getLong(1) ;
					computer.setId(newIdGenerated);
					statement.close();
	            	logger.info("Success insert computerdao : "+ computer);
	            	cacheCompany.clear();
					return newIdGenerated;
	            }
	            else {
	            	// ??
	            	logger.info("Erreur insert computerdao : "+ computer);
	            	statement.close();
					cacheCompany.clear();
	                return -1;
	            }
				
			} else {
				// Aucune ligne affectée
            	logger.info("Erreur 2 insert computerdao : "+ computer);
				statement.close();
				cacheCompany.clear();
				return -1;
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("Erreur 3 insert computerdao : "+ computer+" => "+e.getMessage());
		}
    	
		cacheCompany.clear();
		return -1;
	}

	public static boolean deleteById(int id) {
		
		String deleteSQL = "DELETE FROM "+ComputerDAO.TABLE_NAME+" WHERE "+ComputerDAO.TABLE_NAME+"."+ComputerDAO.COL_COMPUTER_ID+"="+id;
		Connector c = Connector.getInstance();
		Connection connec;
		
		try {
			connec = c.getDBConnection();
			PreparedStatement statement = (PreparedStatement) connec.prepareStatement(deleteSQL);
			int resultExec = statement.executeUpdate();
			statement.close();
			logger.info("Succes delete "+id+" computerdao");
			return resultExec != 0;
		} catch (ClassNotFoundException | SQLException e) {
			logger.info("Error delete Computerdao "+id+" : "+e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	
	public static boolean update(Computer computer) {
		try {
			
			String sqlUpdate = "UPDATE "+TABLE_NAME+" SET "+ComputerDAO.COL_COMPUTER_NAME+"='"+computer.getName()+"',"
					+ComputerDAO.COL_COMPUTER_INTRODUCED +" = "+Format.quotedOrNull(computer.getIntroduced())+", "
					+ComputerDAO.COL_COMPUTERDISCONTINUED+" = "+Format.quotedOrNull(computer.getDiscontinued())+", "
					+ComputerDAO.COL_COMPUTER_COMPANY_ID+ " = "+(computer.getCompany() == null ? "NULL" : "'"+computer.getCompany().getId()+"'") +" "
					+ "WHERE "+ComputerDAO.COL_COMPUTER_ID+"="+computer.getId();
			
			Connector c = Connector.getInstance();
			Connection connec = c.getDBConnection();
			PreparedStatement statement = (PreparedStatement) connec.prepareStatement(sqlUpdate);
			
			int resultExec = statement.executeUpdate();
			statement.close();
			logger.info("Succes Update Computerdao : "+ computer);
			return resultExec != 0;
			
		} catch(Exception e) {
			
			System.out.println("Exce : "+ e.getMessage());
			logger.info("Error Update Computerdao : "+ computer+" => "+e.getMessage());
			
		}
		return false;
	}
	
	
	public static ResultSet getPagination(int page, int numberOfResults) {
		cacheCompany.clear();

		try {
			String selectSQL = "SELECT * FROM " + ComputerDAO.TABLE_NAME + " LIMIT " + numberOfResults + " OFFSET "
					+ (page * numberOfResults);
			
			Connector c = Connector.getInstance();
			Connection connec = (Connection) c.getDBConnection();
			PreparedStatement preparedStatement = (PreparedStatement) connec.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery(selectSQL);
			
			
			logger.info("Succes pagination Computerdao");
			return rs;
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("Error Pagination Computerdao : "+e.getMessage());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.info("Error 2 Pagination Computerdao : "+e.getMessage());
		}
		
		cacheCompany.clear();
		return null;
	}
	
	public static Integer getCount() {
		try {
			
			String sqlCount = "SELECT Count(*) FROM " + ComputerDAO.TABLE_NAME;
			
			Connector c = Connector.getInstance();
			Connection connec = c.getDBConnection();
			PreparedStatement statement = (PreparedStatement) connec.prepareStatement(sqlCount);
			ResultSet rs = statement.executeQuery();
			Integer count = null;
			
			if (rs.next()) {
				count = rs.getInt(1);
			}
			
			rs.close();
			logger.info("Success Count Computerdao ");
			return count;
			
		} catch (Exception e) {
			logger.info("Error Count Computerdao : "+e.getMessage());
		}
		return null;
	}


}
