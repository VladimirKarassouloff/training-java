package applicationcli;

import java.util.Date;
import java.util.List;

import model.Company;
import model.Computer;
import services.CommonServices;

public class ListComputerPage extends Pageable {

	protected List<Computer> list;

	public ListComputerPage(Application app, List<Computer> list) {
		super(app,8);
		this.list = list;
	}

	

	@Override
	public void printHeader() {
		System.out.println("---- Liste des ordinateurs ----");
		System.out.println("{numero_de_ligne} : voir le détail par numero de ligne");
		System.out.println("c : creer un ordinateur");
		System.out.println("d : supprimer un ordinateur");
		System.out.println("exit : revenir");
		
	}

	@Override
	public void printLine(int i) {
		System.out.println((i + 1) + "\t" + list.get(i).getName());
	}
	
	
	

	public void handleDeletion() {
		System.out.println("Entrez le numero de ligne de l'ordinateur a supprimer");
		try {
			int lineDelete = Integer.parseInt(input.nextLine()) - 1;
			Computer comp = list.get(lineDelete);
			if (comp == null)
				throw new Exception("Ligne invalide");
			else if (CommonServices.deleteComputer(comp)) {
				System.out.println("Suppression de la liste : " + this.list.remove(comp));
				System.out.println("Suppression reussie");
			} else {
				throw new Exception("impossible de supprimer cet element");
			}
		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}

	public void handleCreation() {
		
		try {
			// Vars
			String nameInput;
			Integer lineChoosen;
			Date start, end;
			
			// Form
			// Nom
			System.out.println("Entrez le nom");
			nameInput = input.nextLine();
			// Date commercial
			System.out.println("Entrez la date de commercialisation");
			start = reclaimDateOrNullInput();
			// Date fin commercial
			System.out.println("Entrez la date de fin de commercialisation");
			end = reclaimDateOrNullInput();
			// Entreprise 
			System.out.println("Choississez l'entreprise créatrice de l'ordinateur");
			List<Company> companies = CommonServices.getCompanies();
			Company company = null;
			for (int i = 0; i < companies.size(); i++) {
				System.out.println((i + 1) + "\t" + companies.get(i).getName());
			}
			
			try {
				lineChoosen = reclaimIntOrNullInputBetweenRange(1, companies.size());
				if (lineChoosen != null) {
					company = companies.get(lineChoosen - 1);
				}
			} catch (Exception e) {

			}
			
			// Insert DB
			Computer newComputer = new Computer(company, nameInput, start, end);
			CommonServices.addComputer(newComputer);
			list.add(newComputer);
			
		} catch (Exception e) {
			System.out.println("Erreur lors de la creation d'un nouvel ordinateur");
		}
	}

	@Override
	protected int delegateDataSourceSizePageable() {
		return this.list.size();
	}

	@Override
	public void otherCommands(String command) {
		try {
			// Dans le choix on commence l'index a 1 et non a 0
			int id = Integer.parseInt(command) - 1;
			this.app.pushPage(new DetailComputer(app, list.get(id), CommonServices.getCompanies()));
			return;
		} catch (Exception e) {
			// System.out.println("Erreur parsing du int");
		}

		if (command.equals("c")) {
			handleCreation();
		} else if (command.equals("d")) {
			handleDeletion();
		} else if (command.equals("exit")) {
			app.popPage();
		} else {
			System.out.println("Non reconnu");
		}
		
	}

	

}
