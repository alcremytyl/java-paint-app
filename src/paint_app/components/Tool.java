package paint_app.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import paint_app.AppState;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public enum Tool {

    // TODO: tool events
    BRUSH((state, e, gc) -> {
        final var color = getClickColor(state, e);
        if (color.isEmpty()) return;

        gc.setFill(color.get());
        gc.setStroke(color.get());
        gc.setLineWidth(state.brushSizeProperty().get());
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.setLineJoin(StrokeLineJoin.ROUND);

        if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
            gc.beginPath();
            gc.moveTo(e.getX(), e.getY());
        } else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
        }
    }),
    SPRAY((state, e, gc) -> {
        final var color = getClickColor(state, e);
        if (color.isEmpty()) return;

        double particles = state.brushSizeProperty().get() * 5 / 3; // temp
        double size = state.brushSizeProperty().get();

        gc.setFill(color.get());

        for (int i = 0; i < particles; i++) {
            // mouse + offset
            double x = e.getX() + Math.random() * size - (size / 2);
            double y = e.getY() + Math.random() * size - (size / 2);

            gc.fillOval(
                    x, y, 2, 2
            );
        }
    });
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

    private final ImageView image;
    private final ToolAction event;

    Tool(ToolAction event) {
        // if this ever fails, enum values don't line up with file names
        final var file = Objects.requireNonNull(getClass().getResource("/icons/" + name().toLowerCase() + ".png"));

        this.event = event;
        this.image = new ImageView(new Image(file.toString()));
        this.image.setFitWidth(30);
        this.image.setFitHeight(30);
    }

    public static List<Button> getToolButtons() {
        return Arrays.stream(Tool.values())
                .map(Tool::getImage)
                .toList();
    }

    private static Optional<Color> getClickColor(AppState state, MouseEvent e) {
        return Optional.ofNullable(switch (e.getButton()) {
            case PRIMARY -> state.primaryColorProperty().get();
            case SECONDARY -> state.secondaryColorProperty().get();
            default -> null;
        });
    }

    public Button getImage() {
        final var button = new Button();
        button.setGraphic(this.image);
        button.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");
        button.setOnAction(e -> {
            AppState.getInstance().currentToolProperty().set(this);
        });
        return button;
    }

    public ToolAction getEvent() {
        return this.event;
    }

    @FunctionalInterface
    public interface ToolAction {
        void handle(AppState AppState, MouseEvent e, GraphicsContext gc);
    }
}
