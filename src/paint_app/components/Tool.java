package paint_app.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import paint_app.AppState;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum Tool {

    // TODO: tool events
    BRUSH((state, e, gc) -> {
        final Color color = switch (e.getButton()) {
            case PRIMARY -> state.primaryColorProperty().get();
            case SECONDARY -> state.secondaryColorProperty().get();
            default -> null;
        };

        if (color == null) {
            return;
        }

        double size = state.brushSizeProperty().get();

        gc.setFill(color);
        gc.fillOval(e.getX(), e.getY(), size, size);

    });
//    SPRAY((e, gc) -> {
//    }),
//    CIRCLE((e, gc) -> {
//    }),
//    RECTANGLE((e, gc) -> {
//    }),
//    DROPPER((e, gc) -> {
//    }),
//    TEXT((e, gc) -> {
//    }),
//    SELECT((e, gc) -> {
//    }),
//    ERASER((e, gc) -> {
//    });

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
        void handle(AppState AppState, MouseEvent e, GraphicsContext gc);
    }
}
