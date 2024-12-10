package paint_app.components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import paint_app.AppColors;
import paint_app.AppState;
import paint_app.Main;

public class Sidebar extends VBox {
    private final AppState AppState = paint_app.AppState.getInstance();
    private final VBox layers = new VBox();

    public Sidebar() {
        final var text_style = "-fx-font-weight: bold; -fx-padding: 10px 0px;";

        final var hist_label = new Label("History");
        hist_label.setTextFill(AppColors.Text);
        hist_label.setStyle(text_style);

        final var history = new Rectangle(300, (double) Main.HEIGHT / 3);

        final var layers_label = new Label("Layers");
        layers_label.setTextFill(AppColors.Text);
        layers_label.setStyle(text_style);

        layers.setSpacing(10);
        layers.setBackground(AppColors.asBackground(AppColors.Mantle));
        layers.setPrefWidth(Double.MAX_VALUE);

        // setBackground does nothing for ScrollPane specifically
        final var layers_scrollpane = new ScrollPane(layers);
        layers_scrollpane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        layers_scrollpane.setFitToWidth(true);
        layers_scrollpane.setFitToHeight(true);
        layers_scrollpane.setPrefWidth(Double.MAX_VALUE);
        VBox.setVgrow(layers_scrollpane, Priority.ALWAYS);


        final var tool_config_box = new VBox();

        // TODO: make this a gridpane
        tool_config_box.getChildren().addAll(
                createToolOption("stroke size", "stroke size", AppState.strokeSizeProperty()),
                createToolOption("fill?", AppState.doFillProperty()),
                createToolOption("stroke?", AppState.doStrokeProperty())
        );

        final var tool_config_pane = new TitledPane("tool options", tool_config_box);

        final var layer_manager = LayerButton.getButtons();
        final var layer_pane = new TitledPane("layers", layers_scrollpane);
        final var spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        final var sep = new Separator(Orientation.HORIZONTAL);
        sep.setPadding(new Insets(30, 0, 0, 0));

        getChildren().addAll(hist_label, history, tool_config_pane, layer_pane, spacer, sep, layer_manager);
    }

    public VBox getLayers() {
        return this.layers;
    }

    private HBox createToolOption(String name, SimpleBooleanProperty property) {
        final var box = new HBox();
        final var cb = new CheckBox();

        cb.setSelected(property.get());
        cb.setOnMouseClicked(e -> property.set(cb.isSelected()));
        box.getChildren().addAll(new Label(name), cb);

        return box;
    }

    private HBox createToolOption(String name, String prompt, SimpleDoubleProperty property) {
        final var box = new HBox();
        final var tf = new TextField();
        tf.setPromptText(prompt);

        tf.textProperty().addListener((_, o, n) -> {
            try {
                double d = Math.max(Double.parseDouble(n), 1.0);
                property.set(d);
            } catch (NumberFormatException _) {
                if (n.isEmpty())
                    property.set(1.0);
                else
                    tf.setText(o);
            }
        });

        box.getChildren().addAll(new Label(name), tf);
        return box;
    }
}
