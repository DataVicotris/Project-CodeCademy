package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controllers.courseController.Course;
import javafx.beans.property.SimpleStringProperty;

public class studentController {

    public static class Student {
        private final SimpleStringProperty email;
        private final SimpleStringProperty name;
        private final SimpleStringProperty dateOfBirth;
        private final SimpleStringProperty gender;
        private final SimpleStringProperty street;
        private final SimpleStringProperty houseNumber;
        private final SimpleStringProperty city;
        private final SimpleStringProperty postalCode;
        private final SimpleStringProperty country;
        private ArrayList<Course> linkedCourses;

        public Student(String email, String name, String dateOfBirth, String gender, String street, String houseNumber,
                String city,
                String postalCode,
                String country) {

            this.email = new SimpleStringProperty(email);
            this.name = new SimpleStringProperty(name);
            this.dateOfBirth = new SimpleStringProperty(dateOfBirth);
            this.gender = new SimpleStringProperty(gender);
            this.street = new SimpleStringProperty(street);
            this.houseNumber = new SimpleStringProperty(houseNumber);
            this.city = new SimpleStringProperty(city);
            this.postalCode = new SimpleStringProperty(postalCode);
            this.country = new SimpleStringProperty(country);
        }

        //Getters and setters
        public String getEmail() {
            return this.email.get();
        }

        public String getName() {
            return this.name.get();
        }

        public String getDateOfBirth() {
            return this.dateOfBirth.get();
        }

        public String getGender() {
            return this.gender.get();
        }

        public String getStreet() {
            return this.street.get();
        }

        public String getHouseNumber() {
            return this.houseNumber.get();
        }

        public String getCity() {
            return this.city.get();
        }

        public String getPostalCode() {
            return this.postalCode.get();
        }

        public String getCountry() {
            return this.country.get();
        }

        public void addCourses(Course course) {
            this.linkedCourses.add(course);
        }

        @Override
        public String toString() {
            return this.email.get();
        }

    }

    public static ArrayList<Student> getAllStudents() {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;
        // ArrayList of all courses
        ArrayList<Student> studentList = new ArrayList<Student>();

        try {
            //Checking database info with query and show student table
            con = DBConnection.connect();

            String query = "SELECT * FROM Student";

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Student student = new Student(rs.getString("Email"), rs.getString("Name"),
                        rs.getString("DateOfBirth"), rs.getString("Gender"), rs.getString("Street"),
                        rs.getString("HouseNumber"),
                        rs.getString("City"), rs.getString("PostalCode"), rs.getString("Country"));
                studentList.add(student);
            }
            return studentList;
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmt, con);
        }
        return studentList;
    }

    public static String addStudent(Student newStudent) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;

        try {
            //Checking database info with query and add student into the database
            con = DBConnection.connect();

            String query = "INSERT INTO Student (Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City , Country) VALUES (?,?,?,?,?,?,?,?,?)";

            stmt = con.prepareStatement(query);
            stmt.setString(1, newStudent.getEmail());
            stmt.setString(2, newStudent.getName());
            stmt.setString(3, newStudent.getDateOfBirth());
            stmt.setString(4, newStudent.getGender());
            stmt.setString(5, newStudent.getStreet());
            stmt.setString(6, newStudent.getHouseNumber());
            stmt.setString(7, newStudent.getPostalCode());
            stmt.setString(8, newStudent.getCity());
            stmt.setString(9, newStudent.getCountry());
            stmt.execute();

            return "Student successfully added.";
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return errorController.databaseErrorHandling(e.getErrorCode());
        } finally {
            errorController.queryError(rs, stmt, con);
        }
    }

    public static String deleteStudent(Student student) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;

        try {
            //Checking database info with query and delete student from database
            con = DBConnection.connect();

            String query = "DELETE FROM Student WHERE Email = ?;";

            stmt = con.prepareStatement(query);
            stmt.setString(1, student.getEmail());
            stmt.executeUpdate();

            return "Student successfully deleted.";

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return errorController.databaseErrorHandling(e.getErrorCode());
        } finally {
            errorController.queryError(rs, stmt, con);
        }
    }

    public static String editStudent(Student oldStudent, Student newStudent) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;

        try {
            //Checking database info with query and update student in database
            con = DBConnection.connect();

            String query = "UPDATE Student SET Email = ?, Name = ?, DateOfBirth = ?, Gender = ?, Street = ?, HouseNumber = ?, PostalCode = ?, City = ?, Country = ?  WHERE Email = ?;";

            stmt = con.prepareStatement(query);
            stmt.setString(1, newStudent.getEmail());
            stmt.setString(2, newStudent.getName());
            stmt.setString(3, newStudent.getDateOfBirth());
            stmt.setString(4, newStudent.getGender());
            stmt.setString(5, newStudent.getStreet());
            stmt.setString(6, newStudent.getHouseNumber());
            stmt.setString(7, newStudent.getPostalCode());
            stmt.setString(8, newStudent.getCity());
            stmt.setString(9, newStudent.getCountry());
            stmt.setString(10, oldStudent.getEmail());
            stmt.executeUpdate();

            return "Student successfully edited.";

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return errorController.databaseErrorHandling(e.getErrorCode());
        } finally {
            errorController.queryError(rs, stmt, con);
        }

    }

    public static int getPercentageofPassedStudentsByGender(String gender) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement totalAmountPeople = null;
        PreparedStatement alumni = null;

        // Initiate response variable
        ResultSet rs = null;
        ResultSet secondRs = null;

        // lists
        ArrayList<String> alumniList = new ArrayList<>();
        ArrayList<String> totalList = new ArrayList<>();

        try {
            //Checking database info with query and show students linked with certificates/enrollments
            con = DBConnection.connect();

            String queryAlumniCount = "SELECT Name, Gender, CertificateID FROM Student JOIN Enrollment ON Student.Email = Enrollment.Email WHERE Student.Gender = ? AND CertificateID IS NOT NULL";

            alumni = con.prepareStatement(queryAlumniCount);

            alumni.setString(1, gender);

            rs = alumni.executeQuery();

            while (rs.next()) {
                // count of all students that have finished a course
                alumniList.add(rs.getString("CertificateID"));

            }
            // ===============================================================================================================
            String queryTotalCount = "SELECT CertificateID FROM Student JOIN Enrollment ON Student.Email = Enrollment.Email WHERE Student.Gender = ?";

            totalAmountPeople = con.prepareStatement(queryTotalCount);

            totalAmountPeople.setString(1, gender);

            secondRs = totalAmountPeople.executeQuery();

            while (secondRs.next()) {

                totalList.add(secondRs.getString("CertificateID"));
            }

            if (totalList.size() == 0) {
                return 0;
            }

            int percentage = Math.round((alumniList.size() * 100) / totalList.size());

            return percentage;
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            // return errorController.databaseErrorHandling(e.getErrorCode());
            return 0;
        } finally {
            errorController.queryError(rs, totalAmountPeople, con);
        }
    }
}
