package applicationcli;

import java.util.List;

public abstract class Pageable extends Page {

	
	protected int currentPage;
	protected int numberItemPage;
	
	
	
	public Pageable(Application app, int numberItemPage) {
		super(app);
		this.currentPage = 0;
		this.numberItemPage = numberItemPage;
	}


	
	
	@Override
	public final void printPageInfos() {
		// Verification que l'on se trouve a une page correcte
		if(delegateDataSourceSizePageable() < currentPage * numberItemPage) {
			currentPage = delegateDataSourceSizePageable() / numberItemPage;
		} else if(currentPage < 0) {
			currentPage = 0;
		}
		
		this.printHeader();
		for(int i = currentPage * numberItemPage ; i < delegateDataSourceSizePageable() && i < currentPage*numberItemPage+numberItemPage ; i++) {
			this.printLine(i);
		}
	}

	protected abstract int delegateDataSourceSizePageable();

	public abstract void printHeader();
	public abstract void printLine(int i);
	
	@Override
	public final void computeCommand(String command) {
		if(command.equals("n")) {
			this.currentPage++;
		} else if(command.equals("p")) {
			this.currentPage--;
		}else if(command.equals("last")) {
			this.currentPage = delegateDataSourceSizePageable() / numberItemPage;
		}else if(command.equals("first")) {
			this.currentPage = 0;
		} else {
			otherCommands(command);
		}
		

	}
	
	public abstract void otherCommands(String command);

}
