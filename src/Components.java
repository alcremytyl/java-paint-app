
import javafx.scene.control.Button;
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

    static final int COLORS_WIDTH = colors[0].length;
    static final int COLORS_HEIGHT = colors.length;

    // Java 13+ feature
    static final String STYLE_FORMAT = """
        -fx-background-color: %s;
        -fx-shape: 'M 0,10 A 10,10 0 1,1 20,10 A 10,10 0 1,1 0,10 Z';
        -fx-padding: 0; -fx-min-width: 50px; -fx-min-height: 50px;
    """;

    // TODO: add event listeners
    static public GridPane createColorPicker() {
        var grid = new GridPane(8, 3);
        grid.setHgap(10);
        grid.setVgap(10);

        System.out.println("before pane add");

        for (int r = 0; r < COLORS_HEIGHT; r++) {
            System.out.println("    in pane row build");

            var buttons = new Button[COLORS_WIDTH];

            for (int c = 0; c < COLORS_WIDTH; c++) {
                System.out.println("        inner pane add");

                var b = new Button();
                b.setStyle(String.format(STYLE_FORMAT, colors[r][c]));
                buttons[c] = b;
            }
            grid.addRow(r, buttons);
        }

        return grid;
    }
}
