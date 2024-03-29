package controllers;

import java.sql.*;

/**
 * Database connection to SQLServer database.
 **/
public class DBConnection {
    // Database URL.
    static String connectionUrl = "jdbc:sqlserver://localhost;databaseName=CodeCademyDB;integratedSecurity=true;";
    private static Connection con;

    // throws SQLException (TO BE IMPLEMENTED)
    public static Connection connect() {
        // Create connection
        try {
            con = DriverManager.getConnection(connectionUrl);
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }
}