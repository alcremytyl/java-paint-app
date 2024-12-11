package paint_app.components;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import paint_app.AppColor;
import paint_app.AppState;
import paint_app.Helpers;

import java.util.Objects;

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
            Color.web("#40E0D0"), Color.web("#ACF0F0"),
            Color.web("#1E90FF"), Color.web("#87CEFA"),
            Color.web("#000080"), Color.web("#0000FF"),
            Color.web("#800080"), Color.web("#FF00FF"),
            Color.web("#BA55D3"), Color.web("#EE82EE")
    };
    static final AppState AppState = paint_app.AppState.getInstance();

    public Toolbar() {
        setMaxHeight(100);
        getStyleClass().add("toolbar");
        setAlignment(Pos.CENTER);

        final var brushes = createBrushInterface();
//        brushes.paddingProperty().bind(this.paddingProperty());
//        brushes.alignmentProperty().bind(this.alignmentProperty());

        final var colors = createColorsInterface();
//        colors.paddingProperty().bind(this.paddingProperty());
//        colors.alignmentProperty().bind(this.alignmentProperty());

        addRow(0, brushes, new Separator(Orientation.VERTICAL), colors);
        addRow(1, new Label("tools"), new Separator(Orientation.VERTICAL), new Label("color picker"));

        for (var i : getChildren()) {
            GridPane.setHalignment(i, HPos.CENTER);
        }
    }


    private static VBox createBrushInterface() {
        final var buttons = ToolbarButton.getToolButtons();
        return Helpers.createNodeGrid(buttons.size(), buttons::get);
    }

    private static HBox createColorsInterface() {
        final var colors = new HBox();
        colors.setSpacing(10);

        final var primary_color = createColorButton(Color.BLACK);
        primary_color.fillProperty().bind(AppState.primaryColorProperty());

        final var secondary_color = createColorButton(Color.WHITE);
        secondary_color.fillProperty().bind(AppState.secondaryColorProperty());

        final var color_pair = new VBox(primary_color, secondary_color);
        color_pair.getChildren().forEach(c -> c.getStyleClass().add("circle"));
        color_pair.setSpacing(5);

        final var color_selector = Helpers.createNodeGrid(COLORS.length, i -> {
            var btn = createColorButton(COLORS[i]);
            btn.getStyleClass().add("circle");
            btn.setOnMouseClicked(e -> {
                // did it this way as an experiment, and it surprisingly works
                // use other if this fails
                Objects.requireNonNull(switch (e.getButton()) {
                    case PRIMARY -> AppState.primaryColorProperty();
                    case SECONDARY -> AppState.secondaryColorProperty();
                    default -> null;
                }).set((Color) btn.getFill());


//                switch (e.getButton()) {
//                    case MouseButton.PRIMARY:
//                        AppState.primaryColorProperty().set((Color) btn.getFill());
//                        break;
//                    case MouseButton.SECONDARY:
//                        AppState.secondaryColorProperty().set((Color) btn.getFill());
//                        break;
//                }
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
//        circle.setStroke(AppColor.Crust);
//        circle.setStrokeWidth(2);
        btn.setBackground(AppColor.asBackground(c));
        return circle;
    }

}