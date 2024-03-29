package view.certificate;

import controllers.certificateController;
import controllers.employeeController;
import controllers.validationController;
import controllers.viewController;
import interfaces.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
// import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class editCertificate implements Node {

    public VBox getNode() {
        //VBox
        VBox root = new VBox();
        root.setFillWidth(true);

        // Go back button
        Button goBackBtn = new Button("Cancel");
        goBackBtn.setId("goBackBtn");

        goBackBtn.setOnAction(event -> {
            viewController.previousNode();
        });

        // student fullname to: firstname's
        String firstName = certificateController.getNameByEmail(Certificate.oldCertificate).split(" ")[0]
                + "'s";

        // title
        Label instructionText = new Label(
                "Grading " + firstName
                        + " certificate");
        instructionText.setId("headerTitle");

        // Course-Student information
        String infoString = String.format("Certificate ID: %d\nCourse name: %s\nEmail: %s\nName: %s",
                Certificate.oldCertificate.getCertificateID(), Certificate.oldCertificate.getCourseName(),
                Certificate.oldCertificate.getEmail(),
                certificateController.getNameByEmail(Certificate.oldCertificate));

        Label information = new Label(infoString);

        // grade label
        Label gradeString = new Label("Grade:");
        gradeString.getStyleClass().add("labelText");

        // grade textfield
        TextField grade = new TextField();

        if (!Certificate.oldCertificate.getGrade().equals("Not graded")) {
            grade.setText(String.valueOf(Certificate.oldCertificate.getGrade()));
        }

        grade.getStyleClass().add("cstmTextfield");

        // grade error
        Label gradeError = new Label("");
        gradeError.getStyleClass().add("errorMsg");

        // employee label
        Label employeeString = new Label("Your name:");
        employeeString.getStyleClass().add("labelText");

        // employee textfield
        ObservableList<employeeController.Employee> allEmployees = FXCollections
                .observableArrayList(employeeController.getAllEmployees());

        ComboBox<employeeController.Employee> employee = new ComboBox<>();
        employee.getItems().addAll(allEmployees);

        if (Certificate.oldCertificate.getEmployee() != null) {
            employee.setValue(Certificate.oldCertificate.getEmployee());
        }

        // employee errorMsg
        Label employeeError = new Label("");
        employeeError.getStyleClass().add("errorMsg");

        // submit button
        Button submitButton = new Button("GRADE CERTIFICATE");
        submitButton.getStyleClass().add("gradeSubmitBtn");

        // submit status
        Label submitStatus = new Label("");
        submitStatus.getStyleClass().add("submitStatus");

        // Gridpane for scene
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);

        // Add fields to pane
        layout.add(instructionText, 0, 0);
        layout.add(information, 0, 1);

        layout.add(gradeString, 0, 3);
        layout.add(grade, 0, 4);
        layout.add(gradeError, 0, 5);

        layout.add(employeeString, 0, 6);
        layout.add(employee, 0, 7);
        layout.add(employeeError, 0, 8);

        layout.add(submitButton, 0, 9);
        layout.add(submitStatus, 0, 10);

        submitButton.setOnAction((event) -> {
            boolean status = false;
            String response = "";

            // Validate input fields

            if (!validationController.gradeChecker(grade.getText())) {
                gradeError.setText("Grade not valid.");
                status = false;
            } else {
                gradeError.setText("");
                status = true;
            }

            if (employee.getValue() == null) {
                employeeError.setText("Employee not valid.");
                status = false;
            } else {
                employeeError.setText("");
                status = true;
            }

            if (status) {

                certificateController.Certificate updatedCertificate = Certificate.oldCertificate;

                updatedCertificate.setGrade(grade.getText());
                updatedCertificate.setEmployeeName(employee.getValue());

                response = certificateController.editCertificate(updatedCertificate);
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