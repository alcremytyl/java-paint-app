package paint_app;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
        toolbox.setMaxHeight(scene.getHeight() / 5);
        toolbox.setAlignment(Pos.TOP_CENTER);
        toolbox.setSpacing(10);

        var tl_placeholder = new Rectangle(50, 20, Color.RED);
        var t2_placeholder = new Rectangle(toolbox.getWidth() / 4, toolbox.getHeight(), Color.PALEGREEN);
        var color_picker = Components.createColorPicker();

        toolbox.getChildren().addAll(tl_placeholder, color_picker, t2_placeholder);

        System.out.println(toolbox.getChildren());

        root.getChildren().add(toolbox);
        root.getStylesheets().add("style.css");
        root.getStyleClass().add("root-theme");

        stage.setScene(scene);
        stage.setTitle("Paint App");

        stage.show();
    }
}
