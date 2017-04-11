package applicationcli;

import java.util.List;

import model.Company;

public class ListCompaniesPage extends Pageable {


	protected List<Company> list;
	
	public ListCompaniesPage(Application app, List<Company> list) {
		super(app,8);
		this.list = list;
	}

	
	
	@Override
	public void printHeader() {
		System.out.println("---- Liste des entreprises ----");
		System.out.println("{id} : voir le détail par id");
		System.out.println("exit : revenir");
		System.out.println();
	}

	@Override
	public void printLine(int i) {
		System.out.println((i+1)+"\t"+list.get(i).getName());
		
	}
	

	@Override
	protected int delegateDataSourceSizePageable() {
		return this.list.size();
	}

	@Override
	public void otherCommands(String command) {
		try{
			int id = Integer.parseInt(command);
			// Dans le choix on commence l'index a 1 et non a 0
			id--; 
			this.app.pushPage(new DetailCompany(app, list.get(id)));
			return;
		} catch(Exception e) {
			//System.out.println("Erreur parsing du int");
		}
		
		if(command.equals("exit")) {
			app.popPage();
		} else {
			System.out.println("Non reconnu");
		}
	}

	

}
