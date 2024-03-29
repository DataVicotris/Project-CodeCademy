package view.certificate;

import java.util.Arrays;

import controllers.certificateController;
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

public class Certificate implements Node {

        public static certificateController.Certificate oldCertificate;
        public static ObservableList<certificateController.Certificate> data;

        public VBox getNode() {
                // VBox
                VBox root = new VBox();
                root.setFillWidth(true);
                // Gridpane for scene
                GridPane layout = new GridPane();
                layout.setAlignment(Pos.CENTER);
                // HBox
                HBox buttonGrid = new HBox();
                buttonGrid.setSpacing(15);
                // Button
                Button refreshBtn = new Button("Refresh");
                refreshBtn.getStyleClass().add("RefreshBtn");

                buttonGrid.getChildren().addAll(refreshBtn);

                layout.add(buttonGrid, 0, 0);
                // Refresh table
                TableView<certificateController.Certificate> table = new TableView<certificateController.Certificate>();

                data = FXCollections.observableArrayList(certificateController.getAllCertificates());

                table.setItems(data);

                refreshBtn.setOnAction(e -> {
                        data = FXCollections.observableArrayList(certificateController.getAllCertificates());
                        table.setItems(data);
                });

                // Table boundaries
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                table.setMinWidth(Screen.getPrimary().getBounds().getWidth() - 30);

                // Show table columns
                TableColumn<certificateController.Certificate, String> certificateID = new TableColumn<>(
                                "Certificate ID");
                TableColumn<certificateController.Certificate, String> courseName = new TableColumn<>("Course Name");
                TableColumn<certificateController.Certificate, String> email = new TableColumn<>("Student");
                TableColumn<certificateController.Certificate, String> grade = new TableColumn<>("Grade");
                TableColumn<certificateController.Certificate, String> employeeName = new TableColumn<>(
                                "Employee Name");
                TableColumn<certificateController.Certificate, certificateController.Certificate> edit = new TableColumn<>(
                                "Edit");
                edit.setMinWidth(72);
                edit.setMaxWidth(72);
                table.setMaxHeight(842);

                edit.setCellValueFactory(
                                param -> new ReadOnlyObjectWrapper<>(param.getValue()));

                certificateID.setCellValueFactory(
                                new PropertyValueFactory<>("certificateID"));
                courseName.setCellValueFactory(
                                new PropertyValueFactory<>("CourseName"));
                email.setCellValueFactory(
                                new PropertyValueFactory<>("Email"));
                grade.setCellValueFactory(
                                new PropertyValueFactory<>("Grade"));
                employeeName.setCellValueFactory(
                                new PropertyValueFactory<>("EmployeeName"));

                edit.setCellFactory(
                                param -> new TableCell<certificateController.Certificate, certificateController.Certificate>() {
                                        private final Button editCertificateBtn = new Button("Edit");
                                        // editCertificateBtn.getStyleClass().add("editCertificateBtn");

                                        @Override
                                        protected void updateItem(certificateController.Certificate certificate,
                                                        boolean empty) {

                                                setGraphic(editCertificateBtn);
                                                editCertificateBtn.setOnAction(
                                                                event -> {

                                                                        oldCertificate = certificate;

                                                                        editCertificate editCertificate = new editCertificate();
                                                                        viewController.setNode(
                                                                                        editCertificate.getNode());
                                                                });
                                        }
                                });

                table.setItems(data);
                table.getColumns().addAll(Arrays.asList(certificateID, courseName,
                                email, grade, employeeName, edit));

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