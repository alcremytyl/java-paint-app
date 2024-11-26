package paint_app.components;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import paint_app.AppState;
import paint_app.InterfaceColors;

import java.util.function.Function;

public class Tools extends HBox {
    // TODO: get a nicer pallet
    static final Color[] COLORS = {
            Color.web("#000000"), Color.web("#FFFFFF"),
            Color.web("#696969"), Color.web("#c0c0c0"),
            Color.web("#800000"), Color.web("#FF0000"),
            Color.web("#B22222"), Color.web("#FF6347"),
            Color.web("#FF7F50"), Color.web("#FF8C00"),
            Color.web("#FFA07A"), Color.web("#FFD700"),
            Color.web("#FFFF00"), Color.web("#FFFACD"),
            Color.web("#808000"), Color.web("#9ACD32"),
            Color.web("#008000"), Color.web("#00FF00"),
            Color.web("#32CD32"), Color.web("#98FB98"),
            Color.web("#008080"), Color.web("#00FFFF"),
            Color.web("#40E0D0"), Color.web("#E0FFFF"),
            Color.web("#1E90FF"), Color.web("#87CEFA"),
            Color.web("#000080"), Color.web("#0000FF"),
            Color.web("#800080"), Color.web("#FF00FF"),
            Color.web("#BA55D3"), Color.web("#EE82EE")
    };
    static final AppState AppState = paint_app.AppState.getInstance();

    public Tools() {
        addColorInterface(this);

        for (int i = getChildren().size() - 1; i > 0; i--) {
            var sep = new Separator(Orientation.VERTICAL);
            getChildren().add(i, sep);
        }
    }

    private static VBox createNodeGrid(String label_text, int size, Function<Integer, Node> generator) {
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
        text.setTextFill(InterfaceColors.Text);
        text.setStyle("-fx-font-size: 16px; -fx-font-style: italic;");
        text.setPadding(new Insets(5));

        box.getChildren().addAll(grid, text);
        return box;
    }

    private static void addColorInterface(Pane n) {
        final var colors = new HBox();
        colors.setSpacing(10);
        final var color_pair = new VBox();
        final var primary_color = createColorButton(Color.BLACK);
        primary_color.backgroundProperty().bind(AppState.primaryColorProperty());
        final var secondary_color = createColorButton(Color.WHITE);
        secondary_color.backgroundProperty().bind(AppState.secondaryColorProperty());

        color_pair.getChildren().addAll(primary_color, secondary_color);


        final var color_selector = createNodeGrid("color picker", COLORS.length, i -> {
            var btn = createColorButton(COLORS[i]);
            btn.setOnMouseClicked(e -> {
                AppState.primaryColorProperty().set(btn.getBackground());
            });

            return btn;
        });

        final var sep = new Separator(Orientation.VERTICAL);

        colors.getChildren().addAll(color_pair, sep, color_selector);
        n.getChildren().add(colors);
    }

    private static Button createColorButton(Color c) {
        final var btn = new Button(" ");
        // TODO: continue here
        final var color = InterfaceColors.Crust;
        final var style = String.format("-fx-border-color: %s; -fx-border-width: 2px;", . .toString().substring(2));
        btn.setStyle(style);
        btn.setBackground(new Background(new BackgroundFill(c, null, null)));
        btn.setShape(new Circle(12));
        return btn;
    }

}

//  Old code, delete later

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
/// /            s.setStroke(InterfaceColors.Crust);
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
