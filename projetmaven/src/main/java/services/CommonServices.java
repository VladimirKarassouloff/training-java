package services;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.Company;
import model.Computer;
import persistence.CompanyDAO;
import persistence.ComputerDAO;
import utils.Formulaire;

public class CommonServices {


	///// COMPANY
	
	
	public static List<Company> getCompanies() {
		return CompanyDAO.getAll();
	}
	
	public static List<Company> getPagedCompany(int page, int numberItem){
		return CompanyDAO.getPagination(page, numberItem);
	}
	
	public static int getCountCompany() {
		return CompanyDAO.getCount();
	}

	public static Company getCompany(int id) {
		return CompanyDAO.getById(id);
	}

	public static boolean updateCompany(Company company) {
		return CompanyDAO.update(company);
	}
	

	
	
	
	
	////COMPUTER
	

	public static List<Computer> getComputers() {
		return ComputerDAO.getAll();
	}
	
	public static List<Computer> getPagedComputer(int page, int numberItem){
		return ComputerDAO.getPagination(page, numberItem);
	}

	public static int getCountComputer() {
		return ComputerDAO.getCount();
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

	public static boolean deleteComputer(Computer comp) {
		return ComputerDAO.deleteById(comp.getId());
	}

	
}
