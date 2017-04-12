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

	public static Scanner input = new Scanner(System.in);

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
	

	public static void updateCompanyName(Company company) {
		System.out.println("Entrez le nouveau nom");
		company.setName(input.nextLine());
		CommonServices.updateCompany(company);
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

	public static Computer precreateComputer() {
		// Vars
		String nameInput;
		Integer lineChoosen;
		Date start, end;

		// Form
		// Nom
		System.out.println("Entrez le nom");
		nameInput = input.nextLine();
		// Date commercial
		System.out.println("Entrez la date de commercialisation");
		start = Formulaire.reclaimDateOrNullInput();
		// Date fin commercial
		System.out.println("Entrez la date de fin de commercialisation");
		end = Formulaire.reclaimDateOrNullInput();

		
		return new Computer(null, nameInput, start, end);
	}

	
	
	
	
	public static boolean updateComputer(Computer computer) {
		return ComputerDAO.update(computer);
	}

	public static boolean deleteComputer(Computer comp) {
		return ComputerDAO.deleteById(comp.getId());
	}

	public static void updateComputerIntroduced(Computer computer) {
		System.out.println("Entrez la nouvelle date");
		Date newDate = Formulaire.reclaimDateOrNullInput();
		computer.setIntroduced(newDate);
		CommonServices.updateComputer(computer);
	}

	public static void updateComputerDiscontinuedDate(Computer computer) {
		System.out.println("Entrez la nouvelle date");
		Date newDate = Formulaire.reclaimDateOrNullInput();
		computer.setDiscontinued(newDate);
		CommonServices.updateComputer(computer);
	}

	


	public static void updateComputerName(Computer computer) {
		System.out.println("Entrez le nouveau nom");
		computer.setName(input.nextLine());
		CommonServices.updateComputer(computer);
	}

}
