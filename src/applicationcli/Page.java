package applicationcli;

import java.util.Date;
import java.util.Scanner;

import utils.Format;

public abstract class Page {

	
	protected Application app;
	protected Scanner input;

	
	
	
	public Page(Application app) {
		super();
		this.app = app;
		this.input = new Scanner(System.in);
	}


	public abstract void printPageInfos();
	public abstract void computeCommand(String command);

	
	
	public Date reclaimDateOrNullInput() {
		while(true) {
			String inputUser = input.nextLine();
			if(inputUser.toLowerCase().equals("null")) {
				return null;
			} else {
				try {
					Date d = Format.dateFromString(inputUser);
					if(d.getYear() < 71 || d.getYear() > 100) throw new Exception("Annee invalide");
					else if(d != null) return d;
				} catch(Exception e){
					
				}
			}
			
		}
	}
	
	
	public Integer reclaimIntInputBetweenRange(int startRange, int endRange) {
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
	
	public Integer reclaimIntOrNullInputBetweenRange(int startRange, int endRange) {
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
	
	public Integer reclaimIntOrNullInput() {
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
	
}
