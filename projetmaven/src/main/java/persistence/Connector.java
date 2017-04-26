package persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class Connector {

    // Config
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/computer-database-db?useSSL=false";
    private static final String USER = "admincdb";
    private static final String PASS = "qwerty1234";


    private static DataSource datasource;

    public static DataSource getDataSource() {
        return datasource;
    }

    public static Connector connector = new Connector();
    
    private Connector() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(Connector.DB_URL);
        config.setUsername(Connector.USER);
        config.setPassword(Connector.PASS);

        config.setMaximumPoolSize(20);
        config.setAutoCommit(false);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        datasource = new HikariDataSource(config);
    }


}
