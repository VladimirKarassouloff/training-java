package applicationcli;

import java.util.ArrayList;
import java.util.List;

import model.Company;
import persistence.CompanyDAO;

public class ListCompaniesPage extends Page {


	protected List<Company> list;
	
	public ListCompaniesPage(Application app) {
		super(app);
		list = CompanyDAO.getAll();
	}

	@Override
	public void printPageInfos() {
		System.out.println("---- Liste des entreprises ----");
		System.out.println("{id} : voir le détail par id");
		System.out.println("exit : revenir");
		for(Company comp : list) {
			System.out.println(comp.getId()+"\t"+comp.getName());
		}
	}

	@Override
	public void computeCommand(String command) {
		
		try{
			int id = Integer.parseInt(command);
			System.out.println("TODO LA PAGE DETAIL");

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
