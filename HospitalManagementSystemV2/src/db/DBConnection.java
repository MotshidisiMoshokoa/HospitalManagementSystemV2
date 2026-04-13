package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/hospital_v2";
    private static final String USER = "root";
    private static final String PASSWORD = "TendaZ200";

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (Exception e) {
            System.out.println("DB CONNECTION ERROR: " + e.getMessage());
            return null;
        }
    }
}