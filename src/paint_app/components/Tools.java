package paint_app.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.function.Function;

public class Tools extends HBox {
    static final String[] COLORS = {
            "#000000", "#FFFFFF", "#FF0000", "#00FF00",
            "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF",
            "#D3D3D3", "#A9A9A9", "#FF6347", "#90EE90",
            "#ADD8E6", "#FFFFE0", "#FFB6C1", "#E0FFFF",
            "#8B0000", "#006400", "#00008B", "#B8860B",
            "#8B008B", "#008B8B", "#FFA500", "#FFC0CB",
            "#A52A2A", "#8A2BE2", "#808000", "#D2B48C"
    };

    public Tools() {

        var colors = gridHelper("color picker", COLORS.length, i -> {
            var btn = new Button();
            var c = new Circle(12);
            c.setFill(Color.web(COLORS[i]));
            btn.setShape(c);
            return btn;
        });
    }
//    // TODO: replace with custom shape type & maybe add hover text
//    // FIXME: https://stackoverflow.com/questions/67607416/what-is-the-best-way-to-statically-initialize-an-enummap-in-java don't keep it as is
//    static final EnumMap<InterfaceShape, Shape> shapes = new EnumMap<>(InterfaceShape.class);
//
//    static {
//        final var poly = new Polygon(0, 0, -35, 0, -35.0 / 2, -35, 0, 0);
//        final var arrow = new Polyline(0, 0, 0, -25, -5, -20, 0, -25, 5, -20);
//
//        shapes.put(InterfaceShape.CIRCLE, new Circle(18, Color.WHITESMOKE));
//        shapes.put(InterfaceShape.RECTANGLE, new Rectangle(36, 36, Color.WHITESMOKE));
//        shapes.put(InterfaceShape.POLYGON, poly);
//
//        for (var s : shapes.values()) {
////            s.setStroke(InterfaceColors.Crust);
//            s.setFill(Color.WHITESMOKE);

    /// /            s.setStrokeWidth(2);
//        }
//
//        shapes.put(InterfaceShape.ARROW, arrow);
//
//        arrow.setStrokeWidth(5);
//        arrow.setStroke(Color.WHITESMOKE);
//        arrow.setStrokeLineJoin(StrokeLineJoin.ROUND);
//    }
//
//    public Tools() {
//        getStyleClass().add("toolbox-label");
//        setAlignment(Pos.TOP_CENTER);
//        setSpacing(30);
//        setPadding(new Insets(20, 20, 20, 0));
//        setBackground(new Background(new BackgroundFill(Color.web("#363a4f"), null, null)));
//
//        var brush_grid = gridHelper("brushes", 0, i -> {
//            return new Rectangle();
//
//        });
//
//        var shape_grid = gridHelper("shapes", shapes.size(), i -> {
//            final var k = InterfaceShape.values()[i];
//            return shapes.get(k);
//        });
//
//        var color_picker = gridHelper("color picker", colors.length, i -> {
//            var circle = new Circle(12, Color.web(colors[i]));
//            circle.setStroke(InterfaceColors.Crust);
//            circle.setStrokeWidth(2);
//            return circle;
//        });
//
//        var children = getChildren();
//        children.addAll(brush_grid, shape_grid, color_picker);
//
//        for (int i = getChildren().size() - 1; i > 0; i--) {
//            var sep = new Separator(Orientation.VERTICAL);
//            children.add(i, sep);
//        }
//    }
//
    static VBox gridHelper(String label_text, int size, Function<Integer, Node> generator) {
        var box = new VBox();
        box.setAlignment(Pos.CENTER);

        var grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);

        for (int i = 0; i < size; i++) {
            var node = generator.apply(i);

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

//    enum InterfaceShape {
//        CIRCLE, RECTANGLE, POLYGON, ARROW
//
//    }
}