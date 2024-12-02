package paint_app.components;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import paint_app.AppState;
import paint_app.InterfaceColors;

import java.util.function.Function;

public class Toolbar extends GridPane {
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

    public Toolbar() {
        setBackground(new Background(new BackgroundFill(InterfaceColors.Surface0, null, null)));
        setBorder(new Border(new BorderStroke(InterfaceColors.Mantle, BorderStrokeStyle.SOLID, null, BorderStroke.THICK)));
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5, 20, 5, 20));

        var brush_label = new Label("tools");
        brush_label.setTextFill(InterfaceColors.Text);
        brush_label.setStyle("-fx-font-size: 14px; -fx-padding: 4px");
        brush_label.setAlignment(Pos.CENTER);

        var colors_label = new Label("color picker");
        colors_label.setTextFill(brush_label.getTextFill());
        colors_label.setStyle(brush_label.getStyle());
        colors_label.setAlignment(brush_label.getAlignment());

        final var brushes = createBrushInterface();
        brushes.paddingProperty().bind(this.paddingProperty());
        brushes.alignmentProperty().bind(this.alignmentProperty());

        final var colors = createColorsInterface();
        colors.paddingProperty().bind(this.paddingProperty());
        colors.alignmentProperty().bind(this.alignmentProperty());

        final var sep_1 = new Separator(Orientation.VERTICAL);
        final var sep_2 = new Separator(Orientation.VERTICAL);

        addRow(0, brushes, sep_1, colors);
        addRow(1, brush_label, sep_2, colors_label);

        for (var i : getChildren()) {
            GridPane.setHalignment(i, HPos.CENTER);
        }
    }

    private static VBox createNodeGrid(int size, Function<Integer, Node> generator) {
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


        box.getChildren().addAll(grid);
        return box;
    }

    private static VBox createBrushInterface() {
        final var icons = Tool.getImageViews();
        return createNodeGrid(icons.size(), icons::get);
    }

    private static HBox createColorsInterface() {
        final var colors = new HBox();
        colors.setSpacing(10);

        final var color_pair = new VBox();

        final var primary_color = createColorButton(Color.BLACK);
        primary_color.fillProperty().bind(AppState.primaryColorProperty());

        final var secondary_color = createColorButton(Color.WHITE);
        secondary_color.fillProperty().bind(AppState.secondaryColorProperty());

        color_pair.getChildren().addAll(primary_color, secondary_color);

        final var color_selector = createNodeGrid(COLORS.length, i -> {
            var btn = createColorButton(COLORS[i]);
            btn.setOnMouseClicked(e -> {
                switch (e.getButton()) {
                    case MouseButton.PRIMARY:
                        AppState.primaryColorProperty().set((Color) btn.getFill());
                        break;
                    case MouseButton.SECONDARY:
                        AppState.secondaryColorProperty().set((Color) btn.getFill());
                        break;
                }
            });

            return btn;
        });

        final var sep = new Separator(Orientation.VERTICAL);
        sep.setMaxHeight(60);

        colors.getChildren().addAll(color_pair, sep, color_selector);
        return colors;
    }

    private static Circle createColorButton(Color c) {
        final var btn = new Button(" ");
        var circle = new Circle(12, c);
        circle.setStroke(InterfaceColors.Crust);
        circle.setStrokeWidth(2);
        btn.setBackground(new Background(new BackgroundFill(c, null, null)));
        return circle;
    }

}