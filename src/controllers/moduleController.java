package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controllers.courseController.Course;
import controllers.studentController.Student;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class moduleController {

    public static class Module {
        private SimpleIntegerProperty trackingNumber;
        private SimpleIntegerProperty contentItemId;
        private final SimpleStringProperty title;
        private SimpleDoubleProperty version;
        private final SimpleStringProperty description;
        private final SimpleStringProperty contactPerson;
        private SimpleStringProperty publicationDate;
        private SimpleStringProperty status;
        private SimpleStringProperty progress;

        public Module(Integer contentItemId, String title, double version,
                String description, String contactPerson,
                String publicationDate, String status) {
            this.contentItemId = new SimpleIntegerProperty(contentItemId);
            this.title = new SimpleStringProperty(title);
            this.version = new SimpleDoubleProperty(version);
            this.description = new SimpleStringProperty(description);
            this.contactPerson = new SimpleStringProperty(contactPerson);
            this.publicationDate = new SimpleStringProperty(publicationDate);
            this.status = new SimpleStringProperty(status);

        }

        public Module(Integer contentItemId, String title, double version,
                String description, String contactPerson,
                String publicationDate, String status, String progress) {
            this.contentItemId = new SimpleIntegerProperty(contentItemId);
            this.title = new SimpleStringProperty(title);
            this.version = new SimpleDoubleProperty(version);
            this.description = new SimpleStringProperty(description);
            this.contactPerson = new SimpleStringProperty(contactPerson);
            this.publicationDate = new SimpleStringProperty(publicationDate);
            this.status = new SimpleStringProperty(status);
            this.progress = new SimpleStringProperty(progress);
        }

        public Module(Integer trackingNumber, String title,
                String description, String contactPerson, String progress) {
            this.trackingNumber = new SimpleIntegerProperty(trackingNumber);
            this.title = new SimpleStringProperty(title);
            this.description = new SimpleStringProperty(description);
            this.contactPerson = new SimpleStringProperty(contactPerson);
            this.progress = new SimpleStringProperty(progress);
        }

        //Getters and setters
        public Integer getTrackingNumber() {
            return trackingNumber.get();
        }

        public Integer getContentItemId() {
            return contentItemId.get();
        }

        public String getTitle() {
            return title.get();
        }

        public double getVersion() {
            return version.get();
        }

        public String getDescription() {
            return description.get();
        }

        public String getContactPerson() {
            return contactPerson.get();
        }

        public String getPublicationDate() {
            return publicationDate.get();
        }

        public String getStatus() {
            return status.get();
        }

        public String getProgress() {
            return progress.get();
        }

        public void setProgress(String progress) {
            this.progress = new SimpleStringProperty(progress);
        }

        @Override
        public String toString() {
            return title.get();
        }

    }

    public static ArrayList<Module> getAllUnlistedModules() {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;
        // ArrayList of all Modules
        ArrayList<Module> ModuleList = new ArrayList<Module>();

        try {
            //Checking database info with query and show module table
            con = DBConnection.connect();

            String query = "SELECT * FROM Module WHERE (ContentItemId NOT IN (SELECT ContentItemId FROM Course_Module))";

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Module Module = new Module(
                        rs.getInt("ContentItemId"),
                        rs.getString("Title"),
                        rs.getDouble("Version"),
                        rs.getString("Description"),
                        rs.getString("ContactPerson"),
                        rs.getString("PublicationDate"),
                        rs.getString("Status"));
                ModuleList.add(Module);
            }

            return ModuleList;

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmt, con);
        }
        return ModuleList;
    }

    public static ArrayList<Module> getAllModulesBasedOnCourse(Course course) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;
        // ArrayList of all Modules
        ArrayList<Module> ModuleList = new ArrayList<Module>();

        try {
            //Checking database info with query and show module table
            con = DBConnection.connect();

            String query = "SELECT TrackingNumber, Title, Description, ContactPerson, AVG(CAST(Progress AS float)) AS Progress FROM Module INNER JOIN Course_Module ON Module.ContentItemID = Course_Module.ContentItemID LEFT JOIN Student_Content ON Module.ContentItemID = Student_Content.ModuleItemID WHERE Course_Module.CourseName = ? AND module.status = ? GROUP BY Module.Title, TrackingNumber, Title, Description, ContactPerson, module.contentItemID";

            stmt = con.prepareStatement(query);
            stmt.setString(1, course.getCourseName());
            stmt.setString(2, "active");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Module Module = new Module(
                        rs.getInt("TrackingNumber"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("ContactPerson"),
                        (rs.getDouble("Progress") + " %"));
                ModuleList.add(Module);
            }

            return ModuleList;

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmt, con);
        }
        return ModuleList;

    }

    public static String addModuleToCourse(Course thisCourse, Module thisModule) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmtMaxTrackingNumber = null;
        PreparedStatement stmtModule = null;
        // Initiate response variable
        ResultSet rs = null;
        
        int currentTrackingNumber = 0;

        try {
            //Checking database info with query and insert module into course
            con = DBConnection.connect();

            String maxTrackingNumberModule = "SELECT MAX(TrackingNumber) AS MaxTrackingNumber FROM Course_Module WHERE CourseName = ?";

            stmtMaxTrackingNumber = con.prepareStatement(maxTrackingNumberModule);
            stmtMaxTrackingNumber.setString(1, thisCourse.getCourseName());
            ResultSet maxTrackingNumber = stmtMaxTrackingNumber.executeQuery();

            while (maxTrackingNumber.next()) {
                currentTrackingNumber = maxTrackingNumber.getInt("MaxTrackingNumber");
            }

            String queryModule = "INSERT INTO Course_Module (CourseName, TrackingNumber, ContentItemID) VALUES (?, ?, ?)";

            stmtModule = con.prepareStatement(queryModule);
            stmtModule.setString(1, thisCourse.getCourseName());
            stmtModule.setInt(2, currentTrackingNumber + 1);
            stmtModule.setInt(3, thisModule.getContentItemId());
            stmtModule.execute();

            addContentToStudentAfterModuleAdded(thisCourse);

            return "Module successfully added to course.";
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return errorController.databaseErrorHandling(e.getErrorCode());
        } finally {
            errorController.queryError(rs, stmtMaxTrackingNumber, con);
        }
    }

    public static void addContentToStudentAfterModuleAdded(Course thisCourse) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmtStudent = null;
        PreparedStatement stmtContentItemID = null;
        PreparedStatement stmtUpdateStudentContent = null;
        // Initiate response variable
        ResultSet rs = null;
        ResultSet rsStudent = null;
        ResultSet rsContentItemId = null;

        try {
            con = DBConnection.connect();

            String queryStudent = "SELECT Email FROM Enrollment WHERE CourseName = ?";

            stmtStudent = con.prepareStatement(queryStudent);
            stmtStudent.setString(1, thisCourse.getCourseName());
            rsStudent = stmtStudent.executeQuery();

            while (rsStudent.next()) {
                String queryModuleItemID = "SELECT ContentItemID FROM Course_Module WHERE CourseName = ? AND ContentItemID NOT IN (SELECT ModuleItemID FROM Student_Content WHERE ModuleItemID IS NOT NULL)";

                stmtContentItemID = con.prepareStatement(queryModuleItemID);
                stmtContentItemID.setString(1, thisCourse.getCourseName());
                rsContentItemId = stmtContentItemID.executeQuery();

                while (rsContentItemId.next()) {
                    int moduleItemID = rsContentItemId.getInt("ContentItemID");
                    String queryLastContentItemID = "INSERT INTO Student_Content(Email, ModuleItemID) VALUES (?, ?)";

                    stmtUpdateStudentContent = con.prepareStatement(queryLastContentItemID);
                    stmtUpdateStudentContent.setString(1, rsStudent.getString("Email"));
                    stmtUpdateStudentContent.setInt(2, moduleItemID);
                    stmtUpdateStudentContent.executeUpdate();
                }
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmtStudent, con);
        }
    }

    public static ArrayList<Module> getAllLinkedModulesBasedOnStudents(Course course, Student student) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;
        // ArrayList of all Modules
        ArrayList<Module> ModuleList = new ArrayList<Module>();

        try {
            //Checking database info with query and show module table
            con = DBConnection.connect();

            String query = "SELECT * FROM Module INNER JOIN Student_Content ON Module.ContentItemID = Student_Content.ModuleItemID WHERE ContentItemID IN (SELECT ContentItemID FROM Course_Module WHERE CourseName = ?) AND Email = ?;";

            stmt = con.prepareStatement(query);
            stmt.setString(1, course.getCourseName());
            stmt.setString(2, student.getEmail());
            rs = stmt.executeQuery();

            while (rs.next()) {
                Module Module = new Module(
                        rs.getInt("ContentItemId"),
                        rs.getString("Title"),
                        rs.getDouble("Version"),
                        rs.getString("Description"),
                        rs.getString("ContactPerson"),
                        rs.getString("PublicationDate"),
                        rs.getString("Status"),
                        rs.getString("Progress"));
                ModuleList.add(Module);
            }

            return ModuleList;

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmt, con);
        }
        return ModuleList;
    }

    public static String updateStudentModuleProgress(Student student, Module module, Course course) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;
        PreparedStatement stmt4 = null;
        PreparedStatement stmt5 = null;
        PreparedStatement stmt6 = null;
        // Initiate response variable
        ResultSet rs = null;
        ResultSet rs2 = null;
        // list with current progress
        ArrayList<String> currentProgress = new ArrayList<>();

        try {
            con = DBConnection.connect();

            String query = "UPDATE Student_Content SET Progress = ? WHERE Email = ? AND ModuleItemID = ?;";

            stmt = con.prepareStatement(query);
            stmt.setString(1, module.getProgress());
            stmt.setString(2, student.getEmail());
            stmt.setInt(3, module.getContentItemId());
            stmt.execute();

            String checkIfAllModulesAreCompleted = "SELECT Progress FROM Student_Content WHERE Email = ? AND ModuleItemID IN (SELECT ContentItemID FROM Course_Module WHERE CourseName = ?)";

            stmt2 = con.prepareStatement(checkIfAllModulesAreCompleted);
            stmt2.setString(1, student.getEmail());
            stmt2.setString(2, course.getCourseName());
            rs = stmt2.executeQuery();

            while (rs.next()) {
                currentProgress.add(rs.getString("Progress"));
            }

            int totalProgress = 0;
            int count = 0;
            for (String i : currentProgress) {

                totalProgress += Integer.valueOf(i);
                count++;
            }

            int coursePercentage = Math.round(totalProgress / currentProgress.size());

            String addPercentageToCourse = "UPDATE Percentage SET Percentage = ? WHERE Email = ? AND CourseName = ?";

            stmt3 = con.prepareStatement(addPercentageToCourse);
            stmt3.setInt(1, coursePercentage);
            stmt3.setString(2, student.getEmail());
            stmt3.setString(3, course.getCourseName());
            stmt3.execute();

            if (totalProgress / count == 100) {

                String makeCertificateQuery = "INSERT INTO Certificate (Grade, EmployeeName) VALUES (NULL,NULL)";
                stmt4 = con.prepareStatement(makeCertificateQuery);
                stmt4.execute();

                String getHighestCertificateID = "SELECT TOP 1 CertificateID FROM Certificate ORDER BY CertificateID DESC";
                stmt5 = con.prepareStatement(getHighestCertificateID);
                rs2 = stmt5.executeQuery();

                int certificateID = 0;

                while (rs2.next()) {
                    certificateID += rs2.getInt("CertificateID");
                }

                String linkStudentCertificate = "UPDATE Enrollment SET CertificateID = ? WHERE  Email = ? AND CourseName = ?";

                stmt6 = con.prepareStatement(linkStudentCertificate);
                stmt6.setInt(1, certificateID);
                stmt6.setString(2, student.getEmail());
                stmt6.setString(3, course.getCourseName());
                stmt6.execute();
            }

            return "Progress succesfully updated.";

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return errorController.databaseErrorHandling(e.getErrorCode());
        } finally {
            errorController.queryError(rs, stmt, con);
        }
    }

}