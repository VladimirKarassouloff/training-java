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
	public static String COL_JOINED_COMPANY_NAME = "company_name";


	////////////////
	/////Query parts
	public static String SELECT = "SELECT "+TABLE_NAME+"."+COL_COMPUTER_ID+", "+TABLE_NAME+"."+COL_COMPUTER_NAME+", "+COL_COMPUTER_INTRODUCED+", "+COL_COMPUTERDISCONTINUED+
			", "+COL_COMPUTER_COMPANY_ID+", "+CompanyDAO.TABLE_NAME+"."+CompanyDAO.COL_COMPANY_NAME+" as "+COL_JOINED_COMPANY_NAME+" FROM "+ ComputerDAO.TABLE_NAME +
			" LEFT JOIN "+CompanyDAO.TABLE_NAME+" ON "+CompanyDAO.TABLE_NAME+"."+CompanyDAO.COL_COMPANY_ID+
			"="+ComputerDAO.TABLE_NAME+"."+ComputerDAO.COL_COMPUTER_COMPANY_ID;

	public static String COUNT = "SELECT Count(*) FROM "+ ComputerDAO.TABLE_NAME;

	public static String WHERE_FILTER_ID = " WHERE " + ComputerDAO.TABLE_NAME + "."+ComputerDAO.COL_COMPUTER_ID+"=?";

	public static String DELETE = "DELETE FROM "+ComputerDAO.TABLE_NAME+" WHERE "+ComputerDAO.TABLE_NAME+"."+ComputerDAO.COL_COMPUTER_ID+"=?";

	///////////////////
	///////////////////



	// Comme on construit l'object company pour chaque computer, on évite d'aller en base récuperer une Company pour chaque Computer et on fait
	// pointer les objets computer sur les meme objets company
	public static HashMap<Integer, Company> cacheCompany = new HashMap<>();
	
	public static ResultSet getAll() {
		cacheCompany.clear();
		try {
			PreparedStatement preparedStatement = Connector.getInstance().preparedStatement(SELECT);
			ResultSet rs = preparedStatement.executeQuery();
			logger.info("Succes getAll computerdao");
			cacheCompany.clear();
			return rs;
		} catch (SQLException e) {
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
			PreparedStatement preparedStatement = Connector.getInstance().preparedStatement(SELECT + WHERE_FILTER_ID);
			System.out.println(SELECT + WHERE_FILTER_ID);
			preparedStatement.setInt(1,id);
			obj = preparedStatement.executeQuery();
			logger.info("Succes getbyid computerdao");
		} catch (SQLException e) {
        	logger.info("Erreur sql get by id : "+ e.getMessage());
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

			PreparedStatement statement = Connector.getInstance().preparedStatement(insertSQL,Statement.RETURN_GENERATED_KEYS);
			
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
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("Erreur 3 insert computerdao : "+ computer+" => "+e.getMessage());
		}
    	
		cacheCompany.clear();
		return -1;
	}

	public static boolean deleteById(int id) {

		try {
			PreparedStatement statement = Connector.getInstance().preparedStatement(DELETE);
			statement.setInt(1, id);
			int resultExec = statement.executeUpdate();
			statement.close();
			logger.info("Succes delete "+id+" computerdao");
			return resultExec != 0;
		} catch (SQLException e) {
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
			System.out.println(SELECT+" LIMIT " + numberOfResults + " OFFSET " + (page * numberOfResults));
			PreparedStatement preparedStatement = Connector.getInstance().preparedStatement(SELECT+" LIMIT " + numberOfResults + " OFFSET " + (page * numberOfResults));
			ResultSet rs = preparedStatement.executeQuery();
			logger.info("Succes pagination Computerdao");
			cacheCompany.clear();
			return rs;
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("Error Pagination Computerdao : "+e.getMessage());
		}

		cacheCompany.clear();
		return null;
	}

	/***
	 *
	 * @param searchByName nullable parameter to research computer by name
	 * @return count of computer considering filters
	 */
	public static Integer getCount(String searchByName) {
		try {
			ResultSet rs = Connector.getInstance().preparedStatement(COUNT).executeQuery();
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
