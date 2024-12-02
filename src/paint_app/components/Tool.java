package paint_app.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum Tool {
    // TODO: tool events
    BRUSH(gc -> {

    }),
    SPRAY(gc -> {
    }),
    CIRCLE(gc -> {
    }),
    RECTANGLE(gc -> {
    }),
    DROPPER(gc -> {
    }),
    TEXT(gc -> {
    }),
    SELECT(gc -> {
    }),
    ERASER(gc -> {
    });

    private final ImageView image;
    private final ToolAction event;

    Tool(ToolAction event) {
        this.event = event;
        this.image = new ImageView(new Image(
                Objects.requireNonNull(getClass().getResource("/icons/" + this.name().toLowerCase() + ".png")).toString()
        ));
        this.image.setFitWidth(30);
        this.image.setFitHeight(30);
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
        void handle(GraphicsContext gc);
    }
}
