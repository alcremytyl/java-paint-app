package paint_app;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import paint_app.components.Layer;
import paint_app.components.Sidebar;
import paint_app.components.ToolbarButton;
import paint_app.components.Workspace;

import java.util.Stack;
import java.util.logging.Logger;

public class AppState {
    public static final int HEIGHT = 800;
    public static final int WIDTH = 1280;
    public static final int MAX_BRUSH_SIZE = 200;
    private static AppState INSTANCE;
    public final Logger logger = Logger.getLogger("Paint App");
    private final SimpleBooleanProperty do_fill = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty do_stroke = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty secondary_as_stroke = new SimpleBooleanProperty(true);
    private final SimpleDoubleProperty start_x = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty start_y = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty stroke_size = new SimpleDoubleProperty(12.0);
    private final SimpleListProperty<Layer> layers = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final SimpleObjectProperty<Color> primary_color = new SimpleObjectProperty<>(Color.BLACK);
    private final SimpleObjectProperty<Color> secondary_color = new SimpleObjectProperty<>(Color.WHITE);
    private final SimpleObjectProperty<Layer> current_layer = new SimpleObjectProperty<>(null);
    private final SimpleObjectProperty<String> text_to_draw = new SimpleObjectProperty<>("Enter text here");
    private final SimpleObjectProperty<ToolbarButton> current_tool = new SimpleObjectProperty<>(ToolbarButton.BRUSH);
    private final Stack<Layer> redo_stack = new Stack<>();
    private final Stack<Layer> undo_stack = new Stack<>();
    private Workspace workspace;
    private Sidebar sidebar;

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


    public Workspace getWorkspace() {
        return this.workspace;
    }

    public Sidebar getSidebar() {
        return this.sidebar;
    }

    public SimpleObjectProperty<Color> primaryColorProperty() {
        return this.primary_color;
    }

    public SimpleObjectProperty<Color> secondaryColorProperty() {
        return this.secondary_color;
    }

    public SimpleBooleanProperty secondaryAsStrokeProperty() {
        return this.secondary_as_stroke;
    }

    public SimpleObjectProperty<ToolbarButton> currentToolProperty() {
        return this.current_tool;
    }

    public SimpleDoubleProperty strokeSizeProperty() {
        return this.stroke_size;
    }

    public SimpleBooleanProperty doStrokeProperty() {
        return do_stroke;
    }

    public SimpleBooleanProperty doFillProperty() {
        return do_fill;
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

    public SimpleObjectProperty<String> textToDrawProperty() {
        return this.text_to_draw;
    }

    public String getTextToDraw() {
        return text_to_draw.get();
    }

    public void setTextToDraw(String text) {
        text_to_draw.set(text);
    }

    public void saveState(Layer layer) {
        undo_stack.push(cloneLayer(layer));
        redo_stack.clear();
    }

    public void undo() {
        if (!undo_stack.isEmpty()) {
            Layer lastState = undo_stack.pop();
            redo_stack.push(cloneLayer(current_layer.get()));
            current_layer.get().restoreState(lastState);
        }
    }

    public void redo() {
        if (!redo_stack.isEmpty()) {
            Layer nextState = redo_stack.pop();
            undo_stack.push(cloneLayer(currentLayerProperty().get()));
            current_layer.get().restoreState(nextState);
        }
    }

    private Layer cloneLayer(Layer layer) {
        return layer.clone();
    }

    // sync workspace and sidebar to `layers`
    public void synchronizeLayerComponents(Workspace workspace, Sidebar sidebar) {
        this.workspace = workspace;
        this.sidebar = sidebar;

        this.layers.addListener((ListChangeListener<? super Node>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    final var layer = (Layer) change.getAddedSubList().getFirst();

                    workspace.getLayers().getChildren().add(layer);
                    sidebar.getLayers().getChildren().reversed().add(layer.getSidebarContent());
                    current_layer.set(layer);

                } else if (change.wasRemoved()) {
                    final var layer = (Layer) change.getRemoved().getFirst();
                    final var next = layers.size() > 1 ? layers.getLast() : null;

                    workspace.getChildren().remove(layer);
                    sidebar.getLayers().getChildren().remove(layer.getSidebarContent());

                    System.out.println(next);
                    current_layer.set(next);
                }
            }
        });
    }

}