package paint_app;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * @author deck
 */
public class Components {
    static final String[][] colors = {
            {"#2E2E2E", "#7F7F7F",
                    "#FF7F7F", "#FFB3B3",
                    "#FFB366", "#FFEB99",
                    "#66B266", "#B3FFB3",
                    "#6666FF", "#B3B3FF"},
            {"#4D4D4D", "#A3A3A3",
                    "#FF4D4D", "#FF9999",
                    "#FF9900", "#FFCC66",
                    "#4D9933", "#80E080",
                    "#4D66CC", "#99CCFF"
            }
    };


    // TODO: add event listeners
    static public GridPane createColorPicker() {
        var grid = new GridPane(8, 3);
        grid.setHgap(3);
        grid.setVgap(3);

        for (int i = 0; i < Constants.COLOR_BUTTON_COUNT; i++) {
            var button = new Button();
//            var shape = ;
            button.setStyle("");

            button.setShape(new Circle(10));
            button.setMaxSize(20, 20);
            button.setMinSize(20, 20);

            grid.add(button, i / Constants.COLOR_BUTTON_ROW_COUNT, i % Constants.COLOR_BUTTON_ROW_COUNT);
        }

        return grid;
    }
}
