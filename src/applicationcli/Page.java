package applicationcli;

import java.util.Scanner;

public abstract class Page {

	
	protected Application app;
	protected Scanner input;

	
	
	
	public Page(Application app) {
		super();
		this.app = app;
		this.input = new Scanner(System.in);
	}


	public abstract void printPageInfos();
	public abstract void computeCommand(String command);

	
	
	
	
}
