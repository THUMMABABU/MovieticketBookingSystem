/*package com.company.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnections {
    public Connection connection;

    public static Connection connection() {
      Connection connection=null;
        String url = "jdbc:mysql://localhost:3306/movieticketbooking";
        String userName = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
           connection= DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return  connection;
    }
}


 */

package com.company.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnections {
    private static final String URL = "jdbc:mysql://localhost:3306/movieticketbooking";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private Connection connection;

    public DbConnections() {
        try {
            // Ensure that the MySQL JDBC driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection successful!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to establish a connection with the database.");
        }
    }

    public Connection getConnection() {
        return connection;
    }
/*
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 */
}

