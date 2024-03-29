package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Homepage {

    public VBox getNode() {
        //VBox 
        VBox root = new VBox();
        root.setFillWidth(true);
        //Label for scene
        Label authorLabel = new Label(
                "Made by: Dhani Taelman");
        authorLabel.getStyleClass().add("authorLabel");
        //Borderpane for scene
        BorderPane authorLabelHolder = new BorderPane();
        authorLabelHolder.getChildren().add(authorLabel);
        //Gridpane for scene
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);

        //Codecademy label
        GridPane codecademy = new GridPane();

        Label welcomeLabelCode = new Label("code");
        Label welcomeLabelC = new Label("c");
        Label welcomeLabelAdemy = new Label("ademy");

        //Styling
        welcomeLabelCode.getStyleClass().add("homeTitleCode");
        welcomeLabelC.getStyleClass().add("homeTitleC");
        welcomeLabelAdemy.getStyleClass().add("homeTitleAdemy");

        codecademy.setHgap(-3);
        
        //Slogan label
        Label slogan = new Label("Come help us build the education the world deserves");

        codecademy.add(welcomeLabelCode, 0, 0);
        codecademy.add(welcomeLabelC, 1, 0);
        codecademy.add(welcomeLabelAdemy, 2, 0);

        layout.setVgap(10);

        layout.add(codecademy, 0, 0);
        layout.add(slogan, 0, 1);


        //Add button(s)/gridpane(s) to root and return root
        root.getChildren().addAll(authorLabel, layout);
        VBox.setMargin(authorLabel, new Insets(15, 15, 0, 15));
        VBox.setMargin(layout, new Insets(275, 0, 0, 0));

        return root;
    }
}