package applicationcli;

import java.util.ArrayList;
import java.util.List;

import model.Company;
import persistence.CompanyDAO;

public class ListCompaniesPage extends Page {


	protected List<Company> list;
	
	public ListCompaniesPage(Application app, List<Company> list) {
		super(app);
		this.list = list;
	}

	@Override
	public void printPageInfos() {
		System.out.println("---- Liste des entreprises ----");
		System.out.println("{id} : voir le détail par id");
		System.out.println("exit : revenir");
		for(int i = 0 ; i < list.size() ; i++) {
			System.out.println((i+1)+"\t"+list.get(i).getName());
		}
	}

	@Override
	public void computeCommand(String command) {
		
		try{
			int id = Integer.parseInt(command);
			// Dans le choix on commence l'index a 1 et non a 0
			id--; 
			this.app.pushPage(new DetailCompany(app, list.get(id)));
		} catch(Exception e) {
			System.out.println("Erreur parsing du int");
		}
		
		if(command.equals("exit")) {
			app.popPage();
		} else {
			System.out.println("Non reconnu");
		}
		
	}

}
