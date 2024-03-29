package view.course;

import java.util.ArrayList;
import java.util.Arrays;

import controllers.courseController;
import controllers.moduleController;
import controllers.viewController;
import interfaces.Node;
import javafx.beans.binding.Bindings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.module.addModule;

public class viewCourse implements Node {

    public static ObservableList<moduleController.Module> data;

    @Override
    public VBox getNode() {
        //VBox
        VBox root = new VBox();
        root.setFillWidth(true);

        // Go back
        Button goBackBtn = new Button("Go back");
        goBackBtn.setId("goBackBtn");

        goBackBtn.setOnAction(event -> {
            viewController.previousNode();
        });
        //HBox
        HBox buttonGrid = new HBox();
        buttonGrid.setSpacing(15);
        //Buttons
        Button addModuleToCourseBtn = new Button("Add a module to course");
        addModuleToCourseBtn.getStyleClass().add("addCourseBtn");

        Button refreshBtn = new Button("Refresh");
        refreshBtn.getStyleClass().add("RefreshBtn");

        buttonGrid.getChildren().addAll(addModuleToCourseBtn, refreshBtn);
        //Gridpane for scene
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);

        layout.add(buttonGrid, 0, 0);

        addModuleToCourseBtn.setOnAction(e -> {
            addModule addModule = new addModule();
            viewController.setNode(addModule.getNode());
        });

        ArrayList<courseController.Course> courseList = courseController
                .getCourseBasedOnName(Course.thisCourse);
        courseController.Course displayCourse = courseList.get(0);

        HBox course = new HBox();
        VBox courseInfo = new VBox();
        VBox courseInfo2 = new VBox();

        courseInfo.getChildren().addAll(
                new Label("Course: " + displayCourse.getCourseName()),
                new Label("Subject: " + displayCourse.getSubject()),
                new Label("Introduction: " + displayCourse.getIntroText()));
        courseInfo.setSpacing(15);

        ArrayList<courseController.Course> suggestedCourses = courseController.getSuggestedCourses(Course.thisCourse);

        StringBuilder suggestedCoursesLabel = new StringBuilder();

        for (courseController.Course suggestedCourse : suggestedCourses) {
            suggestedCoursesLabel.append("   - " + suggestedCourse.getCourseName() + "\n");
        }

        courseInfo2.getChildren().addAll(
                new Label("Level: " + displayCourse.getLevel()),
                new Label("Graded students: " +  courseController.getGradedStudentsBasedOnCourse(Course.thisCourse)),
                new Label("Suggested courses:"),
                new Label(suggestedCoursesLabel.toString()));
        // courseInfo2.setSpacing(15);

        course.getChildren().addAll(courseInfo, courseInfo2);
        course.setSpacing(250);

        HBox.setMargin(courseInfo, new Insets(15, 0, 15, 0));
        HBox.setMargin(courseInfo2, new Insets(15, 0, 15, 0));

        layout.add(course, 0, 1);
        //View table with refresh
        TableView<moduleController.Module> table = new TableView<moduleController.Module>();

        data = FXCollections
                .observableArrayList(moduleController
                        .getAllModulesBasedOnCourse(view.course.Course.thisCourse));

        table.setItems(data);

        refreshBtn.setOnAction(e -> {
            data = FXCollections
                    .observableArrayList(moduleController
                            .getAllModulesBasedOnCourse(view.course.Course.thisCourse));
            table.setItems(data);
            table.setFixedCellSize(50);
            table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(42));
        });
        //Table boundaries
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMinWidth(viewController.WIDTH);
        //Show table
        TableColumn<moduleController.Module, String> id = new TableColumn<>("ID");
        TableColumn<moduleController.Module, String> title = new TableColumn<>("Title");
        TableColumn<moduleController.Module, String> description = new TableColumn<>("Description");
        TableColumn<moduleController.Module, String> contactPerson = new TableColumn<>("Contact");
        TableColumn<moduleController.Module, String> progress = new TableColumn<>("Average progress");

        id.setCellValueFactory(
                new PropertyValueFactory<>("TrackingNumber"));
        title.setCellValueFactory(
                new PropertyValueFactory<>("Title"));
        description.setCellValueFactory(
                new PropertyValueFactory<>("Description"));
        contactPerson.setCellValueFactory(
                new PropertyValueFactory<>("ContactPerson"));
        contactPerson.setCellValueFactory(
                new PropertyValueFactory<>("ContactPerson"));
        progress.setCellValueFactory(
                new PropertyValueFactory<>("Progress"));

        table.getColumns()
                .addAll(Arrays.asList(id, title, description, contactPerson, progress));

        table.setFixedCellSize(50);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(42));

        layout.add(table, 0, 2);

        //Add button(s)/gridpane(s) to root and return root
        root.getChildren().addAll(goBackBtn, layout);
        VBox.setMargin(goBackBtn, new Insets(15, 15, 0, 15));
        VBox.setMargin(layout, new Insets(15, 15, 0, 15));

        return root;
    }

}
