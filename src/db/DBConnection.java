package db;

import env.EnvLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:oracle:thin:@oracle.cs.torontomu.ca:1521:orcl"; // append service name
    private static final String USER = EnvLoader.get("UserID");
    private static final String PASSWORD = EnvLoader.get("Password");

   

    private static Connection conn;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(USER + ' '+ PASSWORD);
            System.out.println("Database connection failed. Check credentials!");
        }
        return conn;
    }
}
