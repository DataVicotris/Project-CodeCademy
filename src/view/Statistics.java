package view;

import java.util.ArrayList;

import controllers.webcastController.Webcast;
import controllers.studentController;
import controllers.enrollmentController;

import controllers.webcastController;
import controllers.enrollmentController.Enrollment;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import view.student.Gender;

public class Statistics {

    public VBox getNode() {
        //VBox
        VBox root = new VBox();
        root.setFillWidth(true);
        //Gridpane for scene
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        //Label for scene
        Label topWebcastTitle = new Label("Top 3 Webcasts:");
        topWebcastTitle.getStyleClass().addAll("boldHeader");

        Label top3Webcasts = new Label();

        Label titleLabel = new Label("Statistics:");
        titleLabel.setId("statisticsTitle");

        //Arraylist for top 3 webcasts
        ArrayList<Webcast> webcasts = webcastController.getTop3MostViewedWebcasts();
        StringBuilder webcastText = new StringBuilder();
        int webcastCount = 1;
        for (Webcast w : webcasts) {

            webcastText.append(webcastCount + ". " + w.getTitle() + "\n");
            webcastCount++;
        }

        top3Webcasts.setText(webcastText.toString());

        //Label for alumni per gender
        Label selectGenderPassed = new Label("Check percentage of alumni per gender:");
        selectGenderPassed.getStyleClass().addAll("boldHeader");

        ComboBox<String> genderDropdown = new ComboBox<>();
        genderDropdown.getItems().addAll(Gender.MALE.value, Gender.FEMALE.value,
                Gender.OTHER.value);

        genderDropdown.setValue(Gender.MALE.value);
        //Button
        Button selectGenderBtn = new Button("Check");

        Label courseAlumniGenderLabel = new Label("");

        selectGenderBtn.setOnAction(e -> {

            String genderValue = "";

            if (genderDropdown.getValue().equals("Male")) {
                genderValue = "M";
            } else if (genderDropdown.getValue().equals("Female")) {
                genderValue = "F";
            } else {
                genderValue = "O";
            }

            int percentage = studentController.getPercentageofPassedStudentsByGender(genderValue);
            courseAlumniGenderLabel
                    .setText(percentage + "% of " + genderDropdown.getValue() + "'s has completed a course.");
        });

        Separator separator = new Separator(Orientation.HORIZONTAL);
        Separator separator2 = new Separator(Orientation.HORIZONTAL);

        separator.setPadding(new Insets(10, 0, 10, 0));
        separator2.setPadding(new Insets(10, 0, 10, 0));

        layout.setVgap(15);

        // Top 3 courses that have the most alumni
        Label topCertificatedCoursesTitle = new Label("Top 3 Courses with most alumni:");
        topCertificatedCoursesTitle.getStyleClass().addAll("boldHeader");

        Label top3CertificatedCourses = new Label();
        ArrayList<Enrollment> topCourses = enrollmentController.getTop3CertificatedCourses();
        StringBuilder coursesText = new StringBuilder();

        int countEnrollment = 1;

        for (Enrollment e : topCourses) {

            coursesText.append(countEnrollment + ". " + e.getCourseName() + "\n");
            countEnrollment++;

        }

        top3CertificatedCourses.setText(coursesText.toString());
        layout.add(titleLabel, 0, 0);
        layout.add(topWebcastTitle, 0, 1);
        layout.add(top3Webcasts, 0, 2);

        layout.add(separator, 0, 3);

        layout.add(topCertificatedCoursesTitle, 0, 4);
        layout.add(top3CertificatedCourses, 0, 5);

        layout.add(separator2, 0, 6);

        layout.add(selectGenderPassed, 0, 7);
        layout.add(genderDropdown, 0, 8);
        layout.add(selectGenderBtn, 0, 9);
        layout.add(courseAlumniGenderLabel, 0, 10);

        VBox.setMargin(layout, new Insets(15, 15, 15, 15));


        //Add button(s)/gridpane(s) to root and return root
        root.getChildren().addAll(layout);
        VBox.setMargin(layout, new Insets(150, 0, 0, 0));

        return root;
    }
}