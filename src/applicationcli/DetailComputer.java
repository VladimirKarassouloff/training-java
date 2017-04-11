package applicationcli;

import java.util.Date;

import model.Computer;
import persistence.CompanyDAO;
import persistence.ComputerDAO;
import utils.Format;

public class DetailComputer extends Page {

	
	private Computer computer;
	
	public DetailComputer(Application app, Computer computer) {
		super(app);
		this.computer = computer;
	}

	@Override
	public void printPageInfos() {
		System.out.println("---- Detail ordinateur : "+computer.getName()+" ----");
		System.out.println("1 : changer le nom de l'ordinateur");
		System.out.println("2 : changer la date commercialisation");
		System.out.println("3 : changer la date d'arret");
		System.out.println("exit : quitter la page");
		System.out.println("Marque : "+(this.computer.getCompany() != null ? this.computer.getName() : "Inconnue"));
		System.out.println("Date d'introduction dans le marché : "+Format.formatDate(computer.getIntroduced()));
		System.out.println("Date d'arret de commercialisation : "+Format.formatDate(computer.getDiscontinued()));
	}

	@Override
	public void computeCommand(String command) {
		if(command.equals("1")) {
			System.out.println("Entrez le nouveau nom");
			computer.setName(input.nextLine());
			ComputerDAO.update(computer);
		}
		else if(command.equals("2")) {
			System.out.println("Entrez la nouvelle date");
			Date newDate = Format.dateFromString(input.nextLine());
			computer.setIntroduced(newDate);
			ComputerDAO.update(computer);
		} else if(command.equals("3")) {
			Date newDate = Format.dateFromString(input.nextLine());
			computer.setIntroduced(newDate);
			ComputerDAO.update(computer);
		}
		else if(command.equals("exit")) {
			app.popPage();
		} else {
			System.out.println("Non reconnu");
		}
		
	}

}
