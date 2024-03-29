package view.module;

import controllers.moduleController;
import controllers.viewController;
import interfaces.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class addModule implements Node {

    @Override
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
        Label instructionText = new Label("Add a module to " + view.course.Course.thisCourse.getCourseName());
        instructionText.setId("headerTitle");

        // coursname label
        Label courseNameString = new Label("Course name:");
        courseNameString.getStyleClass().add("labelText");

        // coursename textfield
        TextField courseName = new TextField();
        courseName.getStyleClass().add("cstmTextfield");
        courseName.setText(view.course.Course.thisCourse.getCourseName());
        courseName.setEditable(false);

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
        Button submitButton = new Button("ADD MODULE");
        submitButton.getStyleClass().add("submitBtn");

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

        layout.add(moduleString, 0, 5);
        layout.add(module, 0, 6);
        layout.add(moduleError, 0, 7);

        layout.add(submitButton, 0, 8);
        layout.add(submitStatus, 0, 9);

        submitButton.setOnAction((event) -> {
            boolean status = true;
            String response = "";
            
            if (module.getValue() == null) {
                status = false;
            }

            if (status) {
                response =moduleController.addModuleToCourse(view.course.Course.thisCourse, module.getValue());
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
