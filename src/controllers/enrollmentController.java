package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controllers.studentController.Student;
import javafx.beans.property.SimpleStringProperty;

public class enrollmentController {
    public static class Enrollment {
        private final SimpleStringProperty courseName;
        private final SimpleStringProperty email;
        private final SimpleStringProperty enrollmentDate;
        private SimpleStringProperty certificateID;

        public Enrollment(String courseName, String email, String enrollmentDate, String certificateID) {
            this.courseName = new SimpleStringProperty(courseName);
            this.email = new SimpleStringProperty(email);
            this.enrollmentDate = new SimpleStringProperty(enrollmentDate);
            this.certificateID = new SimpleStringProperty(certificateID);
        }

        public Enrollment(String courseName, String email, String enrollmentDate) {
            this.courseName = new SimpleStringProperty(courseName);
            this.email = new SimpleStringProperty(email);
            this.enrollmentDate = new SimpleStringProperty(enrollmentDate);
        }

        public Enrollment(String courseName) {
            this.courseName = new SimpleStringProperty(courseName);
            this.email = new SimpleStringProperty();
            this.enrollmentDate = new SimpleStringProperty();
        }

        // Getters and setters
        public String getCertificateID() {
            return this.certificateID.get();
        }

        public String getCourseName() {
            return this.courseName.get();
        }

        public String getEmail() {
            return this.email.get();
        }

        public String getEnrollmentDate() {
            return this.enrollmentDate.get();
        }

    }

    public static ArrayList<Enrollment> getAllEnrollments() {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;
        // ArrayList of all certificates
        ArrayList<Enrollment> enrollmentList = new ArrayList<Enrollment>();

        try {
            // Checking database info with query and show enrollment table
            con = DBConnection.connect();

            String SQL = "SELECT * FROM Enrollment";

            stmt = con.prepareStatement(SQL);
            rs = stmt.executeQuery();

            while (rs.next()) {

                String certificateID = "";
                if (rs.getInt("CertificateId") == 0) {
                    certificateID = "No certificate";
                } else {
                    certificateID = rs.getString("CertificateId");
                }

                Enrollment enrollment = new Enrollment(rs.getString("CourseName"),
                        rs.getString("Email"),
                        rs.getString("EnrollmentDate"), certificateID);

                enrollmentList.add(enrollment);
            }

            return enrollmentList;

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmt, con);
        }
        return enrollmentList;
    }

    public static String addEnrollment(Student student, Enrollment newEnrollment) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmtEnrollment = null;
        PreparedStatement stmtCourseAdd = null;
        PreparedStatement stmtGetIDs = null;
        PreparedStatement stmtLinkToModules = null;

        // Initiate response variable
        ResultSet rs = null;

        // list for content ids
        ArrayList<String> contentIDs = new ArrayList<>();

        try {
            // Checking database info with query and insert into enrollment
            con = DBConnection.connect();

            String queryEnrollment = "INSERT INTO Enrollment (EnrollmentDate, CourseName, Email) VALUES (? ,? ,?)";

            stmtEnrollment = con.prepareStatement(queryEnrollment);

            stmtEnrollment.setString(1, newEnrollment.getEnrollmentDate());
            stmtEnrollment.setString(2, newEnrollment.getCourseName());
            stmtEnrollment.setString(3, newEnrollment.getEmail());
            stmtEnrollment.execute();

            String makeCoursePercentageLinkQuery = "INSERT INTO Percentage (EnrollmentDate,CourseName,Email) VALUES (?, ? , ?)";

            stmtCourseAdd = con.prepareStatement(makeCoursePercentageLinkQuery);
            stmtCourseAdd.setString(1, newEnrollment.getEnrollmentDate());
            stmtCourseAdd.setString(2, newEnrollment.getCourseName());
            stmtCourseAdd.setString(3, newEnrollment.getEmail());
            stmtCourseAdd.execute();

            String getAllRelatedContentIds = "SELECT ContentItemID FROM Course_Module Where CourseName = ?;";

            stmtGetIDs = con.prepareStatement(getAllRelatedContentIds);
            stmtGetIDs.setString(1, newEnrollment.getCourseName());
            rs = stmtGetIDs.executeQuery();

            while (rs.next()) {
                contentIDs.add(rs.getString("ContentItemID"));
            }

            // Add enrollment to database
            if (contentIDs.size() >= 1) {
                for (String i : contentIDs) {
                    String linkEmailToModules = "INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES (?, ? , 0);";

                    stmtLinkToModules = con.prepareStatement(linkEmailToModules);

                    stmtLinkToModules.setString(1, newEnrollment.getEmail());
                    stmtLinkToModules.setString(2, i);
                    stmtLinkToModules.execute();

                }
            }

            return "Enrollment successfully added.";
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return errorController.databaseErrorHandling(e.getErrorCode());
        } finally {
            errorController.queryError(rs, stmtEnrollment, con);
        }
    }

    public static ArrayList<Enrollment> getTop3CertificatedCourses() {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;
        // ArrayList of all Courses with most certificates
        ArrayList<Enrollment> enrollmentList = new ArrayList<>();

        try {
            // Checking database info with query and show enrollment table
            con = DBConnection.connect();

            String query = "SELECT TOP 3 CourseName, count(*) AS count FROM Enrollment WHERE CertificateID IS NOT NULL GROUP BY CourseName ORDER BY Count(*) DESC";

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Enrollment enrollment = new Enrollment(
                        rs.getString("CourseName"));
                enrollmentList.add(enrollment);
            }

            return enrollmentList;

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmt, con);
        }
        return enrollmentList;
    }

}
