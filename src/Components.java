
import javafx.scene.layout.GridPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author deck
 */
public class Components {

    String[][] colors = {
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

    // Java 13+ feature
    String style_format = """
        -fx-background-color: %s;
        -fx-shape: 'M 0,10 A 10,10 0 1,1 20,10 A 10,10 0 1,1 0,10 Z';" +
        -fx-padding: 0; -fx-min-width: 50px; -fx-min-height: 50px;
    """;

    public void createColorPicker() {
        var grid = new GridPane(8, 3);
        grid.setHgap(10);
        grid.setVgap(10);

//        for (int r = 0; r < 3; r++) {
//            for (int c = 0; c < 8; c++) {
//                var b = new Button();
//                b.setStyle(String.format(style_format, colors[c][r]));
//            }
//        }
    }
}
