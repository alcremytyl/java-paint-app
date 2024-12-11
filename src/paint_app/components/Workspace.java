package paint_app.components;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import paint_app.AppColor;
import paint_app.AppState;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Workspace extends StackPane {
    public static final int CANVAS_WIDTH = 800;
    public static final int CANVAS_HEIGHT = 600;

    private static final AppState AppState = paint_app.AppState.getInstance();
//    private static final TextField temp_textfield = new TextField();

    final StackPane layer_pane = new StackPane();

    public Workspace() {
        setMinSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        setMaxSize(CANVAS_WIDTH, CANVAS_HEIGHT);

        layer_pane.setBackground(AppColor.asBackground(Color.WHITE));
        layer_pane.setAlignment(Pos.BOTTOM_CENTER);
        layer_pane.getChildren().setAll(AppState.layersProperty());

        final EventHandler<MouseEvent> handler = e -> {
            final var layer = AppState.currentLayerProperty().get();
            if (layer == null) return;
            AppState.currentToolProperty().get().getEvent().handle(AppState, e, layer.getGraphicsContext2D());
            layer.updatePreview();
        };

        // change background for sidebar version and attach/remove tool listeners
        addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
        addEventFilter(MouseEvent.MOUSE_DRAGGED, handler);
        addEventFilter(MouseEvent.MOUSE_RELEASED, handler);
        addEventFilter(MouseEvent.MOUSE_PRESSED, _ -> {
            final var layer = AppState.currentLayerProperty().get();
            if (layer != null) {
                AppState.saveState(layer);
            }
        });

        getChildren().addAll(layer_pane);
    }

    public void saveAsImage(File file) {
        WritableImage image = new WritableImage(CANVAS_WIDTH, CANVAS_HEIGHT);
        this.snapshot(null, image);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            AppState.logger.log(Level.SEVERE, "Error while saving", e);
        }
    }

    public StackPane getLayers() {
        return this.layer_pane;
    }

}
