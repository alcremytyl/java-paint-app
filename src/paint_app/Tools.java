package paint_app;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

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

    Tools() {
        getStyleClass().add("toolbox-label");
        setAlignment(Pos.TOP_CENTER);
        setSpacing(30);
        setPadding(new Insets(20, 20, 20, 0));
        setBackground(new Background(new BackgroundFill(Color.web("#363a4f"), null, null)));

        var bl_placeholder = new VBox();
        bl_placeholder.setAlignment(Pos.CENTER);
        var tl_placeholder = new Rectangle(200, 50);
        tl_placeholder.setFill(Color.PALEGOLDENROD);
        var ll_placeholder = new Label("placeholder left");
        ll_placeholder.setPadding(new Insets(5));
        bl_placeholder.getChildren().addAll(tl_placeholder, ll_placeholder);

        var br_placeholder = new VBox();
        var tr_placeholder = new Rectangle(200, 50);
        tr_placeholder.setFill(Color.PALEGREEN);
        var lr_placeholder = new Label("placeholder right");
        lr_placeholder.setPadding(new Insets(5));
        br_placeholder.getChildren().addAll(tr_placeholder, lr_placeholder);
        br_placeholder.setAlignment(Pos.CENTER);

        var color_picker = createColorPicker();


        var children = getChildren();
        children.addAll(bl_placeholder, color_picker, br_placeholder);

        for (int i = getChildren().size() - 1; i > 0; i--) {
            var sep = new Separator(Orientation.VERTICAL);
            children.add(i, sep);
        }
    }

    // TODO: add event listeners
    static public VBox createColorPicker() {
        var box = new VBox();
        box.setAlignment(Pos.CENTER);

        var grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);

        for (int i = 0; i < colors.length; i++) {
            final var color = Color.web(colors[i]);
            final var circle_stroke = Color.web(color.getBrightness() >= 0.5 ? DARK_STROKE : BRIGHT_STROKE);

            var circle = new Circle(12, color);
            circle.setStroke(circle_stroke);
            circle.setStrokeWidth(2);

            grid.add(circle, i / 2, i % 2);
        }

        var text = new Label("color picker");
        text.setPadding(new Insets(5));

        box.getChildren().addAll(grid, text);
        return box;
    }

    void scaleChildren() {
    }
}