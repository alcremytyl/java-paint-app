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
import paint_app.components.ToolbarButton;
import paint_app.components.Workspace;

import java.util.List;
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
        current_layer.addListener((observable, o, n) -> {
            logger.info("current layer " + o + " to " + n);

            if (n != null) n.toSelected();
            if (o != null) o.unSelect();
        });
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

    // sync workspace and sidebar to `layers`
    public void synchronizeLayerComponents(Workspace w, Sidebar s) {
        // references for equality checking

        this.layers.addListener((ListChangeListener<? super Node>) change -> {
            while (change.next()) {
                // on add, append to pairs and set to current_layer
                if (change.wasAdded()) {
                    final var added_canvases = change.getAddedSubList();
                    final List<HBox> previews = added_canvases.stream()
                            .map(Layer.class::cast)
                            .map(Layer::getSidebarContent)
                            .toList();

                    current_layer.set((Layer) added_canvases.getFirst());

                    w.getChildren().addAll(added_canvases);
                    s.getLayers().getChildren().reversed().addAll(previews);

                } else if (change.wasRemoved()) {
                    final var removed_canvases = change.getRemoved();

                    removed_canvases.stream()
                            .map(Layer.class::cast)
                            .forEach(layer -> {
                                w.getChildren().remove(layer);
                                s.getLayers().getChildren().remove(layer.getSidebarContent());
                            });

                    final var next_layer = !layers.isEmpty() ? layers.getLast() : null;
                    current_layer.set(next_layer);
                }
            }
        });
    }
}