package controllers;
import java.util.ArrayList;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

/**
 * viewController
 */
public class viewController {
    public static VBox main;
    public static ArrayList<VBox> breadcrumb = new ArrayList<>();
    public static BorderPane root = new BorderPane();

    //Boundaries
    public static final int WIDTH = (int) Screen.getPrimary().getBounds().getWidth() - 30;
    public static final int HEIGHT = (int) Screen.getPrimary().getBounds().getHeight() - 30;
    
    //Set page to current page
    public static void setNode(VBox newLayout) {
        breadcrumb.add(getCurrentNode());

        main = newLayout;
        root.setTop(getCurrentNode());
    }
    //Set page to last page
    public static void setPreviousNode(VBox newLayout) {
        main = newLayout;
        root.setTop(getCurrentNode());
    }
    //Getter of current node
    public static VBox getCurrentNode() {
        return main;
    }
    //Getter of previous node
    public static void previousNode() {
        int index = breadcrumb.size()-1;
        setPreviousNode(breadcrumb.get(index));
        breadcrumb.remove(index);
    }
}