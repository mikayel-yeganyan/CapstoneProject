package am.aua;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    public static void main(String[] args) {
        // Database URL
        String url = "jdbc:mysql://localhost:3306/resource";
        String user = "root";
        String password = "admin123";

//        try {
//            // Establish connection (no need for Class.forName)
//            Connection conn = DriverManager.getConnection(url, user, password);
//
//            // If successful, print this message
//            System.out.println("Connected to MySQL database!");
//
//            // Close connection (important to avoid leaks)
//            conn.close();
//
//        } catch (SQLException e) {
//            System.err.println("Failed to connect to database.");
//            e.printStackTrace();
//        }
    }
}
