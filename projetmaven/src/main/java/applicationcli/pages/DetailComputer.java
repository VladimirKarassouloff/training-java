package applicationcli.pages;

import applicationcli.Application;
import applicationcli.FormulaireCli;
import model.Computer;
import services.CommonServices;
import utils.Format;

public class DetailComputer extends Page {

	
	private Computer computer;
	private boolean isEditingCompanyId;
	
	public DetailComputer(Application app, Computer computer) {
		super(app);
		this.computer = computer;
		this.isEditingCompanyId = false;
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
		FormulaireCli.updateComputerName(computer);
	}

	private void handleDateSwitchIntroduced() {
		FormulaireCli.updateComputerIntroduced(computer);
	}

	private void handleDateSwitchDiscontinuedDate() {
		FormulaireCli.updateComputerDiscontinuedDate(computer);		
	}

	private void handleEnterpriseSwitchId() {
		isEditingCompanyId = true;
		this.app.pushPage(new ListCompaniesPageForm(app, computer));
	}

	@Override
	public void onFirstGroundEvent() {
		if(isEditingCompanyId) {
			isEditingCompanyId = false;
			if(CommonServices.updateComputer(computer)) {
				System.out.println("Succes de la modification");
			} else {
				System.out.println("Erreur durant la modification");
			}
		}
	}
	
	

}
