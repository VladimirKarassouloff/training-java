package applicationcli;

import java.util.List;

public abstract class Pageable extends Page {

	protected int currentPage;
	protected int numberItemPage;
	protected int countItemTotal;

	public Pageable(Application app, int numberItemPage) {
		super(app);
		this.currentPage = 0;
		this.numberItemPage = numberItemPage;
		this.countItemTotal = orderFetchDataCountPageable();
		orderFetchNewDataForPage();
	}

	protected boolean checkPageIsCorrect() {
		// Verification que l'on se trouve a une page correcte
		if (currentPage > getLastPage()) {
			currentPage = getLastPage();
			return false;
		} else if (currentPage < 0) {
			currentPage = 0;
			return false;
		}
		return true;
	}

	@Override
	public final void printPageInfos() {
		this.checkPageIsCorrect();
		this.printHeader();
		for (int i = currentPage * numberItemPage; i < countItemTotal
				&& i < currentPage * numberItemPage + numberItemPage; i++) {
			this.printLine(i + 1, i - (currentPage * numberItemPage));
		}
	}

	protected int getLastPage() {
		double d = ((double)countItemTotal / (double)numberItemPage);
		/*System.out.println(d);
		System.out.println("Et on retourne "+ ((int)d));*/
		if( d%1 != 0) return (int)d;
		else return (int)d-1;
		//return (int)d;
	}
	
	protected abstract int orderFetchDataCountPageable();

	protected abstract void orderFetchNewDataForPage();

	public abstract void printHeader();

	public abstract void printLine(int trueLine, int i);

	@Override
	public final void computeCommand(String command) {

		if (command.equals("n")) {

			if (getLastPage() > currentPage) {
				this.currentPage++;
				orderFetchNewDataForPage();
			} else {
				System.out.println("Pas de page suivante");
			}

		} else if (command.equals("p")) {

			if (currentPage > 0) {
				this.currentPage--;
				orderFetchNewDataForPage();
			} else {
				System.out.println("Pas de page précédente");
			}
		} else if (command.equals("last")) {
			this.currentPage = getLastPage();
			orderFetchNewDataForPage();
		} else if (command.equals("first")) {
			this.currentPage = 0;
			orderFetchNewDataForPage();
		} else if (command.equals("exit")) {
			app.popPage();
		} else {
			// On test si l'utilisateur essaye d'acceder au detail de la liste
			try {
				showDetailFor(Integer.parseInt(command));
			} catch (Exception e) {
				otherCommands(command);
			}
		}

	}
	
	public abstract void showDetailFor(int id);

	public abstract void otherCommands(String command);

}
