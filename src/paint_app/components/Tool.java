package paint_app.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import paint_app.AppState;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum Tool {

    // TODO: tool events
    BRUSH((e, gc) -> {
        final var color;

        gc.setStroke(AppState.pr);
    }),
    SPRAY((e, gc) -> {
    }),
    CIRCLE((e, gc) -> {
        double startX = AppState.getInstance().getStartX();
        double startY = AppState.getInstance().getStartY();
        double width = Math.abs(startX - e.getX());
        double height = Math.abs(startY - e.getY());
        gc.setStroke(AppState.getInstance().primaryColorProperty().get());
        gc.setLineWidth(AppState.getInstance().brushSizeProperty().get());
        gc.strokeOval(Math.min(startX, e.getX()), Math.min(startY, e.getY()), width, height)
    }),
    RECTANGLE((e, gc) -> {
        double startX = AppState.getInstance().getStartX();
        double startY = AppState.getInstance().getStartY();
        double width = Math.abs(startX - e.getX());
        double height = Math.abs(startY - e.getY());
        gc.setStroke(AppState.getInstance().primaryColorProperty().get());
        gc.setLineWidth(AppState.getInstance().brushSizeProperty().get());
        gc.strokeRect(Math.min(startX, e.getX()), Math.min(startY, e.getY()), width, height)
    }),
    DROPPER((e, gc) -> {
        var currentLayer = AppState.getInstance().currentLayerProperty().get();
        if (currentLayer != null) {
            var snapshot = currentLayer.getCanvas().snapshot(null, null);
            int x = (int) e.getX();
            int y = (int) e.getY();
            if (x >= 0 && y >= 0 && x < snapshot.getWidth() && y < snapshot.getHeight()) {
                var color = snapshot.getPixelReader().getColor(x, y);
                AppState.getInstance().primaryColorProperty().set(color);
            }
        }
    }),
    TEXT((e, gc) -> {
        gc.setFill(AppState.getInstance().primaryColorProperty().get());
        gc.setFont(AppState.getInstance().textFont);
        String text = AppState.getInstance().getTextToDraw();
        if (text != null && !text.isEmpty()) {
            gc.fillText(text, e.getX(), e.getY());
        }
    }),
    SELECT((e, gc) -> {
        double startX = AppState.getInstance().getStartX();
        double startY = AppState.getInstance().getStartY();
        double width = Math.abs(startX - e.getX());
        double height = Math.abs(startY - e.getY());
        gc.setStroke(AppState.getInstance().selectionColor);
        gc.setLineWidth(1);
        gc.strokeRect(Math.min(startX, e.getX()), Math.min(startY, e.getY()), width, height)
    }),
    ERASER((e, gc) -> {
        double eraserSize = AppState.getInstance().getEraserSize();
        gc.clearRect(
            e.getX() - eraserSize / 2;
            e.getY() - eraserSize / 2;
            eraserSize,
            eraserSize
        );
    });

    static AppState AppState = paint_app.AppState.getInstance();
    private final ImageView image;
    private final ToolAction event;

    Tool(ToolAction event) {
        // if this ever fails, enum values don't line up with file names
        final var file = Objects.requireNonNull(getClass().getResource("/icons/" + name().toLowerCase() + ".png"));

        this.event = event;
        this.image = new ImageView(new Image(file.toString()));
        this.image.setFitWidth(30);
        this.image.setFitHeight(30);

        // trying to do mouseevent in layer
    }

    public static List<ImageView> getImageViews() {
        return Arrays.stream(Tool.values())
                .map(Tool::getImage)
                .toList();
    }

    public ImageView getImage() {
        return this.image;
    }

    public ToolAction getEvent() {
        return this.event;
    }

    @FunctionalInterface
    public interface ToolAction {
        void handle(MouseEvent e, GraphicsContext gc);
    }
}
