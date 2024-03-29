package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class employeeController {

    public static class Employee {
        private final SimpleStringProperty employeeName;

        public Employee(String employeeName) {
            this.employeeName = new SimpleStringProperty(employeeName);

        }

        public String getEmployeeName() {
            return this.employeeName.get();
        }

        @Override
        public String toString() {
            return this.employeeName.get();
        }

    }

    public static ArrayList<Employee> getAllEmployees() {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;
        // ArrayList of all Modules
        ArrayList<Employee> employeeList = new ArrayList<>();

        try {
            //Checking database info with query 
            con = DBConnection.connect();

            String query = "SELECT * FROM Employee";

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getString("EmployeeName"));

                employeeList.add(employee);
            }

            return employeeList;

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmt, con);
        }
        return employeeList;
    }

}