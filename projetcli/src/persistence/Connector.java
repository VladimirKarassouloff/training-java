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

	private Connector() {}

	public static Connector getInstance() {
		if (connector == null) connector = new Connector();
		return connector;
	}
	

	public Connection getDBConnection() throws ClassNotFoundException, SQLException {
		Class.forName(Connector.JDBC_DRIVER);
		return (Connection) DriverManager.getConnection(Connector.DB_URL, Connector.USER, Connector.PASS);
	}



}
