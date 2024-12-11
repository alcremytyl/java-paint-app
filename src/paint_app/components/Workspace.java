package paint_app.components;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import paint_app.AppColors;
import paint_app.AppState;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Workspace extends StackPane {
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 600;

    private static final AppState AppState = paint_app.AppState.getInstance();

    public Workspace() {
        setMinSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        setMaxSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        setBackground(AppColors.asBackground(Color.WHITE));
        setAlignment(Pos.BOTTOM_CENTER);
        getChildren().setAll(AppState.layersProperty());


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

        addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            final var layer = AppState.currentLayerProperty().get();
            if (layer != null) {
                AppState.saveState(layer);
            }
        });

    }

    public WritableImage captureWorkspace() {
        WritableImage image = new WritableImage((int) getWidth(), (int) getHeight());
        this.snapshot(null, image);
        return image;
    }

    public void saveAsImage(File file) {
        WritableImage image = captureWorkspace();
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
