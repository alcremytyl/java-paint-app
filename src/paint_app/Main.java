package paint_app;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/* TODO
 * base UI off this
 * https://catppuccin.com/palette#Macchiato
 *
 * add brush
 */


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        var root = new StackPane();
        var scene = new Scene(root, Constants.WIDTH, Constants.HEIGHT);

        var toolbox = new HBox();

        toolbox.getChildren().addAll(Components.createColorPicker());

        root.getChildren().add(toolbox);
        root.getStylesheets().add("style.css");

        stage.setScene(scene);
        stage.setTitle("Paint App");

        stage.show();
    }
}
