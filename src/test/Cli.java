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
		
			/*
			System.out.println("Test get connection");
			Connector c = Connector.getInstance();
			System.out.println(c);
			
			
			System.out.println("Get All companies");
			System.out.println(CompanyDAO.getAll());
			
			System.out.println();
			System.out.println();
			
			System.out.println("Get company by id = 1");
			System.out.println(CompanyDAO.getById(1));

			System.out.println();
			System.out.println();
			
			System.out.println("Get All computers");
			System.out.println(ComputerDAO.getAll());

			System.out.println();
			System.out.println();
			

			System.out.println("Get computer by id = 20");
			System.out.println(ComputerDAO.getById(20));
			
			
			System.out.println("Insert Computer");
			Computer comp = new Computer(CompanyDAO.getById(2),"mdr 2.0", new Date(),new Date());
			System.out.println(ComputerDAO.insert(comp));
			
			
			System.out.println();
			System.out.println("L'id generee est "+comp.getId());
			System.out.println("Delete compute by id = "+comp.getId());
			System.out.println("Succes ? : "+ComputerDAO.deleteById(comp.getId()));
			
			System.out.println();
			System.out.println("Delete d'une fake id");
			System.out.println("Succes ? : "+ComputerDAO.deleteById(20202));


			System.out.println("Get computer by id = "+comp.getId());
			System.out.println(ComputerDAO.getById(comp.getId()));
			*/
			
			Application app = new Application();
			app.run();
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
