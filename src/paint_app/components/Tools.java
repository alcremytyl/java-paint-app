package paint_app.components;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import paint_app.InterfaceColors;

import java.util.function.Function;

public class Tools extends HBox {
    static final String DARK_STROKE = "#181926";
    static final String BRIGHT_STROKE = "#cad3f5";
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

    public Tools() {
        getStyleClass().add("toolbox-label");
        setAlignment(Pos.TOP_CENTER);
        setSpacing(30);
        setPadding(new Insets(20, 20, 20, 0));
        setBackground(new Background(new BackgroundFill(Color.web("#363a4f"), null, null)));

        var brushes = gridHelper("brushes", 0, i -> {
            return new Rectangle();
        });

        var shapes = gridHelper("shapes", 0, i -> {
            return new Rectangle();
        });

        // TODO: add event listeners/custom type for pickers
        var color_picker = gridHelper("color picker", colors.length, (i) -> {
            var circle = new Circle(12, Color.web(colors[i]));
            circle.setStroke(InterfaceColors.Crust);
            circle.setStrokeWidth(2);
            return circle;
        });

        var children = getChildren();
        children.addAll(brushes, shapes, color_picker);

        for (int i = getChildren().size() - 1; i > 0; i--) {
            var sep = new Separator(Orientation.VERTICAL);
            children.add(i, sep);
        }
    }

    static VBox gridHelper(String label_text, int size, Function<Integer, Node> generator) {
        var box = new VBox();
        box.setAlignment(Pos.CENTER);

        var grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);

        for (int i = 0; i < size; i++) {
            var node = generator.apply(i);
            grid.add(node, i / 2, i % 2);
        }

        var text = new Label(label_text);
        text.setPadding(new Insets(5));

        box.getChildren().addAll(grid, text);
        return box;
    }
}