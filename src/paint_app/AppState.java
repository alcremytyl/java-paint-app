package paint_app;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import paint_app.components.Layer;
import paint_app.components.Sidebar;
import paint_app.components.ToolbarButton;
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

    private final SimpleObjectProperty<ToolbarButton> current_tool = new SimpleObjectProperty<>(ToolbarButton.BRUSH);

    private final SimpleDoubleProperty brush_size = new SimpleDoubleProperty(12.0);
    private final SimpleDoubleProperty eraser_size = new SimpleDoubleProperty(20.0);

    private final SimpleListProperty<Layer> layers = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final SimpleObjectProperty<Layer> current_layer = new SimpleObjectProperty<>(null);

    private final SimpleDoubleProperty start_x = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty start_y = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty lastX = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty lastY = new SimpleDoubleProperty(0);

    private final SimpleObjectProperty<String> text_to_draw = new SimpleObjectProperty<>("Enter text here");

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

    public SimpleObjectProperty<ToolbarButton> currentToolProperty() {
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

    public SimpleDoubleProperty startXProperty() {
        return start_x;
    }

    public SimpleDoubleProperty startYProperty() {
        return start_y;
    }

    public SimpleDoubleProperty lastXProperty() {
        return lastX;
    }

    public SimpleDoubleProperty lastYProperty() {
        return lastY;
    }

    public SimpleDoubleProperty eraserSizeProperty() {
        return eraser_size;
    }

    public SimpleObjectProperty<String> textToDrawProperty() {
        return this.text_to_draw;
    }

    public String getTextToDraw() {
        return text_to_draw.get();
    }

    public void setTextToDraw(String text) {
        text_to_draw.set(text);
    }

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
                    s.getLayers().getChildren().reversed().addAll(previews);

                } else if (change.wasRemoved()) {
                    // TODO merge the streams like how it's done in change was added
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

        final EventHandler<MouseEvent> handler = e -> {
            final var layer = current_layer.get();
            if (layer == null) return;

            current_tool.get().getEvent().handle(this, e, layer.getGraphicsContext2D());
            layer.updatePreview();
        };

        // change background for sidebar version and attach/remove tool listeners
        w.addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
        w.addEventFilter(MouseEvent.MOUSE_DRAGGED, handler);
        w.addEventFilter(MouseEvent.MOUSE_RELEASED, handler);

        this.current_layer.addListener((observable, o, n) -> {
            logger.info("current layer " + o + " to " + n);
            if (n != null)
                n.toSelected();
            if (o != null)
                o.unSelect();
        });
    }


}
