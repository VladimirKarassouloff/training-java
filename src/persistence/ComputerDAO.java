package persistence;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import model.Company;
import model.Computer;
import utils.Format;

public class ComputerDAO {

	private static String TABLE_NAME = "computer";
	public static String COL_COMPUTER_ID = "id";
	public static String COL_COMPUTER_NAME = "name";
	public static String COL_COMPUTER_INTRODUCED = "introduced";
	public static String COL_COMPUTERDISCONTINUED = "discontinued";
	public static String COL_COMPUTER_COMPANY_ID = "company_id";

	// Comme on construit l'object company pour chaque computer, on évite d'aller en base récuperer 
	// une Company pour chaque Computer
	public static HashMap<Integer, Company> cacheCompany = new HashMap<>();
	
	public static List<Computer> getAll() {
		List<Computer> list = new ArrayList<Computer>();
		cacheCompany.clear();
		try {

			String selectSQL = "SELECT * FROM " + ComputerDAO.TABLE_NAME;
			Connector c = Connector.getInstance();
			Connection connec = c.getDBConnection();
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
		cacheCompany.clear();
		return list;

	}

	public static Computer getById(int id) {
		cacheCompany.clear();
		Computer obj = null;
		try {
			String selectSQL = "SELECT * FROM " + ComputerDAO.TABLE_NAME + " WHERE " + ComputerDAO.TABLE_NAME + "."
					+ ComputerDAO.COL_COMPUTER_ID + "=" + id;
			Connector c = Connector.getInstance();
			Connection connec = c.getDBConnection();
			PreparedStatement preparedStatement = (PreparedStatement) connec.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery(selectSQL);
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
		cacheCompany.clear();
		return obj;
	}


	public static int insert(Computer computer) {
		try {
			String insertSQL = "INSERT INTO " + ComputerDAO.TABLE_NAME + "(" + COL_COMPUTER_NAME + ","
					+ COL_COMPUTER_INTRODUCED + "," + COL_COMPUTERDISCONTINUED + "," + COL_COMPUTER_COMPANY_ID + ") "
					+ "VALUES ('" + computer.getName() + "','" + Format.formatDate(computer.getIntroduced()) + "','"
					+ Format.formatDate(computer.getDiscontinued()) + "','" + (computer.getCompany() != null ? computer.getCompany().getId() : "NULL")
					+ "')";
			System.out.println("Insert query : " + insertSQL);
			Connector c = Connector.getInstance();
			Connection connec = c.getDBConnection();
			PreparedStatement statement = (PreparedStatement) connec.prepareStatement(insertSQL,Statement.RETURN_GENERATED_KEYS);
			
			if(statement.executeUpdate() != 0) {
				ResultSet generatedKeys = statement.getGeneratedKeys();
				if(generatedKeys.next()) {
					int newIdGenerated = (int)generatedKeys.getLong(1) ;
					computer.setId(newIdGenerated);
					return newIdGenerated;
	            }
	            else {
	            	// ??
	                return -1;
	            }
				
			} else {
				// Aucune ligne affectée
				return -1;
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public static boolean deleteById(int id) {
		String deleteSQL = "DELETE FROM "+ComputerDAO.TABLE_NAME+" WHERE "+ComputerDAO.TABLE_NAME+"."+ComputerDAO.COL_COMPUTER_ID+"="+id;
		Connector c = Connector.getInstance();
		Connection connec;
		try {
			connec = c.getDBConnection();
			PreparedStatement statement = (PreparedStatement) connec.prepareStatement(deleteSQL);
			return statement.executeUpdate() != 0;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public static Computer mapResultSetToObject(ResultSet rs) throws SQLException {
		int computerId = rs.getInt(ComputerDAO.COL_COMPUTER_ID);
		int companyId = rs.getInt(ComputerDAO.COL_COMPUTER_COMPANY_ID);
		String computerName = rs.getString(ComputerDAO.COL_COMPUTER_NAME);
		Date introduced = rs.getDate(ComputerDAO.COL_COMPUTER_INTRODUCED);
		Date discontinued = rs.getDate(ComputerDAO.COL_COMPUTERDISCONTINUED);
		
		Company company;
		if(companyId == 0) {
			company = null;
		}
		else if(ComputerDAO.cacheCompany.get(companyId) != null) {
			company = ComputerDAO.cacheCompany.get(companyId);
			// System.out.println("Recuperation d'une Company depuis le cache");
		} else {
			company = CompanyDAO.getById(companyId);
			cacheCompany.put(companyId, company);
		}
		
		return new Computer(computerId, company, computerName, introduced, discontinued);
	}

	public static boolean update(Computer computer) {
		try {
			
			String sqlUpdate = "UPDATE "+TABLE_NAME+" SET "+ComputerDAO.COL_COMPUTER_NAME+"='"+computer.getName()+"',"
					+ComputerDAO.COL_COMPUTER_INTRODUCED +" = '" + Format.formatDate(computer.getIntroduced())+"', "
					+ComputerDAO.COL_COMPUTERDISCONTINUED+" = '" + Format.formatDate(computer.getDiscontinued())+ "', "
					+ComputerDAO.COL_COMPUTER_COMPANY_ID+ " = "+(computer.getCompany() == null ? "NULL" : "'"+computer.getCompany().getId()+"'") 
					+ "WHERE "+ComputerDAO.COL_COMPUTER_ID+"="+computer.getId();
			Connector c = Connector.getInstance();
			Connection connec = c.getDBConnection();
			PreparedStatement statement = (PreparedStatement) connec.prepareStatement(sqlUpdate);
			
			return statement.executeUpdate() != 0;
		} catch(Exception e) {
			System.out.println("Exce : "+ e.getMessage());
		}
		return false;
	}


}
