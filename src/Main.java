/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        var pane = new StackPane();
        var toolbox = new HBox();

        toolbox.getChildren().addAll(
            new Button("button1"),
            new Button("buton22")
        );

        pane.getChildren().addAll(toolbox);

        stage.setScene(new Scene(pane, Constants.WIDTH, Constants.WIDTH));
        stage.setTitle("Paint App");

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
