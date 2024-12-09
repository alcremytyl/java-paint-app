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
import java.util.function.Consumer;

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
    }),
    OVAL((state, e, gc) -> {
        releaseDrawShape(state, e, gc, 'o');
    }),
    RECTANGLE((state, e, gc) -> {
        releaseDrawShape(state, e, gc, 'r');
    }),
    DROPPER((state, e, gc) -> {
        var currentLayer = state.currentLayerProperty().get();
        if (currentLayer != null) {
            var snapshot = currentLayer.snapshot(null, null);
            int x = (int) e.getX();
            int y = (int) e.getY();
            if (x >= 0 && y >= 0 && x < snapshot.getWidth() && y < snapshot.getHeight()) {
                var color = snapshot.getPixelReader().getColor(x, y);
                state.primaryColorProperty().set(color);
            }
        }
    }),
    TEXT((state, e, gc) -> {
        gc.setFill(state.primaryColorProperty().get());
        // TODO:
//        gc.setFont(state.textFont);
        String text = state.getTextToDraw();
        if (text != null && !text.isEmpty()) {
            gc.fillText(text, e.getX(), e.getY());
        }
    }),
    // would take a ton of time to implement, put under "what to improve"
    //    SELECT((state, e, gc) -> {
    //        double startX = state.getStartX();
    //        double startY = state.getStartY();
    //        double width = Math.abs(startX - e.getX());
    //        double height = Math.abs(startY - e.getY());
    //        gc.setStroke(state.selectionColor);
    //        gc.setLineWidth(1);
    //        gc.strokeRect(Math.min(startX, e.getX()), Math.min(startY, e.getY()), width, height);
    //    }),
    ERASER((state, e, gc) -> {
        double size = state.brushSizeProperty().get();
        // rectangular eraser due to performance issues
        gc.clearRect(
                e.getX() - size / 2,
                e.getY() - size / 2,
                size, size
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

    // using char as workaround to getting the enum name
    private static void releaseDrawShape(AppState state, MouseEvent e, GraphicsContext gc, char shape) {

        if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
            state.startXProperty().set(e.getX());
            state.startYProperty().set(e.getY());
        } else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            // TODO: preview
        } else if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
            final double x1 = state.startXProperty().get();
            final double y1 = state.startYProperty().get();

            double width = Math.abs(e.getX() - x1);
            double height = Math.abs(e.getY() - y1);

            double top_x = Math.min(x1, e.getX());
            double top_y = Math.min(y1, e.getY());


            final Consumer<Void> draw = switch (shape) {
                case 'o' -> (v) -> gc.fillOval(top_x, top_y, width, height);
                case 'r' -> (v) -> gc.fillRect(top_x, top_y, width, height);
                default -> null;
            };

            gc.setStroke(state.primaryColorProperty().get());
            gc.setLineWidth(state.brushSizeProperty().get());
            gc.setLineCap(StrokeLineCap.ROUND);
            gc.setLineJoin(StrokeLineJoin.ROUND);

            if (draw != null) draw.accept(null);
            else AppState.getInstance().logger.info("no shape draw implemented for " + shape);
        }
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
