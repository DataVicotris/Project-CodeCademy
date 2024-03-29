package view.course;

import java.util.ArrayList;

import controllers.courseController;
import controllers.subjectController;
import controllers.moduleController;
import controllers.validationController;
import controllers.viewController;
import controllers.subjectController.Subject;
import interfaces.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class addCourse implements Node {

    public VBox getNode() {
        //VBox
        VBox root = new VBox();
        root.setFillWidth(true);

        // Go back
        Button goBackBtn = new Button("Cancel");
        goBackBtn.setId("goBackBtn");

        goBackBtn.setOnAction(event -> {
            viewController.previousNode();
        });

        // title
        Label instructionText = new Label("Add a Course!");
        instructionText.setId("headerTitle");

        // coursname label
        Label courseNameString = new Label("Course name:");
        courseNameString.getStyleClass().add("labelText");

        // coursename textfield
        TextField courseName = new TextField();
        courseName.getStyleClass().add("cstmTextfield");

        // coursename error
        Label courseNameError = new Label("");
        courseNameError.getStyleClass().add("errorMsg");

        // subject label
        Label subjectString = new Label("Subject:");
        subjectString.getStyleClass().add("labelText");

        // list with all subjects
        ObservableList<Subject> subjectList = FXCollections
                .observableArrayList(subjectController.getAllSubjects());

        ComboBox<Subject> subject = new ComboBox<>();
        subject.getItems().addAll(subjectList);

        // subject error
        Label subjectError = new Label("");
        subjectError.getStyleClass().add("errorMsg");

        // introtext label
        Label introTextString = new Label("Intro text:");
        introTextString.getStyleClass().add("labelText");

        // introtext textfield
        TextArea introText = new TextArea();
        introText.getStyleClass().add("cstmTextfield");

        // introtext errorMsg
        Label introTextError = new Label("");
        introTextError.getStyleClass().add("errorMsg");

        // level label
        Label levelString = new Label("Level:");
        levelString.getStyleClass().add("labelText");

        ComboBox<String> level = new ComboBox<>();
        level.getItems().addAll(courseLevel.BEGINNER.value, courseLevel.INTERMEDIATE.value,
                courseLevel.EXPERT.value);

        // level errorMsg
        Label levelError = new Label("");
        levelError.getStyleClass().add("errorMsg");
        // ================================================================================================
        Label moduleString = new Label("Module:");
        moduleString.getStyleClass().add("labelText");

        // list with unlinked modules
        ObservableList<moduleController.Module> allUnlistedModules = FXCollections
                .observableArrayList(moduleController.getAllUnlistedModules());

        ComboBox<moduleController.Module> module = new ComboBox<>();
        module.getItems().addAll(allUnlistedModules);

        Label moduleError = new Label("");
        moduleError.getStyleClass().add("errorMsg");

        // submit button
        Button submitButton = new Button("ADD COURSE");
        submitButton.getStyleClass().add("courseSubmitBtn");

        // submit status
        Label submitStatus = new Label("");
        submitStatus.getStyleClass().add("submitStatus");

        // Gridpane for scene
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);

        // Add fields to pane
        layout.add(goBackBtn, 0, 0);
        layout.add(instructionText, 0, 1);

        layout.add(courseNameString, 0, 3);
        layout.add(courseName, 0, 4);
        layout.add(courseNameError, 0, 5);

        layout.add(subjectString, 0, 6);
        layout.add(subject, 0, 7);
        layout.add(subjectError, 0, 8);

        layout.add(introTextString, 0, 9);
        layout.add(introText, 0, 10);
        layout.add(introTextError, 0, 11);

        layout.add(levelString, 0, 12);
        layout.add(level, 0, 13);
        layout.add(levelError, 0, 14);

        layout.add(moduleString, 0, 15);
        layout.add(module, 0, 16);
        layout.add(moduleError, 0, 17);

        layout.add(submitButton, 0, 18);
        layout.add(submitStatus, 0, 19);

        submitButton.setOnAction((event) -> {
            ArrayList<Boolean> status = new ArrayList<>();
            String response = "";

            // Validate input fields

            if (!validationController.courseNameChecker(courseName.getText())) {
                courseNameError.setText("Course name not valid.");
                status.add(false);
            } else {
                courseNameError.setText("");
                status.add(true);
            }

            if (subject.getValue() == null) {
                subjectError.setText("Subject not valid.");
                status.add(false);
            } else {
                subjectError.setText("");
                status.add(true);
            }

            if (!validationController.introTextChecker(introText.getText())) {
                introTextError.setText("Intro text not valid.");
                status.add(false);
            } else {
                introTextError.setText("");
                status.add(true);
            }

            if (level.getValue() == null) {
                levelError.setText("Level not valid.");
                status.add(false);
            } else {
                levelError.setText("");
                status.add(true);
            }

            if (module.getValue() == null) {
                moduleError.setText("Module not valid.");
                status.add(false);
            } else {
                moduleError.setText("");
                status.add(true);
            }

            boolean validator = status.stream().allMatch(Boolean::booleanValue);

            if (validator) {
                response = courseController
                        .addCourse(new courseController.Course(courseName.getText(), subject.getValue(),
                                introText.getText(), level.getValue(), module.getValue()));
                submitStatus.setText(response);
                goBackBtn.setText("Go back");

            } else {
                submitStatus.setText("");
            }
        });

        //Add button(s)/gridpane(s) to root and return root
        root.getChildren().addAll(goBackBtn, layout);
        VBox.setMargin(goBackBtn, new Insets(15, 15, 0, 15));

        return root;

    }

}