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
    }),
    RECTANGLE((e, gc) -> {
    }),
    DROPPER((e, gc) -> {
    }),
    TEXT((e, gc) -> {
    }),
    SELECT((e, gc) -> {
    }),
    ERASER((e, gc) -> {
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
