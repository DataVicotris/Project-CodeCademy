package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class subjectController {

    public static class Subject {
        private final SimpleStringProperty subjectName;

        public Subject(String subjectName) {
            this.subjectName = new SimpleStringProperty(subjectName);

        }

        @Override
        public String toString() {
            return this.subjectName.get();
        }
    }

    public static ArrayList<Subject> getAllSubjects() {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;
        // ArrayList of all courses
        ArrayList<Subject> subjectList = new ArrayList<>();

        try {
            con = DBConnection.connect();

            String query = "SELECT * FROM subject";

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
               subjectList.add(new Subject(rs.getString("Subject")));
            }

            return subjectList;

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmt, con);
        }
        return subjectList;
    }
}
