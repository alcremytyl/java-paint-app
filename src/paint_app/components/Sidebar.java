package paint_app.components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import paint_app.AppState;

public class Sidebar extends VBox {
    private static final AppState AppState = paint_app.AppState.getInstance();
    private final VBox layers = new VBox();

    public Sidebar() {
        final var history = new VBox();

        final var history_pane = new TitledPane("history", history);

        final var layers_scrollpane = new ScrollPane(layers);
        final var layer_pane = new TitledPane("layers", layers_scrollpane);
//        layers.setSpacing(10);
        layers.setPrefWidth(Double.MAX_VALUE);
//        layers_scrollpane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        layers_scrollpane.setFitToWidth(true);
        layers_scrollpane.setFitToHeight(true);
        layers_scrollpane.setPrefWidth(Double.MAX_VALUE);
        VBox.setVgrow(layers_scrollpane, Priority.ALWAYS);

        final var tool_config_box = new GridPane();
        final var tool_pane = new TitledPane("tool options", tool_config_box);
        addToolOption(tool_config_box, "stroke size", AppState.strokeSizeProperty());
        addToolOption(tool_config_box, "fill", AppState.doFillProperty());
        addToolOption(tool_config_box, "stroke", AppState.doStrokeProperty());

        final var pane_group = new VBox(history_pane, tool_pane, layer_pane);

        final var buttons = SidebarButton.getButtons();

        final var spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        final var sep = new Separator(Orientation.HORIZONTAL);

        setPrefWidth(300);
        getStyleClass().add("sidebar");
        getChildren().addAll(pane_group, spacer, sep, buttons);
    }

    private void addToolOption(GridPane parent, String name, SimpleDoubleProperty property) {
        final var tf = new TextField();
        tf.setPromptText("Input");

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

        parent.addRow(parent.getRowCount(), new Label(name), tf);
    }

    private void addToolOption(GridPane parent, String name, SimpleBooleanProperty property) {
        final var cb = new CheckBox();
        cb.setSelected(property.get());
        cb.setOnMouseClicked(e -> property.set(cb.isSelected()));

        parent.addRow(parent.getRowCount(), new Label(name), cb);
    }

    public VBox getLayers() {
        return this.layers;
    }
}
