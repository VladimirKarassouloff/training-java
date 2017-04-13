package utils;

import java.util.Date;
import java.util.Scanner;

import model.Company;
import model.Computer;
import services.CommonServices;

public class Formulaire {

	
	public static Scanner input = new Scanner(System.in);
	
	public static Date reclaimDateOrNullInput() {
		
		while(true) {
			
			String inputUser = input.nextLine();
			
			if(inputUser.toLowerCase().equals("null")) return null;
			else {
				
				try {
					Date d = Format.dateFromString(inputUser);
					if(d != null) return d;
					else System.out.println("Date invalide");
				} catch(Exception e){
					System.out.println("Date invalide");
				}
				
			}
			
		}
	}
	
	
	public static Integer reclaimIntInputBetweenRange(int startRange, int endRange) {
		
		while(true) {
			
			try{
				int inputInt = input.nextInt();
				if(inputInt >= startRange && inputInt <= endRange) {
					return inputInt;
				} else {
					System.out.println("La valeur devrait etre entre "+startRange+" et "+endRange);	
				}
				
			} catch(Exception e) {
				System.out.println();
			}
			
		}
		
	}
	
	public static Integer reclaimIntOrNullInputBetweenRange(int startRange, int endRange) {
		
		while(true) {
			
			Integer i = reclaimIntOrNullInput();
			if(i == null) {
				return null;
			}
			if(i >= startRange && i <= endRange) {
				return i;
			} else {
				System.out.println("La valeur devrait etre entre "+startRange+" et "+endRange);
			}
			
		}
	}
	
	public static Integer reclaimIntOrNullInput() {
		
		while(true) {
			
			String inputUser = input.nextLine();
			if(inputUser.toLowerCase().equals("null")) {
				return null;
			} else {
				try {
					return Integer.parseInt(inputUser);
				} catch(Exception e){
					System.out.println("Mauvais format de date");
				}
			}
			
		}
	}
	
	
	public static Computer precreateComputer() {
		// Vars
		String nameInput;
		Integer lineChoosen;
		Date start, end;

		// Form
		// Nom
		System.out.println("Entrez le nom");
		nameInput = Formulaire.input.nextLine();
		// Date commercial
		System.out.println("Entrez la date de commercialisation");
		start = Formulaire.reclaimDateOrNullInput();
		// Date fin commercial
		System.out.println("Entrez la date de fin de commercialisation");
		end = Formulaire.reclaimDateOrNullInput();

		
		return new Computer(null, nameInput, start, end);
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
		computer.setName(Formulaire.input.nextLine());
		CommonServices.updateComputer(computer);
	}
	
	public static void updateCompanyName(Company company) {
		System.out.println("Entrez le nouveau nom");
		company.setName(Formulaire.input.nextLine());
		CommonServices.updateCompany(company);
	}
	

	
}
