package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controllers.studentController.Student;
import controllers.subjectController.Subject;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class courseController {

    public static class Course {
        private final SimpleStringProperty courseName;
        private final Subject subject;
        private final SimpleStringProperty introText;
        private final SimpleStringProperty level;
        private SimpleDoubleProperty avgProgress;
        private final ArrayList<moduleController.Module> moduleList;

        public Course(String courseName, Subject subject, String introText, String level,
                moduleController.Module module) {
            this.courseName = new SimpleStringProperty(courseName);
            this.subject = subject;
            this.introText = new SimpleStringProperty(introText);
            this.level = new SimpleStringProperty(level);
            this.moduleList = new ArrayList<>();
            addModule(module);
        }

        public Course(String courseName, Subject subject, String introText, String level, Double avgProgress) {
            this.courseName = new SimpleStringProperty(courseName);
            this.subject = subject;
            this.introText = new SimpleStringProperty(introText);
            this.level = new SimpleStringProperty(level);
            this.avgProgress = new SimpleDoubleProperty(avgProgress);
            this.moduleList = new ArrayList<>();
        }

        public Course(String courseName, Subject subject, String introText, String level) {
            this.courseName = new SimpleStringProperty(courseName);
            this.subject = subject;
            this.introText = new SimpleStringProperty(introText);
            this.level = new SimpleStringProperty(level);
            this.moduleList = new ArrayList<>();
        }

        //Getters and setters
        public String getCourseName() {
            return this.courseName.get();
        }

        public String getSubject() {
            return this.subject.toString();
        }

        public Subject getSubjectObject() {
            return this.subject;
        }

        public String getIntroText() {
            return this.introText.get();
        }

        public String getLevel() {
            return this.level.get();
        }

        public Double getAvgProgress() {
            return this.avgProgress.get();
        }

        public void addModule(moduleController.Module module) {
            this.moduleList.add(module);
        }

        public ArrayList<moduleController.Module> getModules() {
            return this.moduleList;
        }

        @Override
        public String toString() {
            return this.courseName.get();
        }
    }

    public static ArrayList<Course> getAllCourses() {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;
        // ArrayList of all courses
        ArrayList<Course> courseList = new ArrayList<Course>();

        try {
            //Checking database info with query and show course table
            con = DBConnection.connect();

            String query = "SELECT Course.CourseName, Course.Subject, Course.IntroText, Course.Level, AVG(CAST(Percentage.Percentage AS float)) AS Progress FROM Course LEFT JOIN Percentage ON Course.CourseName = Percentage.CourseName GROUP BY Course.CourseName, Course.Subject, Course.IntroText, Course.Level";

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Course course = new Course(rs.getString("CourseName"), new Subject(rs.getString("Subject")),
                        rs.getString("IntroText"), rs.getString("Level"), rs.getDouble("Progress"));
                courseList.add(course);
            }

            return courseList;

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmt, con);
        }
        return courseList;
    }

    public static ArrayList<Course> getCourseBasedOnName(Course thisCourse) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;
        // ArrayList of all courses
        ArrayList<Course> courseList = new ArrayList<Course>();

        try {
            //Checking database info with query and show course table
            con = DBConnection.connect();

            String query = "SELECT TOP 3 * FROM Course WHERE CourseName = ?";

            stmt = con.prepareStatement(query);
            stmt.setString(1, thisCourse.getCourseName());
            rs = stmt.executeQuery();

            while (rs.next()) {
                Course course = new Course(rs.getString("CourseName"), new Subject(rs.getString("Subject")),
                        rs.getString("IntroText"), rs.getString("Level"));
                courseList.add(course);
            }

            return courseList;

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmt, con);
        }
        return courseList;
    }

    public static ArrayList<Course> getSuggestedCourses(Course thisCourse) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;
        // ArrayList of all courses
        ArrayList<Course> courseList = new ArrayList<Course>();

        try {
            con = DBConnection.connect();

            String query = "SELECT * FROM Course WHERE Subject = ? AND CourseName <> ?";

            stmt = con.prepareStatement(query);
            stmt.setString(1, thisCourse.getSubject());
            stmt.setString(2, thisCourse.getCourseName());

            rs = stmt.executeQuery();

            if (!rs.next()) {
                Course course = new Course("No suggested courses", new Subject("NA"), "", "");
                courseList.add(course);
            } else {
                while (rs.next()) {
                    Course course = new Course(rs.getString("CourseName"), new Subject(rs.getString("Subject")),
                            rs.getString("IntroText"), rs.getString("Level"));
                    courseList.add(course);
                }
            }
            return courseList;

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmt, con);
        }
        return courseList;
    }

    public static Integer getGradedStudentsBasedOnCourse(Course thisCourse) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;
        // ArrayList of all courses
        Integer gradedStudents = 0;

        try {
            con = DBConnection.connect();

            String query = "SELECT Count(*) AS Graded FROM Course INNER JOIN Enrollment ON Course.CourseName = Enrollment.CourseName WHERE CertificateID IS NOT NULL AND Course.CourseName = ? GROUP BY Course.CourseName";

            stmt = con.prepareStatement(query);
            stmt.setString(1, thisCourse.getCourseName());
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                gradedStudents = rs.getInt("Graded");
            }

            return gradedStudents;

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmt, con);
        }
        return gradedStudents;
    }

    public static String addCourse(Course newCourse) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmtCourse = null;
        PreparedStatement stmtModule = null;
        // Initiate response variable
        ResultSet rs = null;

        try {
            //Checking database info with query and insert into course table
            con = DBConnection.connect();

            String queryCourse = "INSERT INTO Course (CourseName, Subject, IntroText, Level) VALUES (?, ?, ?, ?)";

            stmtCourse = con.prepareStatement(queryCourse);

            stmtCourse.setString(1, newCourse.getCourseName());
            stmtCourse.setString(2, newCourse.getSubject());
            stmtCourse.setString(3, newCourse.getIntroText());
            stmtCourse.setString(4, newCourse.getLevel());
            stmtCourse.execute();

            moduleController.Module module = newCourse.getModules().get(0);

            String queryModule = "INSERT INTO Course_Module (CourseName, TrackingNumber, ContentItemID) VALUES (?, ?, ?)";

            stmtModule = con.prepareStatement(queryModule);
            stmtModule.setString(1, newCourse.getCourseName());
            stmtModule.setInt(2, 1);
            stmtModule.setInt(3, module.getContentItemId());
            stmtModule.execute();

            return "Course successfully added.";

            // String maxTrackingNumberModule = "SELECT MAX(TrackingNumber) AS
            // MaxTrackingNumber FROM Course_Module WHERE CourseName = ?";

            // stmtMaxTrackingNumber = con.prepareStatement(maxTrackingNumberModule);
            // stmtMaxTrackingNumber.setString(1, newCourse.getCourseName());
            // ResultSet maxTrackingNumber = stmtMaxTrackingNumber.executeQuery();

            // System.out.println(maxTrackingNumber.getString("MaxTrackingNumber"));

            // ArrayList<moduleController.Module> modules = newCourse.getModules();
            // for (moduleController.Module module : modules) {

            // String queryModule = "INSERT INTO Course_Module (CourseName, TrackingNumber,
            // ContentItemID) VALUES (?, ?, ?)";

            // stmt = con.prepareStatement(queryModule);

            // // stmt.setString(1, module);
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return errorController.databaseErrorHandling(e.getErrorCode());
        } finally {
            errorController.queryError(rs, stmtCourse, con);
        }
    }

    public static String editCourse(Course oldCourse, Course newCourse) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;

        try {
            //Checking database info with query and update course in table
            con = DBConnection.connect();

            String query = "UPDATE Course SET CourseName = ?, Subject = ?, IntroText = ?, Level = ? WHERE CourseName = ?";

            stmt = con.prepareStatement(query);
            stmt.setString(1, newCourse.getCourseName());
            stmt.setString(2, newCourse.getSubject());
            stmt.setString(3, newCourse.getIntroText());
            stmt.setString(4, newCourse.getLevel());
            stmt.setString(5, oldCourse.getCourseName());

            stmt.execute();

            return "Course successfully edited.";

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return errorController.databaseErrorHandling(e.getErrorCode());
        } finally {
            errorController.queryError(rs, stmt, con);
        }

    }

    public static String deleteCourse(Course course) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;

        try {
            //Checking database info with query and delete course from table
            con = DBConnection.connect();

            String query = "DELETE FROM Course WHERE CourseName = ?";

            stmt = con.prepareStatement(query);
            stmt.setString(1, course.getCourseName());

            stmt.execute();

            return "Course successfully deleted.";

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return errorController.databaseErrorHandling(e.getErrorCode());
        } finally {
            errorController.queryError(rs, stmt, con);
        }
    }

    public static ArrayList<Course> getAllLinkedStudentCourses(Student student) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;
        // ArrayList of all courses
        ArrayList<Course> courseList = new ArrayList<Course>();

        try {
            //Checking database info with query and show course table
            con = DBConnection.connect();

            String query = "SELECT * FROM Course WHERE CourseName IN (SELECT CourseName FROM Enrollment WHERE Email = ?)";

            stmt = con.prepareStatement(query);
            stmt.setString(1, student.getEmail());
            rs = stmt.executeQuery();

            while (rs.next()) {
                Course course = new Course(rs.getString("CourseName"), new Subject(rs.getString("Subject")),
                        rs.getString("IntroText"), rs.getString("Level"));
                courseList.add(course);
            }

            return courseList;

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmt, con);
        }
        return courseList;
    }
}