package applicationcli.pages;

import java.util.List;
import java.util.stream.Collectors;

import applicationcli.Application;
import model.Company;
import model.Computer;
import services.CommonServices;

public class ListCompaniesPageForm extends ListCompaniesPage {

	private Computer form;
	
	public ListCompaniesPageForm(Application app, Computer comp) {
		super(app);
		this.form = comp;
	}

	@Override
	public void printHeader() {
		System.out.println("---- Chosir la nouvelle entreprise rattachée ----");
		System.out.println("{id} : Selectionner l'entreprise");
		System.out.println();
	}
	
	
	
	@Override
	public void selected(int id) {
		List<Company> filterId = list.stream().filter(e -> e.getId() == id).collect(Collectors.toList());
		
		if (filterId.size() > 0) {
			
			form.setCompany(filterId.get(0));
			this.app.popPage();
			
		} else {
			
			Company tmp = CommonServices.getCompany(id);
			
			if(tmp != null) {
				this.app.popPage();
				form.setCompany(tmp);
			} else {
				System.out.println("Entreprise introuvable");
			}
			
		}
	}
	
	
}
