package utils;

import java.util.Date;
import java.util.Scanner;

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
	
	
	
}
