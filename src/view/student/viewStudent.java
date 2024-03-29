package view.student;

import java.util.ArrayList;
import java.util.Arrays;

import controllers.certificateController;
import controllers.courseController;
import controllers.moduleController;

import controllers.viewController;
import controllers.webcastController;
import controllers.certificateController.Certificate;
import controllers.moduleController.Module;
import controllers.webcastController.Webcast;
import interfaces.Node;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

public class viewStudent implements Node {

        public static ObservableList<Module> dataModule;
        public static ObservableList<Webcast> dataWebcast;

        public static moduleController.Module oldModule;
        public static courseController.Course currentCourse;

        @Override
        public VBox getNode() throws IllegalArgumentException {
                // parent wrapper
                VBox root = new VBox();
                // set fullscreen width
                root.setFillWidth(true);

                // Button that returns to previous node
                Button goBackBtn = new Button("Go back");
                goBackBtn.setId("goBackBtn");

                // onclick goBackBtn return to previous node
                goBackBtn.setOnAction(event -> {
                        viewController.previousNode();
                });

                Button refreshBtn = new Button("Refresh");
                refreshBtn.getStyleClass().add("RefreshBtn");

                HBox topBtnGrid = new HBox();
                topBtnGrid.getChildren().addAll(goBackBtn, refreshBtn);
                topBtnGrid.setSpacing(15);

                // gridpane that will hold all of this page's content
                GridPane layout = new GridPane();
                // give layout padding so it wont touch borders of the application
                layout.setPadding(new Insets(10, 10, 10, 10));

                // ====================================================================================================================

                // observablelist with all linked courses
                ObservableList<courseController.Course> allLinkedCourses = FXCollections
                                .observableArrayList(courseController.getAllLinkedStudentCourses(Student.oldStudent));
                // combobox with all courses linked to student
                ComboBox<courseController.Course> courses = new ComboBox<>();
                courses.getItems().addAll(allLinkedCourses);
                courses.setPromptText("Select course");

                TableView<moduleController.Module> table = new TableView<moduleController.Module>();
                TableView<webcastController.Webcast> table2 = new TableView<webcastController.Webcast>();

                refreshBtn.setOnAction(e -> {

                        if (courses.getValue() != null) {
                                dataModule = FXCollections.observableArrayList(
                                                moduleController.getAllLinkedModulesBasedOnStudents(courses.getValue(),
                                                                Student.oldStudent));
                                table.setItems(dataModule);
                                table.prefHeightProperty()
                                                .bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize())
                                                                .add(42));

                        }

                });

                table2.setVisible(false);
                table.setVisible(false);
                table.setMaxHeight(492);

                Button showModulesBtn = new Button("Show modules");

                showModulesBtn.setOnAction(e -> {
                        table2.setVisible(false);
                        table.setVisible(true);

                        dataModule = FXCollections.observableArrayList(
                                        moduleController.getAllLinkedModulesBasedOnStudents(courses.getValue(),
                                                        Student.oldStudent));
                        table.setItems(dataModule);
                        table.prefHeightProperty()
                                        .bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()));

                });

                layout.add(table, 0, 3);
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                table.setMinWidth(Screen.getPrimary().getBounds().getWidth() - 30);

                TableColumn<moduleController.Module, String> title = new TableColumn<>("Title");
                TableColumn<moduleController.Module, String> version = new TableColumn<>("Version");
                TableColumn<moduleController.Module, String> description = new TableColumn<>("Description");
                TableColumn<moduleController.Module, String> contactPerson = new TableColumn<>("ContactPerson");
                TableColumn<moduleController.Module, String> progress = new TableColumn<>("Progress");
                TableColumn<moduleController.Module, moduleController.Module> edit = new TableColumn<>("Edit");
                edit.setMinWidth(72);
                edit.setMaxWidth(72);
                edit.setStyle("-fx-alignment: CENTER;");

                edit.setCellValueFactory(
                                param -> new ReadOnlyObjectWrapper<>(param.getValue()));

                title.setCellValueFactory(
                                new PropertyValueFactory<>("Title"));
                version.setCellValueFactory(
                                new PropertyValueFactory<>("Version"));
                description.setCellValueFactory(
                                new PropertyValueFactory<>("Description"));

                contactPerson.setCellValueFactory(
                                new PropertyValueFactory<>("ContactPerson"));
                progress.setCellValueFactory(
                                new PropertyValueFactory<>("Progress"));

                edit.setCellFactory(param -> new TableCell<moduleController.Module, moduleController.Module>() {
                        private final Button editMomentBtn = new Button("Edit");

                        @Override
                        protected void updateItem(moduleController.Module module, boolean empty) {

                                setGraphic(editMomentBtn);
                                editMomentBtn.setOnAction(
                                                event -> {
                                                        oldModule = module;
                                                        currentCourse = courses.getValue();

                                                        editStudentModule editStudentModule = new editStudentModule();
                                                        viewController.setNode(editStudentModule.getNode());
                                                });

                        }
                });

                // table.setItems(data);
                table.getColumns().addAll(Arrays.asList(title, version, description, contactPerson, progress, edit));

                table.setFixedCellSize(50);

                // ========================================================================================================================

                // observablelist with all linked webcasts
                Button showWebcastBtn = new Button("Show webcasts");
                showWebcastBtn.getStyleClass().add("showWebcastBtn");

                showWebcastBtn.setOnAction(e -> {

                        table.setVisible(false);
                        table2.setVisible(true);

                        dataWebcast = FXCollections.observableArrayList(
                                        webcastController.getAllStudentWebcasts(Student.oldStudent));

                        table2.setItems(dataWebcast);
                        table2.prefHeightProperty()
                                        .bind(Bindings.size(table2.getItems())
                                                        .multiply(table2.getFixedCellSize())
                                                        .add(42));

                });

                layout.add(table2, 0, 3);
                table2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                table2.setMinWidth(Screen.getPrimary().getBounds().getWidth() - 30);

                TableColumn<webcastController.Webcast, String> titleWebast = new TableColumn<>("Title");
                TableColumn<webcastController.Webcast, String> descriptionWebast = new TableColumn<>("Description");
                TableColumn<webcastController.Webcast, String> duration = new TableColumn<>("Duration");
                TableColumn<webcastController.Webcast, String> speakerName = new TableColumn<>("SpeakerName");
                TableColumn<webcastController.Webcast, String> url = new TableColumn<>("URL");
                TableColumn<webcastController.Webcast, String> views = new TableColumn<>("Views");
                TableColumn<webcastController.Webcast, String> progressWebcast = new TableColumn<>("Progress");

                titleWebast.setCellValueFactory(
                                new PropertyValueFactory<>("Title"));
                descriptionWebast.setCellValueFactory(
                                new PropertyValueFactory<>("Description"));
                duration.setCellValueFactory(
                                new PropertyValueFactory<>("Duration"));

                speakerName.setCellValueFactory(
                                new PropertyValueFactory<>("SpeakerName"));
                url.setCellValueFactory(
                                new PropertyValueFactory<>("URL"));
                views.setCellValueFactory(
                                new PropertyValueFactory<>("Views"));
                progressWebcast.setCellValueFactory(
                                new PropertyValueFactory<>("Progress"));

                table2.getColumns()
                                .addAll(Arrays.asList(titleWebast, descriptionWebast, duration, speakerName, url, views,
                                                progressWebcast));

                table2.setFixedCellSize(50);
                // ====================================================================================================================

                HBox tableBtnGrid = new HBox();
                tableBtnGrid.getChildren().addAll(courses, showModulesBtn, showWebcastBtn);
                tableBtnGrid.setSpacing(15);

                String studentInfo = String.format(
                                "Name: %s\nEmail: %s\nDate of Birth: %s\nGender: %s\nCity: %s\nAddress: %s %s\nPostal Code: %s\nCountry: %s",
                                Student.oldStudent.getName(), Student.oldStudent.getEmail(),
                                Student.oldStudent.getDateOfBirth(), Student.oldStudent.getGender(),
                                Student.oldStudent.getCity(), Student.oldStudent.getStreet(),
                                Student.oldStudent.getHouseNumber(), Student.oldStudent.getPostalCode(),
                                Student.oldStudent.getCountry());
                Label studentInfoLabel = new Label(studentInfo);

                String allCertificates = "All Certificates:\n";

                if (certificateController.getStudentdCertificates(Student.oldStudent).size() == 0) {
                        allCertificates += "No graded certificates yet.";
                } else {
                        ArrayList<Certificate> certficates = certificateController
                                        .getStudentdCertificates(Student.oldStudent);

                        for (Certificate c : certficates) {
                                allCertificates = allCertificates + c.getCourseName() + " - " + c.getGrade() + "\n";
                        }
                }

                Label certificatesLabel = new Label(allCertificates);

                HBox studentInfoHolder = new HBox();
                studentInfoHolder.getChildren().addAll(studentInfoLabel, certificatesLabel);
                HBox.setMargin(studentInfoLabel, new Insets(0, 200, 0, 0));

                layout.add(studentInfoHolder, 0, 1);

                layout.add(tableBtnGrid, 0, 2);
                layout.setVgap(15);

                // ====================================================================================================================

                // add all panes to parent wrapper
                root.getChildren().addAll(topBtnGrid, layout);
                // add margin to make it look better
                VBox.setMargin(topBtnGrid, new Insets(15, 15, 0, 15));
                VBox.setMargin(layout, new Insets(0, 5, 15, 5));
                // return parent wrapper
                return root;
        }

}
