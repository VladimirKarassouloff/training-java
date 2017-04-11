package services;

import java.util.List;

import model.Company;
import model.Computer;
import persistence.CompanyDAO;
import persistence.ComputerDAO;

public class CommonServices {

	
	
	public static List<Company> getCompanies() {
		return CompanyDAO.getAll();
	}
	
	public static Company getCompany(int id) {
		return CompanyDAO.getById(id);
	}
	
	public static boolean updateCompany(Company company) {
		return CompanyDAO.update(company);
	}
	
	
	
	public static List<Computer> getComputers() {
		return ComputerDAO.getAll();
	}
	
	public static Computer getComputer(int id) {
		return ComputerDAO.getById(id);
	}
	
	public static int addComputer(Computer computer) {
		return ComputerDAO.insert(computer);
	}
	
	public static boolean updateComputer(Computer computer) {
		return ComputerDAO.update(computer);
	}
	
	
	
	
}
