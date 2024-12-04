package paint_app;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import paint_app.components.Layer;
import paint_app.components.Sidebar;
import paint_app.components.Tool;
import paint_app.components.Workspace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class AppState {
    private static AppState INSTANCE;

    public final Logger logger = Logger.getLogger("Paint App");

    private final SimpleObjectProperty<Color> primary_color = new SimpleObjectProperty<>(Color.BLACK);
    private final SimpleObjectProperty<Color> secondary_color = new SimpleObjectProperty<>(Color.WHITE);

    private final SimpleObjectProperty<Tool> current_tool = new SimpleObjectProperty<>(Tool.BRUSH);
    private final SimpleDoubleProperty brush_size = new SimpleDoubleProperty(12.0);

    private final SimpleListProperty<Layer> layers = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final SimpleObjectProperty<Layer> current_layer = new SimpleObjectProperty<>(null);


    private AppState() {
    }

    public static synchronized AppState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppState();
        }
        return INSTANCE;
    }

    public SimpleObjectProperty<Color> primaryColorProperty() {
        return this.primary_color;
    }

    public SimpleObjectProperty<Color> secondaryColorProperty() {
        return this.secondary_color;
    }

    public SimpleObjectProperty<Tool> currentToolProperty() {
        return this.current_tool;
    }

    public SimpleDoubleProperty brushSizeProperty() {
        return this.brush_size;
    }

    public SimpleListProperty<Layer> layersProperty() {
        return this.layers;
    }

    public SimpleObjectProperty<Layer> currentLayerProperty() {
        return this.current_layer;
    }

    public void resetColors() {
        logger.info("Resetting colors");
        primary_color.set(Color.BLACK);
        secondary_color.set(Color.WHITE);
    }

    public void swapColors() {
        logger.info("Swapping colors");

        final var tmp = primary_color.get();
        primary_color.set(secondary_color.get());
        secondary_color.set(tmp);
    }

    // TODO: current layer attach and unattach mouse listeners for tool
    public void attachListeners(Workspace w, Sidebar s) {
        // references for equality checking
        Map<Layer, HBox> layer_pairs = new HashMap<>();

        // sync workspace and sidebar to `layers`
        this.layers.addListener((ListChangeListener<? super Node>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    final var canvases = change.getAddedSubList();
                    final List<HBox> previews = canvases.stream()
                            .filter(Layer.class::isInstance)
                            .map(Layer.class::cast)
                            .map(layer -> {
                                final var preview = layer.getSidebarContent();
                                layer_pairs.put(layer, preview);
                                return preview;
                            })
                            .toList();

                    current_layer.set((Layer) canvases.getFirst());

                    w.getChildren().addAll(canvases);
                    s.getLayers().getChildren().addAll(previews);

                } else if (change.wasRemoved()) {
                    final var canvases = change.getRemoved();
                    final var previews = canvases.stream()
                            .filter(Layer.class::isInstance)
                            .map(Layer.class::cast)
                            .map(layer_pairs::get)
                            .toList();


                    final var next_layer = !layers.isEmpty() ? layers.getLast() : null;
                    current_layer.set(next_layer);

                    w.getChildren().removeAll(canvases);
                    s.getLayers().getChildren().removeAll(previews);

                    canvases.stream()
                            .filter(Layer.class::isInstance)
                            .map(Layer.class::cast)
                            .forEach(layer_pairs::remove);
                }
            }
        });

        // change background for sidebar version and attach/remove tool listeners
        this.current_layer.addListener((observable, o, n) -> {
            if (o != null)
                o.unSelect();
            if (n != null)
                n.toSelected();
        });
    }
}
