package paint_app;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        var pane = new StackPane();
        var toolbox = new HBox();
        var gp = Components.createColorPicker();

        toolbox.getChildren().addAll(gp);

        pane.getChildren().addAll(toolbox);

        stage.setScene(new Scene(pane, Constants.WIDTH, Constants.WIDTH));
        stage.setTitle("Paint App");

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
