package view.enrollment;

import java.util.Arrays;

import controllers.enrollmentController;
import controllers.viewController;
import interfaces.Node;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

public class Enrollment implements Node {

        public static ObservableList<enrollmentController.Enrollment> data;

        public VBox getNode() {
                // VBox
                VBox root = new VBox();
                root.setFillWidth(true);
                // HBox
                HBox buttonGrid = new HBox();
                buttonGrid.setSpacing(15);
                // Buttons
                Button addEnrollmentBtn = new Button("Add an enrollment");
                addEnrollmentBtn.getStyleClass().add("addEnrollmentBtn");

                Button refreshBtn = new Button("Refresh");
                refreshBtn.getStyleClass().add("RefreshBtn");

                buttonGrid.getChildren().addAll(addEnrollmentBtn, refreshBtn);
                // Gridpane for scene
                GridPane layout = new GridPane();
                layout.setAlignment(Pos.CENTER);

                layout.add(buttonGrid, 0, 0);

                addEnrollmentBtn.setOnAction(e -> {
                        addEnrollment addEnrollment = new addEnrollment();
                        viewController.setNode(addEnrollment.getNode());
                });
                // View table on refresh
                TableView<enrollmentController.Enrollment> table = new TableView<enrollmentController.Enrollment>();

                data = FXCollections.observableArrayList(enrollmentController.getAllEnrollments());

                table.setItems(data);

                refreshBtn.setOnAction(e -> {
                        data = FXCollections.observableArrayList(enrollmentController.getAllEnrollments());
                        table.setItems(data);
                });
                // Table boundaries
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                table.setMinWidth(Screen.getPrimary().getBounds().getWidth() - 30);
                table.setMaxHeight(842);

                // View table
                TableColumn<enrollmentController.Enrollment, String> courseName = new TableColumn<>("Course Name ");
                TableColumn<enrollmentController.Enrollment, String> email = new TableColumn<>("Email ");
                TableColumn<enrollmentController.Enrollment, String> EnrollmentDate = new TableColumn<>(
                                "Enrollment Date ");
                TableColumn<enrollmentController.Enrollment, Integer> certificateID = new TableColumn<>(
                                "Certificate ID");

                courseName.setCellValueFactory(
                                new PropertyValueFactory<>("CourseName"));
                email.setCellValueFactory(
                                new PropertyValueFactory<>("Email"));
                EnrollmentDate.setCellValueFactory(
                                new PropertyValueFactory<>("EnrollmentDate"));
                certificateID.setCellValueFactory(
                                new PropertyValueFactory<>("CertificateID"));

                table.setItems(data);
                table.getColumns().addAll(Arrays.asList(courseName, email, EnrollmentDate, certificateID));

                table.setFixedCellSize(50);
                table.prefHeightProperty()
                                .bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(42));

                layout.add(table, 0, 1);

                // Add button(s)/gridpane(s) to root and return root
                root.getChildren().addAll(layout);

                layout.setVgap(15);
                VBox.setMargin(layout, new Insets(15, 15, 0, 15));

                return root;

        }
}