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

	public static Computer createComputer() {
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
		// Entreprise
		System.out.println("Choississez l'entreprise créatrice de l'ordinateur");
		List<Company> companies = CommonServices.getCompanies();
		Company company = null;
		for (int i = 0; i < companies.size(); i++) {
			System.out.println((i + 1) + "\t" + companies.get(i).getName());
		}

		try {
			lineChoosen = Formulaire.reclaimIntOrNullInputBetweenRange(1, companies.size());
			if (lineChoosen != null) {
				company = companies.get(lineChoosen - 1);
			}
		} catch (Exception e) {

		}

		// Insert DB
		Computer newComputer = new Computer(company, nameInput, start, end);
		int newIdCreated = CommonServices.addComputer(newComputer);
		
		return ComputerDAO.getById(newIdCreated);
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

	public static void updateComputerEnterpriseId(Computer computer) {

		System.out.println("Choisissez une marque parmis les suivantes");
		List<Company> listCompanies = CompanyDAO.getAll();
		int i = 0;
		StringBuilder sb = new StringBuilder();
		while(i < listCompanies.size()) {
			sb.append((i+1)+"\t"+listCompanies.get(i).getName()+"\n");
			i++;
		}
		System.out.println(sb.toString());
		try {
			Integer newComp = Formulaire.reclaimIntOrNullInputBetweenRange(1, listCompanies.size());
			if(newComp == null) {
				computer.setCompany(null);
			} else {
				computer.setCompany(listCompanies.get(newComp - 1));
			}				
			CommonServices.updateComputer(computer);
		} catch (Exception e) {
			System.out.println("Erreur lors de l'assignation de l'entreprise");
		}
		
	}
	
	public static void updateCompanyName(Company company) {
		System.out.println("Entrez le nouveau nom");
		company.setName(input.nextLine());
		CommonServices.updateCompany(company);
	}

	public static void updateComputerName(Computer computer) {
		System.out.println("Entrez le nouveau nom");
		computer.setName(input.nextLine());
		CommonServices.updateComputer(computer);
	}

}
