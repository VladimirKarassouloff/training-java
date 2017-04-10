package applicationcli;

import model.Company;
import persistence.CompanyDAO;

public class DetailCompany extends Page {

	private Company company;
	
	public DetailCompany(Application app, Company company) {
		super(app);
		this.company = company;
	}

	@Override
	public void printPageInfos() {
		System.out.println("---- Detail Entreprise ----");
		System.out.println("1 : Modifier le nom de l'entreprise");
		System.out.println("exit : revenir");
	}

	@Override
	public void computeCommand(String command) {
		
		if(command.equals("1")) {
			System.out.println("Entrez le nouveau nom");
			company.setName(input.next());
			CompanyDAO.update(company);
		} else if(command.equals("exit")) {
			app.popPage();
		} else {
			System.out.println("Non reconnu");
		}
		
	}

	
	
	
}
