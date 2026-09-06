package com.savarquiz.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/savar_quiz";

    private static final String USER = "root";

    private static final String PASSWORD = "mou123456";

    public static Connection getConnection() throws SQLException {

        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException cnfe) {
                System.out.println("=================================");
                System.out.println("✗ JDBC DRIVER NOT FOUND");
                System.out.println("Driver error: " + cnfe.getMessage());
                cnfe.printStackTrace();
                System.out.println("=================================");
                throw new SQLException("JDBC Driver not found", cnfe);
            }

            Connection connection = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("=================================");
            System.out.println("✓ Database connection successful!");
            System.out.println("Connected to: " + URL);
            System.out.println("=================================");

            return connection;

        } catch (SQLException e) {

            System.out.println("=================================");
            System.out.println("✗ DATABASE CONNECTION FAILED!");
            System.out.println("URL: " + URL);
            System.out.println("User: " + USER);
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.out.println("=================================");

            throw e;
        }
    }
}