package persistence;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public final class Connector {

	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost/computer-database-db?useSSL=false";

	private static final String USER = "admincdb";
	private static final String PASS = "qwerty1234";

	private static Connector connector = null;
	private Connection connection = null;
	private Connector() {
		try {
			Class.forName(Connector.JDBC_DRIVER);
			connection = (Connection) DriverManager.getConnection(Connector.DB_URL, Connector.USER, Connector.PASS);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connector getInstance() {
		if (connector == null) connector = new Connector();
		return connector;
	}
	

	public Connection getDBConnection() throws ClassNotFoundException, SQLException {
		return connection;
	}



}
