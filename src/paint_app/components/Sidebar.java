package paint_app.components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import paint_app.AppState;

import static paint_app.AppState.MAX_BRUSH_SIZE;

/// Represents the sidebar of the paint application, containing tool options and layer management.
public class Sidebar extends VBox {
    private static final AppState AppState = paint_app.AppState.getInstance();
    private final VBox layers = new VBox();

    public Sidebar() {
//        final var history = new VBox();
//        final var history_pane = new TitledPane("HISTORY", history);

        final var layers_scrollpane = new ScrollPane(layers);
        final var layer_pane = new TitledPane("LAYERS", layers_scrollpane);
        layers.setSpacing(10);
        layers.setPrefWidth(Double.MAX_VALUE);
        layer_pane.getText();
        layers_scrollpane.setFitToWidth(true);
        layers_scrollpane.setFitToHeight(true);
        layers_scrollpane.setPrefWidth(Double.MAX_VALUE);
        VBox.setVgrow(layers_scrollpane, Priority.ALWAYS);

        final var tool_config_box = new GridPane();
        tool_config_box.setHgap(10);
        tool_config_box.setVgap(5);
        final var tool_pane = new TitledPane("TOOL OPTIONS", tool_config_box);
        addToolOption(tool_config_box, "stroke size", AppState.strokeSizeProperty());
        addToolOption(tool_config_box, "fill", AppState.doFillProperty());
        addToolOption(tool_config_box, "stroke", AppState.doStrokeProperty());
        addToolOption(tool_config_box, "alt stroke", AppState.secondaryAsStrokeProperty());

        final var pane_group = new VBox(/*history_pane,*/ tool_pane, layer_pane);

        final var buttons = SidebarButton.getButtons();

        final var spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        final var sep = new Separator(Orientation.HORIZONTAL);
        sep.setPadding(new Insets(0, 10, 0, 10));

        setPrefWidth(300);
        getStyleClass().add("sidebar");
        getChildren().addAll(pane_group, spacer, sep, buttons);
    }

    /**
     * Adds a tool option to the GridPane.
     *
     * @param parent   The {@link javafx.scene.layout.GridPane} to which the tool option will be added.
     * @param name     The name of the tool option.
     * @param property The {@link javafx.beans.property.Property} bound to the tool option.
     */
    private void addToolOption(GridPane parent, String name, SimpleDoubleProperty property) {
        final var tf = new TextField();
        tf.setPromptText("Input");
        tf.setMaxWidth(60);
        tf.textProperty().addListener((_, o, n) -> {
            try {
                double d = Math.max(Double.parseDouble(n), 1.0);
                if (d > MAX_BRUSH_SIZE) {
                    d = MAX_BRUSH_SIZE;
                    tf.setText(d + "");
                }
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

    /**
     * Adds a tool option to the GridPane.
     *
     * @param parent   The {@link javafx.scene.layout.GridPane} to which the tool option will be added.
     * @param name     The name of the tool option.
     * @param property The {@link javafx.beans.property.Property} bound to the tool option.
     */
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
