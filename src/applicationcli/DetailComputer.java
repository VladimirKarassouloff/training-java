package applicationcli;

import java.util.Date;
import java.util.List;

import model.Company;
import model.Computer;
import services.CommonServices;
import utils.Format;

public class DetailComputer extends Page {

	
	private Computer computer;
	private List<Company> listCompanies;
	
	
	public DetailComputer(Application app, Computer computer, List<Company> listCompanies) {
		super(app);
		this.computer = computer;
		this.listCompanies = listCompanies;
	}

	@Override
	public void printPageInfos() {
		System.out.println("---- Detail ordinateur : "+computer.getName()+" ----");
		System.out.println("1 : changer le nom de l'ordinateur");
		System.out.println("2 : changer la date commercialisation (yyyy-MM-dd)");
		System.out.println("3 : changer la date d'arret (yyyy-MM-dd)");
		System.out.println("4 : changer la marque");
		System.out.println("exit : quitter la page");
		System.out.println();
		System.out.println("--Infos--");
		System.out.println("Marque : "+(this.computer.getCompany() != null ? this.computer.getCompany().getName() : "Inconnue"));
		System.out.println("Date d'introduction dans le marché : "+Format.formatDate(computer.getIntroduced()));
		System.out.println("Date d'arret de commercialisation : "+Format.formatDate(computer.getDiscontinued()));
	}

	@Override
	public void computeCommand(String command) {
		
		// Changement du nom de l'ordianteur
		if(command.equals("1")) {
			handleNewName();
		}
		// Changement de la date d'introduction sur le marché
		else if(command.equals("2")) {
			handleDateSwitchIntroduced();
		} 
		// Changement de la date d'arret de commercialisation
		else if(command.equals("3")) {
			handleDateSwitchDiscontinuedDate();
		} 
		// Nouvelle entreprise
		else if(command.equals("4")) {
			this.handleEnterpriseSwitchId();
		}// Sortie de la page
		else if(command.equals("exit")) {
			app.popPage();
		} 
		else {
			System.out.println("Non reconnu");
		}
		
	}
	
	
	
	
	
	private void handleNewName() {
		System.out.println("Entrez le nouveau nom");
		computer.setName(input.nextLine());
		CommonServices.updateComputer(computer);
	}

	private void handleDateSwitchIntroduced() {
		System.out.println("Entrez la nouvelle date");
		Date newDate = reclaimDateOrNullInput();
		computer.setIntroduced(newDate);
		CommonServices.updateComputer(computer);
	}

	private void handleDateSwitchDiscontinuedDate() {

		Date newDate = reclaimDateOrNullInput();
		computer.setDiscontinued(newDate);
		CommonServices.updateComputer(computer);
		
	}

	private void handleEnterpriseSwitchId() {
		System.out.println("Choisissez une marque parmis les suivantes");
		int i = 0;
		StringBuilder sb = new StringBuilder();
		while(i < listCompanies.size()) {
			sb.append((i+1)+"\t"+listCompanies.get(i).getName()+"\n");
			i++;
		}
		System.out.println(sb.toString());
		try {
			Integer newComp = reclaimIntOrNullInputBetweenRange(1, listCompanies.size());
			if(newComp == null) {
				computer.setCompany(null);
			} else {
				computer.setCompany(this.listCompanies.get(newComp - 1));
			}				
			CommonServices.updateComputer(computer);
		} catch (Exception e) {
			System.out.println("Erreur lors de l'assignation de l'entreprise");
		}
	}
	

}
