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
		if(this.pages.size() > 0) {
			this.pages.peek().onFirstGroundEvent();
		}
	}
	
	public void pushPage(Page p) {
		this.pages.push(p);
	}
	
	public void run() {
		
        Scanner input = new Scanner(System.in);
        String command = null;
        
        do
		 {
			if(command != null) pages.peek().computeCommand(command);
			if(pages.size() > 0) pages.peek().printPageInfos();
			
		} while(this.pages.size() > 0 && (command = input.next()) != null);
		
	}
	
	
}
