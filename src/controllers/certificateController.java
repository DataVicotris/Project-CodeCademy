package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controllers.employeeController.Employee;
import controllers.studentController.Student;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class certificateController {
    public static class Certificate {
        private final SimpleIntegerProperty certificateID;
        private SimpleStringProperty grade;
        private Employee employee;
        private final SimpleStringProperty courseName;
        private final SimpleStringProperty email;
        private final SimpleStringProperty enrollmentDate;

        // private final LocalDateTime enrollmentDate = java.time.LocalDateTime.now();

        public Certificate(Integer certificateID, String grade, Employee employee, String courseName, String email,
                String enrollmentDate) {
            this.certificateID = new SimpleIntegerProperty(certificateID);
            this.grade = new SimpleStringProperty(grade);
            this.employee = employee;
            this.courseName = new SimpleStringProperty(courseName);
            this.email = new SimpleStringProperty(email);
            this.enrollmentDate = new SimpleStringProperty(enrollmentDate);
        }

        // Getters and setters
        public int getCertificateID() {
            return this.certificateID.get();
        }

        public String getGrade() {
            return this.grade.get();
        }

        public void setGrade(String grade) {
            this.grade = new SimpleStringProperty(grade);
        }

        public String getEmployeeName() {
            return this.employee.getEmployeeName();
        }

        public void setEmployeeName(Employee employee) {
            this.employee = employee;
        }

        public String getCourseName() {
            return this.courseName.get();
        }

        public Employee getEmployee() {
            return this.employee;
        }

        public String getEmail() {
            return this.email.get();
        }

        public String getEnrollmentDate() {
            return this.enrollmentDate.get();
        }
    }

    public static ArrayList<Certificate> getAllCertificates() {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;
        // ArrayList of all certificates
        ArrayList<Certificate> certificatetList = new ArrayList<Certificate>();

        try {
            // Checking database info with query and show course table linked with
            // enrollment
            con = DBConnection.connect();

            String query = "SELECT * FROM Certificate INNER JOIN Enrollment ON Enrollment.CertificateID = Certificate.CertificateID";

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {

                String certificateID = "";
                if (rs.getDouble("Grade") == 0.0) {
                    certificateID = "Not graded";
                } else {
                    certificateID = String.valueOf(rs.getDouble("Grade"));
                }

                Certificate certificate = new Certificate(rs.getInt("CertificateID"),
                        certificateID,
                        new Employee(rs.getString("EmployeeName")), rs.getString("CourseName"), rs.getString("Email"),
                        rs.getString("EnrollmentDate"));
                certificatetList.add(certificate);
            }

            return certificatetList;

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmt, con);
        }
        return certificatetList;
    }

    public static String getNameByEmail(Certificate certificate) {

        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;
        // ArrayList of all certificates

        try {
            // Checking database info with query and show certificate table
            con = DBConnection.connect();

            String query = "SELECT Name FROM Student WHERE Email = ?;";

            stmt = con.prepareStatement(query);
            stmt.setString(1, certificate.getEmail());
            rs = stmt.executeQuery();

            String name = "";

            while (rs.next()) {
                name = rs.getString("Name");
            }

            return name;
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmt, con);
        }
        return "";
    }

    public static String editCertificate(Certificate certificate) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;

        try {
            // Checking database info with query and update certificate in table
            con = DBConnection.connect();

            String query = "UPDATE Certificate SET Grade = ?, EmployeeName = ? WHERE CertificateID = ?";

            stmt = con.prepareStatement(query);
            stmt.setString(1, certificate.getGrade());
            stmt.setString(2, certificate.getEmployeeName());
            stmt.setInt(3, certificate.getCertificateID());

            stmt.execute();

            return "Certificate successfully edited.";

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return errorController.databaseErrorHandling(e.getErrorCode());
        } finally {
            errorController.queryError(rs, stmt, con);
        }

    }

    public static ArrayList<Certificate> getStudentdCertificates(Student student) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;
        // ArrayList of all certificates
        ArrayList<Certificate> certificatetList = new ArrayList<Certificate>();

        try {
            con = DBConnection.connect();

            String query = "SELECT * FROM Certificate INNER JOIN Enrollment ON Enrollment.CertificateID = Certificate.CertificateID WHERE Email = ? AND Grade IS NOT NULL AND EmployeeName IS NOT NULL";

            stmt = con.prepareStatement(query);
            stmt.setString(1, student.getEmail());
            rs = stmt.executeQuery();

            while (rs.next()) {

                Certificate certificate = new Certificate(rs.getInt("CertificateID"),
                        rs.getString("Grade"),
                        new Employee(rs.getString("EmployeeName")), rs.getString("CourseName"), rs.getString("Email"),
                        rs.getString("EnrollmentDate"));
                certificatetList.add(certificate);
            }

            return certificatetList;

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmt, con);
        }
        return certificatetList;
    }
}
