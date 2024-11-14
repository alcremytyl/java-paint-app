package paint_app;

import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * @author deck
 */
public class Components {
    static final String[] colors = {
            "#2E2E2E", "#7F7F7F",
            "#FF7F7F", "#FFB3B3",
            "#FFB366", "#FFEB99",
            "#66B266", "#B3FFB3",
            "#6666FF", "#B3B3FF",
            "#4D4D4D", "#A3A3A3",
            "#FF4D4D", "#FF9999",
            "#FF9900", "#FFCC66",
            "#4D9933", "#80E080",
            "#4D66CC", "#99CCFF"
    };


    // TODO: add event listeners
    static public GridPane createColorPicker() {
        var grid = new GridPane();
//        grid.setHgap(15);
//        grid.setVgap(15);
        grid.getStyleClass().add("color-picker-grid");

        for (int i = 0; i < 10; i++)
            grid.getColumnConstraints().add(new ColumnConstraints(20));

        for (int i = 0; i < 2; i++)
            grid.getRowConstraints().add(new RowConstraints(20));

        for (int i = 0; i < colors.length; i++) {
            var button = new Button();
            button.getStyleClass().add("color-picker-button");
            button.setStyle("-fx-background-color: " + colors[i] + ";");
            grid.add(button, i / 2, i % 2);

        }

        return grid;
    }
}
