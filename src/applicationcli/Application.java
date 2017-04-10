package applicationcli;

import java.util.Scanner;
import java.util.Stack;

public class Application {

	
	public Stack<Page> pages;
	
	
	public Application() {
		pages = new Stack<Page>();
		pages.push(new MainPage(this));
	}

	
	public void popPage() {
		this.pages.pop();
	}
	
	public void pushPage(Page p) {
		this.pages.push(p);
	}
	
	public void run() {
		
        Scanner input = new Scanner(System.in);
        String command = null;
        do
		 {
			//System.out.println("Printed : "+ command);
			if(command != null) pages.peek().computeCommand(command);
			pages.peek().printPageInfos();
			//if(pages.peek() == null) break;
		} while(this.pages.size() > 0 && (command = input.next()) != null);
		
	}
	
	
}
