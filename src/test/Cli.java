package test;

import java.util.Date;

import applicationcli.Application;
import model.Computer;
import persistence.CompanyDAO;
import persistence.ComputerDAO;
import persistence.Connector;

public class Cli {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		try {
			Class.forName(Connector.JDBC_DRIVER);
			Application app = new Application();
			app.run();
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
