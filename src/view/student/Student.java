package view.student;

import java.util.Arrays;

import controllers.studentController;
import controllers.viewController;

import interfaces.Node;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

public class Student implements Node {
        public static studentController.Student oldStudent;
        public static ObservableList<studentController.Student> data;

        public VBox getNode() {
                // VBox
                VBox root = new VBox();
                root.setFillWidth(true);
                // HBox
                HBox buttonGrid = new HBox();
                buttonGrid.setSpacing(15);
                // Buttons
                Button addStudentBtn = new Button("Add a student");
                addStudentBtn.getStyleClass().add("addStudentBtn");

                Button refreshBtn = new Button("Refresh");
                refreshBtn.getStyleClass().add("RefreshBtn");

                buttonGrid.getChildren().addAll(addStudentBtn, refreshBtn);
                // Gridpane for scene
                GridPane layout = new GridPane();
                layout.setAlignment(Pos.CENTER);

                layout.add(buttonGrid, 0, 0);

                addStudentBtn.setOnAction(e -> {

                        addStudent addStudent = new addStudent();
                        viewController.setNode(addStudent.getNode());

                });
                // View table on refresh
                TableView<studentController.Student> table = new TableView<studentController.Student>();

                data = FXCollections.observableArrayList(studentController.getAllStudents());

                table.setItems(data);

                refreshBtn.setOnAction(e -> {
                        data = FXCollections.observableArrayList(studentController.getAllStudents());
                        table.setItems(data);
                });
                // Table boundaries
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                table.setMinWidth(Screen.getPrimary().getBounds().getWidth() - 30);

                table.setMaxHeight(842);
                // View table
                TableColumn<studentController.Student, String> email = new TableColumn<>("Email");
                email.setMinWidth(300);
                email.setMaxWidth(300);
                TableColumn<studentController.Student, String> name = new TableColumn<>("Name");
                name.setMinWidth(250);
                name.setMaxWidth(250);

                TableColumn<studentController.Student, String> dateOfBirth = new TableColumn<>("Date Of Birth");
                dateOfBirth.setStyle("-fx-alignment: CENTER;");
                TableColumn<studentController.Student, String> gender = new TableColumn<>("Gender");
                gender.setMinWidth(80);
                gender.setMaxWidth(80);
                gender.setStyle("-fx-alignment: CENTER;");
                TableColumn<studentController.Student, String> street = new TableColumn<>("Street");
                TableColumn<studentController.Student, String> houseNumber = new TableColumn<>("HouseNumber");

                houseNumber.setStyle("-fx-alignment: CENTER;");
                TableColumn<studentController.Student, String> city = new TableColumn<>("City");
                TableColumn<studentController.Student, String> country = new TableColumn<>("Country");
                TableColumn<studentController.Student, String> postalCode = new TableColumn<>("Postal Code");
                postalCode.setStyle("-fx-alignment: CENTER;");
                TableColumn<studentController.Student, studentController.Student> view = new TableColumn<>("View");

                view.setCellValueFactory(
                                param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                view.setMinWidth(80);
                view.setMaxWidth(80);
                view.setStyle("-fx-alignment: CENTER;");
                TableColumn<studentController.Student, studentController.Student> edit = new TableColumn<>("Edit");
                edit.setMinWidth(72);
                edit.setMaxWidth(72);
                edit.setStyle("-fx-alignment: CENTER;");

                edit.setCellValueFactory(
                                param -> new ReadOnlyObjectWrapper<>(param.getValue()));

                TableColumn<studentController.Student, studentController.Student> delete = new TableColumn<>("Delete");
                delete.setMinWidth(95);
                delete.setMaxWidth(95);
                delete.setStyle("-fx-alignment: CENTER;");

                delete.setCellValueFactory(
                                param -> new ReadOnlyObjectWrapper<>(param.getValue()));

                email.setCellValueFactory(
                                new PropertyValueFactory<>("Email"));
                name.setCellValueFactory(
                                new PropertyValueFactory<>("Name"));
                dateOfBirth.setCellValueFactory(
                                new PropertyValueFactory<>("DateOfBirth"));

                gender.setCellValueFactory(
                                new PropertyValueFactory<>("Gender"));
                street.setCellValueFactory(
                                new PropertyValueFactory<>("Street"));
                houseNumber.setCellValueFactory(
                                new PropertyValueFactory<>("HouseNumber"));
                city.setCellValueFactory(
                                new PropertyValueFactory<>("City"));
                postalCode.setCellValueFactory(
                                new PropertyValueFactory<>("PostalCode"));
                country.setCellValueFactory(
                                new PropertyValueFactory<>("Country"));

                // View table
                view.setCellFactory(param -> new TableCell<studentController.Student, studentController.Student>() {
                        private final Button viewStudentBtn = new Button("View");
                        // editstudentBtn.getStyleClass().add("editstudentBtn");

                        @Override
                        protected void updateItem(studentController.Student student, boolean empty) {

                                setGraphic(viewStudentBtn);
                                viewStudentBtn.setOnAction(
                                                event -> {
                                                        // assign values for placeholders in edit
                                                        oldStudent = student;

                                                        viewStudent viewStudent = new viewStudent();
                                                        viewController.setNode(viewStudent.getNode());
                                                });

                        }
                });
                // Edit table
                edit.setCellFactory(param -> new TableCell<studentController.Student, studentController.Student>() {
                        private final Button editStudentBtn = new Button("Edit");
                        // editstudentBtn.getStyleClass().add("editstudentBtn");

                        @Override
                        protected void updateItem(studentController.Student student, boolean empty) {

                                setGraphic(editStudentBtn);
                                editStudentBtn.setOnAction(
                                                event -> {
                                                        // assign values for placeholders in edit
                                                        oldStudent = student;

                                                        editStudent editStudent = new editStudent();
                                                        viewController.setNode(editStudent.getNode());
                                                });

                        }
                });
                // Delete from table
                delete.setCellFactory(param -> new TableCell<studentController.Student, studentController.Student>() {
                        private final Button deleteStudent = new Button("Delete");

                        @Override
                        protected void updateItem(studentController.Student student, boolean empty) {

                                setGraphic(deleteStudent);
                                deleteStudent.setOnAction(
                                                event -> {
                                                        studentController.deleteStudent(student);
                                                        getTableView().getItems().remove(student);

                                                });
                        }
                });

                table.setItems(data);
                table.getColumns().addAll(
                                Arrays.asList(email, name, dateOfBirth, gender, street, houseNumber, city, postalCode,
                                                country,
                                                view, edit, delete));

                table.setFixedCellSize(50);
                table.prefHeightProperty()
                                .bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(42));

                layout.add(table, 0, 1);
                layout.setVgap(15);
                VBox.setMargin(layout, new Insets(15, 15, 0, 15));

                // Add button(s)/gridpane(s) to root and return root
                root.getChildren().addAll(layout);

                return root;
        }
}