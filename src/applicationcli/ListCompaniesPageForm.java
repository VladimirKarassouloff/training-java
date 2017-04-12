package applicationcli;

import java.util.List;
import java.util.stream.Collectors;

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
			if(CommonServices.updateComputer(form)) {
				System.out.println("Modifié");
				this.app.popPage();
			} else {
				System.out.println("Impossible de faire la modification");
			}
			
		} else {
			Company tmp = CommonServices.getCompany(id);
			if(tmp != null) {
				//this.app.pushPage(new DetailCompany(app, tmp));
				form.setCompany(tmp);
				if(CommonServices.updateComputer(form)) {
					System.out.println("Modifié");
					this.app.popPage();
				} else {
					System.out.println("Impossible de faire la modification");
				}
				
			} else {
				System.out.println("Entreprise introuvable");
			}
			
		}
	}
	
	
}
