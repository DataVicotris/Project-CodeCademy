package templates;

// Controllers
import controllers.viewController;
// Views

import view.Homepage;
import view.Statistics;
import view.course.Course;
import view.student.Student;
import view.certificate.Certificate;
import view.enrollment.Enrollment;

// Packages
import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class template extends Application {

    @Override
    public void start(Stage window) throws Exception {
       
        VBox root = new VBox();
        root.setFillWidth(true);

        // Add Vbox root to scene
        Scene scene = new Scene(root, viewController.WIDTH, viewController.HEIGHT);

        // Buttons
        Button homeBtn = new Button("Home");
        homeBtn.getStyleClass().add("nav-item");

        Button courseBtn = new Button("Courses");
        courseBtn.getStyleClass().add("nav-item");

        Button studentBtn = new Button("Students");
        studentBtn.getStyleClass().add("nav-item");

        Button certificateBtn = new Button("Certificates");
        certificateBtn.getStyleClass().add("nav-item");

        Button enrollmentBtn = new Button("Enrollments");
        enrollmentBtn.getStyleClass().add("nav-item");

        Button statisticsBtn = new Button("Statistics");
        statisticsBtn.getStyleClass().add("nav-item");

        // NavBar
        HBox navbar = new HBox();
        navbar.setId("nav");
        navbar.getChildren().addAll(homeBtn, courseBtn, studentBtn, enrollmentBtn, certificateBtn, statisticsBtn);
        navbar.setPrefHeight(53);
        navbar.setSpacing(15);
        navbar.setAlignment(Pos.CENTER_LEFT);

        root.getChildren().addAll(navbar, viewController.root);

        // Default view and button actions
        Homepage homepage = new Homepage();
        viewController.setNode(homepage.getNode());
        viewController.root.setTop(viewController.getCurrentNode());

        homeBtn.setOnAction(e -> {
            viewController.setNode(homepage.getNode());
        });

        courseBtn.setOnAction(e -> {
            Course course = new Course();
            viewController.setNode(course.getNode());
        });

        studentBtn.setOnAction(e -> {
            Student student = new Student();
            viewController.setNode(student.getNode());
        });

        certificateBtn.setOnAction(e -> {
            Certificate certificate = new Certificate();
            viewController.setNode(certificate.getNode());
        });

        enrollmentBtn.setOnAction(e -> {
            Enrollment enrollment = new Enrollment();
            viewController.setNode(enrollment.getNode());
        });

        statisticsBtn.setOnAction(e -> {
            Statistics statistics = new Statistics();
            viewController.setNode(statistics.getNode());
        });

        scene.getStylesheets().add("style.css");

        window.setMaximized(true);
        window.setScene(scene);
        window.show();
    }
}