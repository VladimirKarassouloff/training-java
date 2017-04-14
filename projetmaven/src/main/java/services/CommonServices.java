package services;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import applicationcli.FormulaireCli;
import dto.ComputerDTO;
import mapper.MapperCompany;
import mapper.MapperComputer;
import model.Company;
import model.Computer;
import persistence.CompanyDAO;
import persistence.ComputerDAO;

public class CommonServices {


	///// COMPANY
	
	
	public static List<Company> getCompanies() {
		return MapperCompany.mapResultSetToObjects(CompanyDAO.getAll());
	}
	
	public static List<Company> getPagedCompany(int page, int numberItem){
		return MapperCompany.mapResultSetToObjects(CompanyDAO.getPagination(page, numberItem));
	}
	
	public static int getCountCompany() {
		return CompanyDAO.getCount();
	}

	public static Company getCompany(int id) {
		return MapperCompany.mapResultSetToObject(CompanyDAO.getById(id));
	}

	public static boolean updateCompany(Company company) {
		return CompanyDAO.update(company);
	}
	

	
	
	
	
	////COMPUTER
	

	public static List<Computer> getComputers() {
		return MapperComputer.mapResultSetToObjects(ComputerDAO.getAll());
	}

	public static List<Computer> getPagedComputer(int page, int numberItem){
		return MapperComputer.mapResultSetToObjects(ComputerDAO.getPagination(page, numberItem));
	}

	public static List<ComputerDTO> getPagedComputerDTO(int page, int numberItem){
		//return MapperComputer.mapResultSetToObjectsDTO(ComputerDAO.getPagination(page, numberItem));
		return null;
	}

	public static int getCountComputer(String searchByName) {
		return ComputerDAO.getCount(searchByName);
	}

	public static int getCountComputer() {
		return ComputerDAO.getCount(null);
	}

	public static Computer getComputer(int id) {
		return MapperComputer.mapResultSetToObject(ComputerDAO.getById(id));
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
