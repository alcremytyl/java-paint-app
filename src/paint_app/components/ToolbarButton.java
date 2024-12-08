package paint_app.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import paint_app.AppState;
import paint_app.Helpers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum ToolbarButton {

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
    private final CanvasAction event;

    ToolbarButton(CanvasAction event) {
        this.event = event;
        this.image = Helpers.getIcon(this.name().toLowerCase(), 30, 30);
    }

    public static List<ImageView> getToolButtons() {
        return Arrays.stream(ToolbarButton.values())
                .map(ToolbarButton::getButton)
                .toList();
    }

    private static Optional<Color> getClickColor(AppState state, MouseEvent e) {
        return Optional.ofNullable(switch (e.getButton()) {
            case PRIMARY -> state.primaryColorProperty().get();
            case SECONDARY -> state.secondaryColorProperty().get();
            default -> null;
        });
    }

    // TODO: move to constructor
    public ImageView getButton() {
        final var button = image;
        button.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");
        button.setOnMouseClicked(e -> AppState.getInstance().currentToolProperty().set(this));
        return button;
    }

    public CanvasAction getEvent() {
        return this.event;
    }

    @FunctionalInterface
    public interface CanvasAction {
        void handle(AppState AppState, MouseEvent e, GraphicsContext gc);
    }
}
