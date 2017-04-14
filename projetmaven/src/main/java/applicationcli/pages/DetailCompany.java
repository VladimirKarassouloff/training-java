package applicationcli.pages;

import applicationcli.Application;
import applicationcli.FormulaireCli;
import model.Company;
import services.CommonServices;

public class DetailCompany extends Page {

	private Company company;
	
	public DetailCompany(Application app, Company company) {
		super(app);
		this.company = company;
	}

	@Override
	public void printPageInfos() {
		System.out.println("---- Detail Entreprise '"+company.getName()+"' ----");
		System.out.println("1 : Modifier le nom de l'entreprise");
		System.out.println("exit : revenir");
	}

	@Override
	public void computeCommand(String command) {
		
		if(command.equals("1")) {
			FormulaireCli.updateCompanyName(company);
		} else if(command.equals("exit")) {
			app.popPage();
		} else {
			System.out.println("Non reconnu");
		}
		
	}

	
	
	
}