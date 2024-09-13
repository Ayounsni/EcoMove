package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbFunctions {
    private static DbFunctions instance;
    private Connection conn;

    private DbFunctions() {}


    public static DbFunctions getInstance() {
        if (instance == null) {
            instance = new DbFunctions();
        }
        return instance;
    }

    public void connect_to_db(String dbname, String user, String pass) {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
//                if (conn != null) {
//                    System.out.println("Connected to database");
//                } else {
//                    System.out.println("Failed to connect to database");
//                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {

                connect_to_db("EcoMove", "postgres", "0000");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while checking the connection: " + e.getMessage());
        }
        return conn;
    }

    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection to database closed.");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while closing the connection: " + e.getMessage());
        }
    }
}
