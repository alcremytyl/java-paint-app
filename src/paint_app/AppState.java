package paint_app;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import paint_app.components.Layer;
import paint_app.components.Sidebar;
import paint_app.components.ToolbarButton;
import paint_app.components.Workspace;

import java.io.File;
import java.util.Stack;
import java.util.logging.Logger;

public class AppState {
    private static AppState INSTANCE;

    public final Logger logger = Logger.getLogger("Paint App");

    private final SimpleObjectProperty<Workspace> workspace_property = new SimpleObjectProperty<>(null);

    private final SimpleObjectProperty<Color> primary_color = new SimpleObjectProperty<>(Color.BLACK);
    private final SimpleObjectProperty<Color> secondary_color = new SimpleObjectProperty<>(Color.WHITE);
    private final SimpleObjectProperty<ToolbarButton> current_tool = new SimpleObjectProperty<>(ToolbarButton.BRUSH);

    private final SimpleDoubleProperty stroke_size = new SimpleDoubleProperty(12.0);
    private final SimpleBooleanProperty do_stroke = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty do_fill = new SimpleBooleanProperty(true);

    private final SimpleListProperty<Layer> layers = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final SimpleObjectProperty<Layer> current_layer = new SimpleObjectProperty<>(null);

    private final SimpleDoubleProperty start_x = new SimpleDoubleProperty(0);
    private final SimpleDoubleProperty start_y = new SimpleDoubleProperty(0);

    private final SimpleObjectProperty<String> text_to_draw = new SimpleObjectProperty<>("Enter text here");

    private final Stack<Layer> undoStack = new Stack<>();
    private final Stack<Layer> redoStack = new Stack<>();

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

    public SimpleObjectProperty<Workspace> workspaceProperty() {
        return this.workspace_property;
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
        undoStack.push(cloneLayer(layer));
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Layer lastState = undoStack.pop();
            redoStack.push(cloneLayer(currentLayerProperty().get()));
            currentLayerProperty().get().restoreState(lastState);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Layer nextState = redoStack.pop();
            undoStack.push(cloneLayer(currentLayerProperty().get()));
            currentLayerProperty().get().restoreState(nextState);
        }
    }

    private Layer cloneLayer(Layer layer) {
        return layer.clone();
    }

    // sync workspace and sidebar to `layers`
    public void synchronizeLayerComponents(Workspace w, Sidebar s) {
        this.layers.addListener((ListChangeListener<? super Node>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    final var layer = (Layer) change.getAddedSubList().getFirst();

                    w.getChildren().add(layer);
                    s.getLayers().getChildren().reversed().add(layer.getSidebarContent());
                    current_layer.set(layer);

                } else if (change.wasRemoved()) {
                    final var layer = (Layer) change.getRemoved().getFirst();
                    final var next = layers.size() > 1 ? layers.getLast() : null;

                    w.getChildren().remove(layer);
                    s.getLayers().getChildren().remove(layer.getSidebarContent());

                    System.out.println(next);
                    current_layer.set(next);
                }
            }
        });
    }

    public void saveWorkspaceAsImage(Workspace workspace) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Workspace");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("JPEG Files", "*.jpg"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            String ext = fileChooser.getSelectedExtensionFilter().getExtensions().getFirst().replace("*.", "");
            System.out.print(file + " || " + ext);
            workspace.saveAsImage(file);
        }
    }

}