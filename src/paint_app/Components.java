package paint_app;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
            "#4D4D4D", "#A3A3A3",
            "#FF4D4D", "#FF9999",
            "#FF7F7F", "#FFB3B3",
            "#FFB366", "#FFEB99",
            "#FF9900", "#FFCC66",
            "#4D9933", "#80E080",
            "#66B266", "#B3FFB3",
            "#6666FF", "#B3B3FF",
            "#4D66CC", "#99CCFF"
    };
    static final String DARK_STROKE = "#181926";
    static final String BRIGHT_STROKE = "#cad3f5";


    // TODO: add event listeners
    static public GridPane createColorPicker() {
        var grid = new GridPane();
        grid.getStyleClass().add("color-picker-grid");

//        for (int i = 0; i < 10; i++) {
//            var constraint = new ColumnConstraints(25);
//            constraint.setHgrow(Priority.NEVER);
//            constraint.setPercentWidth(0);
//            grid.getColumnConstraints().add(constraint);
//        }
//        for (int i = 0; i < 2; i++) {
//            var constraint = new RowConstraints(25);
//            constraint.setPercentHeight(0);
//            grid.getRowConstraints().add(constraint);
//        }

        for (int i = 0; i < colors.length; i++) {
            final var color = Color.web(colors[i]);
            final var circle_stroke = Color.web(color.getBrightness() >= 0.5 ? DARK_STROKE : BRIGHT_STROKE);

            var circle = new Circle(12, color);
            circle.setStroke(circle_stroke);
            circle.setStrokeWidth(2);

            grid.add(circle, i / 2, i % 2);
        }

        return grid;
    }
}