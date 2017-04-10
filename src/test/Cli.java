package test;

import persistence.CompanyDAO;
import persistence.Connector;

public class Cli {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			Class.forName(Connector.JDBC_DRIVER);
		
			System.out.println("Test get connection");
			Connector c = Connector.getInstance();
			System.out.println(c);
			System.out.println(CompanyDAO.getAll());
			System.out.println("Ok");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
