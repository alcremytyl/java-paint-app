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
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import paint_app.InterfaceColors;

import java.util.EnumMap;
import java.util.function.Function;

public class Tools extends HBox {

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
    // TODO: replace with custom shape type & maybe add hover text
    // FIXME: https://stackoverflow.com/questions/67607416/what-is-the-best-way-to-statically-initialize-an-enummap-in-java don't keep it as is
    static final EnumMap<InterfaceShape, Shape> shapes = new EnumMap<>(InterfaceShape.class);

    static {
        var arrow = new Polygon(
                50.0, 10.0,   // top point of the arrow
                90.0, 50.0,   // right point of the arrowhead
                50.0, 90.0,   // bottom point of the arrow
                50.0, 60.0,   // middle left (shaft start)
                10.0, 60.0    // left point of the arrow
        );
        shapes.put(InterfaceShape.CIRCLE, new Circle(18, InterfaceColors.Red));
        shapes.put(InterfaceShape
                .RECTANGLE, new Rectangle(36, 36, InterfaceColors.Green));
        shapes.put(InterfaceShape.POLYGON, new Polygon(10, 0, 20, 20, 0, 20));
        shapes.put(InterfaceShape.ARROW, arrow);

        for (var s : shapes.values()) {
            s.setStroke(InterfaceColors.Crust);
            s.setStrokeWidth(2);
        }
    }

    public Tools() {
        getStyleClass().add("toolbox-label");
        setAlignment(Pos.TOP_CENTER);
        setSpacing(30);
        setPadding(new Insets(20, 20, 20, 0));
        setBackground(new Background(new BackgroundFill(Color.web("#363a4f"), null, null)));

        var brush_grid = gridHelper("brushes", 0, i -> {
            return new Rectangle();

        });

        var shape_grid = gridHelper("shapes", shapes.size(), i -> {
            final var k = InterfaceShape.values()[i];
            return shapes.get(k);
        });

        var color_picker = gridHelper("color picker", colors.length, i -> {
            var circle = new Circle(12, Color.web(colors[i]));
            circle.setStroke(InterfaceColors.Crust);
            circle.setStrokeWidth(2);
            return circle;
        });

        var children = getChildren();
        children.addAll(brush_grid, shape_grid, color_picker);

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
            if (node == null) {
                System.out.printf("found null at [name=%s,shape=%s]\n", label_text, InterfaceShape.values()[i]);
                continue;
            }

            if (size <= 10) {
                grid.add(node, i, 0);
            } else {
                grid.add(node, i / 2, i % 2);
            }
        }

        var text = new Label(label_text);
        text.setPadding(new Insets(5));

        box.getChildren().addAll(grid, text);
        return box;
    }

    enum InterfaceShape {
        CIRCLE, RECTANGLE, POLYGON, ARROW

    }
}