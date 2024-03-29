package view.course;

import java.util.Arrays;

import controllers.courseController;
import controllers.viewController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Course {

    public static courseController.Course thisCourse;
    public static ObservableList<courseController.Course> data;

    public VBox getNode() {
        // Vbox
        VBox root = new VBox();
        root.setFillWidth(true);
        // Hbox
        HBox buttonGrid = new HBox();
        buttonGrid.setSpacing(15);

        // Buttons
        Button addCourseBtn = new Button("Add a course");
        addCourseBtn.getStyleClass().add("addCourseBtn");

        Button refreshBtn = new Button("Refresh");
        refreshBtn.getStyleClass().add("RefreshBtn");

        buttonGrid.getChildren().addAll(addCourseBtn, refreshBtn);
        // Gridpane for scene
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);

        layout.add(buttonGrid, 0, 0);

        addCourseBtn.setOnAction(e -> {
            addCourse addCourse = new addCourse();
            viewController.setNode(addCourse.getNode());
        });
        // View table on refresh
        TableView<courseController.Course> table = new TableView<courseController.Course>();

        data = FXCollections.observableArrayList(courseController.getAllCourses());

        table.setItems(data);

        refreshBtn.setOnAction(e -> {
            data = FXCollections.observableArrayList(courseController.getAllCourses());
            table.setItems(data);
            table.setFixedCellSize(50);
            table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(42));
        });
        // Table boundaries
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMaxHeight(842);

        table.setMinWidth(viewController.WIDTH);
        // Show table
        TableColumn<courseController.Course, String> courseName = new TableColumn<>("Name");
        TableColumn<courseController.Course, String> subject = new TableColumn<>("Subject");
        TableColumn<courseController.Course, String> introText = new TableColumn<>("IntroText");
        TableColumn<courseController.Course, String> level = new TableColumn<>("Level");
        TableColumn<courseController.Course, Double> progress = new TableColumn<>("Average progress");
        TableColumn<courseController.Course, courseController.Course> view = new TableColumn<>("View");
        view.setMinWidth(80);
        view.setMaxWidth(80);

        view.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(
                param.getValue()));

        TableColumn<courseController.Course, courseController.Course> edit = new TableColumn<>("Edit");
        edit.setMinWidth(72);
        edit.setMaxWidth(72);

        edit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(
                param.getValue()));

        TableColumn<courseController.Course, courseController.Course> delete = new TableColumn<>("Delete");
        delete.setMinWidth(95);
        delete.setMaxWidth(95);

        delete.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue()));

        courseName.setCellValueFactory(
                new PropertyValueFactory<>("courseName"));
        subject.setCellValueFactory(
                new PropertyValueFactory<>("Subject"));
        introText.setCellValueFactory(
                new PropertyValueFactory<>("IntroText"));
        level.setCellValueFactory(
                new PropertyValueFactory<>("Level"));
        progress.setCellValueFactory(
                new PropertyValueFactory<>("avgProgress"));

        // View table
        view.setCellFactory(param -> new TableCell<courseController.Course, courseController.Course>() {
            private final Button viewAllModulesBtn = new Button("View");

            @Override
            protected void updateItem(courseController.Course course, boolean empty) {

                setGraphic(viewAllModulesBtn);
                viewAllModulesBtn.setOnAction(
                        event -> {
                            thisCourse = course;

                            viewCourse viewCourse = new viewCourse();
                            viewController.setNode(viewCourse.getNode());
                        });
            }
        });

        // Edit table
        edit.setCellFactory(param -> new TableCell<courseController.Course, courseController.Course>() {
            private final Button editCourseBtn = new Button("Edit");
            // editCourseBtn.getStyleClass().add("editCourseBtn");

            @Override
            protected void updateItem(courseController.Course course, boolean empty) {

                setGraphic(editCourseBtn);
                editCourseBtn.setOnAction(
                        event -> {
                            thisCourse = course;

                            editCourse editCourse = new editCourse();
                            viewController.setNode(editCourse.getNode());
                        });
            }
        });

        // Delete from table
        delete.setCellFactory(param -> new TableCell<courseController.Course, courseController.Course>() {
            private final Button deleteCourseBtn = new Button("Delete");

            @Override
            protected void updateItem(courseController.Course course, boolean empty) {

                setGraphic(deleteCourseBtn);
                deleteCourseBtn.setOnAction(
                        event -> {
                            String result = courseController.deleteCourse(course);

                            if (result.equals("Course successfully deleted.")) {
                                getTableView().getItems().remove(course);
                            } else {
                                Alert alert = new Alert(AlertType.WARNING);
                                alert.setTitle("CodeCademy");
                                alert.setHeaderText("Could not delete course because it is linked to one or more modules or 1 or more students are enrolled in this course.");
                                alert.showAndWait();
                            }
                        });
            }
        });

        table.getColumns().addAll(Arrays.asList(courseName, subject, introText, level, progress, view, edit, delete));

        table.setFixedCellSize(50);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(42));

        layout.add(table, 0, 1);
        layout.setVgap(15);
        VBox.setMargin(layout, new Insets(15, 15, 0, 15));

        // Add button(s)/gridpane(s) to root and return root
        root.getChildren().addAll(layout);
        return root;
    }
}