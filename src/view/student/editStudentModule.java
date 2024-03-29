package view.student;

import controllers.moduleController;
import controllers.validationController;
import controllers.viewController;
import interfaces.Node;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class editStudentModule implements Node {

    @Override
    public VBox getNode() {

        VBox root = new VBox();
        root.setFillWidth(true);

        // Go back
        Button goBackBtn = new Button("Cancel");
        goBackBtn.setId("goBackBtn");

        goBackBtn.setOnAction(event -> {
            viewController.previousNode();
        });

        // title
        Label titelString = new Label("Edit progress:");
        titelString.setId("headerTitle");

        // ================================================================================================
        Label progressString = new Label("Progress:");
        progressString.getStyleClass().add("labelText");

        TextField progress = new TextField();
        progress.getStyleClass().add("cstmTextfield");
        progress.setText(viewStudent.oldModule.getProgress());

        Label progressError = new Label("");
        progressError.getStyleClass().add("errorMsg");

        // submit button
        Button submitButton = new Button("EDIT PROGRESS");
        submitButton.getStyleClass().add("submitBtn");

        // submit status
        Label submitStatus = new Label("");
        submitStatus.getStyleClass().add("submitStatus");

        // Make pane for scene
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);

        // Add fields to pane
        layout.add(goBackBtn, 0, 0);
        layout.add(titelString, 0, 2);

        layout.add(progressString, 0, 3);
        layout.add(progress, 0, 4);
        layout.add(progressError, 0, 5);

        layout.add(submitButton, 0, 6);
        layout.add(submitStatus, 0, 7);

        submitButton.setOnAction((event) -> {
            boolean status = true;
            String response = "";

            if (!validationController.percentageChecker(Integer.valueOf(progress.getText()))) {
                progressError.setText("Progress not valid.");
                status = false;
            } else {
                progressError.setText("");
                status = true;
            }

            if (status) {
                moduleController.Module tempModule = viewStudent.oldModule;
                tempModule.setProgress(progress.getText());

                response = moduleController.updateStudentModuleProgress(Student.oldStudent, tempModule,
                        viewStudent.currentCourse);
                submitStatus.setText(response);
                goBackBtn.setText("Go back");
            } else {
                submitStatus.setText("");
            }
        });

        root.getChildren().addAll(goBackBtn, layout);
        VBox.setMargin(goBackBtn, new Insets(15, 15, 0, 15));
        VBox.setMargin(layout, new Insets(275, 0, 0, 0));

        return root;

    }

}
