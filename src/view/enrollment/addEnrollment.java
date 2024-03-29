package view.enrollment;

import java.sql.Timestamp;

import controllers.courseController;
import controllers.enrollmentController;
import controllers.studentController;
import controllers.viewController;
import interfaces.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
// import controllers.courseController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class addEnrollment implements Node {
    public static String courseName = "";
    public static String email = "";

    @Override
    public VBox getNode() {
        // VBox
        VBox root = new VBox();
        root.setFillWidth(true);

        // Go back
        Button goBackBtn = new Button("Cancel");
        goBackBtn.setId("goBackBtn");

        goBackBtn.setOnAction(event -> {
            viewController.previousNode();
        });

        // Gridpane for scene
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setAlignment(Pos.CENTER);

        // title
        Label headerText = new Label("Add an Enrollment");
        headerText.setId("headerTitle");
        // =======================================================================================================
        // email label
        Label emailString = new Label("Email:");
        emailString.getStyleClass().add("labelText");

        // retrieve all students and list them
        ObservableList<studentController.Student> allStudents = FXCollections
                .observableArrayList(studentController.getAllStudents());

        // email comboBox
        ComboBox<studentController.Student> email = new ComboBox<>();
        email.getItems().addAll(allStudents);
        email.setMinWidth(400);

        // email error
        Label emailError = new Label("");
        emailError.getStyleClass().add("errorMsg");
        // =======================================================================================================
        // name courseName
        Label courseNameString = new Label("Course Name:");
        courseNameString.getStyleClass().add("labelText");

        // retrieve all courses and list them
        ObservableList<courseController.Course> allCourses = FXCollections
                .observableArrayList(courseController.getAllCourses());

        // course comboBox
        ComboBox<courseController.Course> courseName = new ComboBox<>();
        courseName.getItems().addAll(allCourses);
        courseName.setMinWidth(400);

        // name courseName
        Label courseNameError = new Label("");
        courseNameError.getStyleClass().add("errorMsg");
        // =======================================================================================================

        // submit button
        Button submitButton = new Button("ADD ENROLLMENT");
        submitButton.getStyleClass().add("enrollmentSubmitBtn");

        // submit status
        Label submitStatus = new Label("");
        submitStatus.getStyleClass().add("submitStatus");

        // =========================================================================================================

        // Add fields to pane
        layout.add(headerText, 0, 0);

        layout.add(emailString, 0, 3);
        layout.add(email, 0, 4);
        layout.add(emailError, 0, 5);

        layout.add(courseNameString, 0, 6);
        layout.add(courseName, 0, 7);
        layout.add(courseNameError, 0, 8);

        layout.add(submitButton, 0, 9);
        layout.add(submitStatus, 0, 10);

        submitButton.setOnAction((event) -> {
            boolean status = false;
            String response = "";

            // Validate input fields

            if (courseName.getValue() == null) {
                courseNameError.setText("Course name not valid.");
                status = false;
            } else {
                courseNameError.setText("");
                status = true;
            }

            if (email.getValue() == null) {
                emailError.setText("Email not valid.");
                status = false;
            } else {
                emailError.setText("");
                status = true;
            }

            if (status) {

                enrollmentController.Enrollment newEnrollment = new enrollmentController.Enrollment(
                        courseName.getValue().getCourseName(),
                        email.getValue().getEmail(), new Timestamp(System.currentTimeMillis()).toString());
                response = enrollmentController
                        .addEnrollment(email.getValue(), newEnrollment);
                submitStatus.setText(response);
                goBackBtn.setText("Go back");

            }
        });

        layout.setVgap(5);
        // Add button(s)/gridpane(s) to root and return root
        root.getChildren().addAll(goBackBtn, layout);
        VBox.setMargin(goBackBtn, new Insets(15, 15, 0, 15));

        return root;

    }

}
