package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controllers.studentController.Student;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class webcastController {

    public static class Webcast {
        private final SimpleIntegerProperty contentItemId;
        private final SimpleStringProperty title;
        private final SimpleStringProperty description;
        private final SimpleIntegerProperty duration;
        private final SimpleStringProperty speakerName;
        private final SimpleStringProperty url;
        private final SimpleIntegerProperty views;
        private final SimpleStringProperty publicationDate;
        private final SimpleStringProperty status;
        private SimpleStringProperty progress;

        public Webcast(Integer contentItemId, String title, String description,
                Integer duration, String speakerName,
                String url, Integer views, String publicationDate, String status) {
            this.contentItemId = new SimpleIntegerProperty(contentItemId);
            this.title = new SimpleStringProperty(title);
            this.description = new SimpleStringProperty(description);
            this.duration = new SimpleIntegerProperty(duration);
            this.speakerName = new SimpleStringProperty(speakerName);
            this.url = new SimpleStringProperty(url);
            this.views = new SimpleIntegerProperty(views);
            this.publicationDate = new SimpleStringProperty(publicationDate);
            this.status = new SimpleStringProperty(status);

        }

        public Webcast(Integer contentItemId, String title, String description,
                Integer duration, String speakerName,
                String url, Integer views, String publicationDate, String status, String progress) {
            this.contentItemId = new SimpleIntegerProperty(contentItemId);
            this.title = new SimpleStringProperty(title);
            this.description = new SimpleStringProperty(description);
            this.duration = new SimpleIntegerProperty(duration);
            this.speakerName = new SimpleStringProperty(speakerName);
            this.url = new SimpleStringProperty(url);
            this.views = new SimpleIntegerProperty(views);
            this.publicationDate = new SimpleStringProperty(publicationDate);
            this.status = new SimpleStringProperty(status);
            this.progress = new SimpleStringProperty(progress);

        }

        public Integer getContentItemId() {
            return this.contentItemId.get();
        }

        public String getTitle() {
            return this.title.get();
        }

        public String getDescription() {
            return this.description.get();
        }

        public Integer getDuration() {
            return duration.get();
        }

        public String getSpeakerName() {
            return this.speakerName.get();
        }

        public String getURL() {
            return this.url.get();
        }

        public Integer getViews() {
            return this.views.get();
        }

        public String getPublicationDate() {
            return this.publicationDate.get();
        }

        public String getProgress() {
            return this.progress.get();
        }

        public String getStatus() {
            return this.status.get();
        }

        @Override
        public String toString() {
            return this.title.get();
        }

    }

    public static ArrayList<Webcast> getTop3MostViewedWebcasts() {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;
        // ArrayList of all Modules
        ArrayList<Webcast> WebcastList = new ArrayList<>();

        try {
            //Checking database info with query and show webcast table
            con = DBConnection.connect();

            String query = "SELECT TOP 3 * FROM Webcast ORDER BY Views DESC";

            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Webcast webcast = new Webcast(
                        rs.getInt("ContentItemId"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getInt("Duration"),
                        rs.getString("SpeakerName"),
                        rs.getString("URL"),
                        rs.getInt("Views"),
                        rs.getString("PublicationDate"),
                        rs.getString("Status"));

                WebcastList.add(webcast);
            }

            return WebcastList;

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmt, con);
        }
        return WebcastList;
    }

    public static ArrayList<Webcast> getAllStudentWebcasts(Student student) {
        // Database connection info.
        Connection con = null;
        // Statement for executing query.
        PreparedStatement stmt = null;
        // Initiate response variable
        ResultSet rs = null;
        // ArrayList of all Modules
        ArrayList<Webcast> WebcastList = new ArrayList<>();

        try {
            //Checking database info with query and show webcasts linked with student_content
            con = DBConnection.connect();

            String query = "SELECT * FROM Student_Content INNER JOIN Webcast ON WebcastItemID = Webcast.ContentItemID WHERE Email = ? AND WebcastItemID IS NOT NULL";

            stmt = con.prepareStatement(query);
            stmt.setString(1, student.getEmail());
            rs = stmt.executeQuery();

            while (rs.next()) {
                Webcast webcast = new Webcast(
                        rs.getInt("ContentItemId"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getInt("Duration"),
                        rs.getString("SpeakerName"),
                        rs.getString("URL"),
                        rs.getInt("Views"),
                        rs.getString("PublicationDate"),
                        rs.getString("Status"),
                        rs.getString("Progress"));

                WebcastList.add(webcast);
            }

            return WebcastList;

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(errorController.databaseErrorHandling(e.getErrorCode()));
        } finally {
            errorController.queryError(rs, stmt, con);
        }
        return WebcastList;
    }

}