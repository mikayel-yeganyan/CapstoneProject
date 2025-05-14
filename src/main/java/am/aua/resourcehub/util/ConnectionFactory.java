package am.aua.resourcehub.util;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionFactory {

    private static ConnectionFactory instance;
    private final BasicDataSource dataSource;

    private static final String url = "jdbc:mysql://localhost:3306/otters";
    private static final String user = "root";
    private static final String password = "Arevik2001";
    private static final String driverClass = "com.mysql.cj.jdbc.Driver";

    // private constructor
    private ConnectionFactory()
    {
        dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
        dataSource.setDriverClassName(driverClass);
    }

    public static synchronized ConnectionFactory getInstance()
    {
        if(instance == null){
            instance = new ConnectionFactory();
        }

        return instance;
    }

    public Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }

}