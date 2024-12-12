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

///  Enum representing buttons on the toolbar
public enum ToolbarButton {
    BRUSH((state, e, gc) -> {
        final var color = getClickColor(state, e);
        if (color.isEmpty()) return;

        gc.setFill(color.get());
        gc.setStroke(color.get());
        gc.setLineWidth(state.strokeSizeProperty().get());
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

        double particles = state.strokeSizeProperty().get() * 5 / 3; // temp
        double size = state.strokeSizeProperty().get();

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
    OVAL((state, e, gc) -> releaseDrawShape(state, e, gc, 'o')),
    RECTANGLE((state, e, gc) -> releaseDrawShape(state, e, gc, 'r')),
    DROPPER((state, e, _) -> {
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
        // TODO
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
        double size = state.strokeSizeProperty().get();
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

    ///  Returns optional {@link Color} based on which mouse button was clicked.
    private static Optional<Color> getClickColor(AppState state, MouseEvent e) {
        return Optional.ofNullable(switch (e.getButton()) {
            case PRIMARY -> state.primaryColorProperty().get();
            case SECONDARY -> state.secondaryColorProperty().get();
            default -> null;
        });
    }

    /**
     * Handles the drawing of shapes on mouse events.
     * Using char as workaround to getting the enum name
     *
     * @param state The application state.
     * @param e     The mouse event.
     * @param gc    The graphics context to draw on.
     * @param shape The shape to be drawn ('o' for oval, 'r' for rectangle).
     */
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


            gc.setLineWidth(state.strokeSizeProperty().get());
            // improvement: make this a part of the UI
            // gc.setLineCap(StrokeLineCap.ROUND);
            // gc.setLineJoin(StrokeLineJoin.ROUND);

            if (state.doStrokeProperty().get()) {
                final var color = state.secondaryAsStrokeProperty().get()
                        ? state.secondaryColorProperty()
                        : state.primaryColorProperty();

                gc.setStroke(color.get());
                switch (shape) {
                    case 'o' -> gc.strokeOval(top_x, top_y, width, height);
                    case 'r' -> gc.strokeRect(top_x, top_y, width, height);
                    default -> AppState.getInstance().logger.info("no draw stroke implemented for " + shape);
                }
            }

            if (state.doFillProperty().get()) {
                gc.setFill(state.primaryColorProperty().get());

                switch (shape) {
                    case 'o' -> gc.fillOval(top_x, top_y, width, height);
                    case 'r' -> gc.fillRect(top_x, top_y, width, height);
                    default -> AppState.getInstance().logger.info("no draw fill implemented for " + shape);
                }
            } else gc.setFill(null);
        }
    }

    public ImageView getButton() {
        final var button = image;
//        button.setStyle("");
        button.setOnMouseClicked(e -> AppState.getInstance().currentToolProperty().set(this));
        return button;
    }

    public CanvasAction getEvent() {
        return this.event;
    }

    /// Action to be performed on layer
    @FunctionalInterface
    public interface CanvasAction {
        void handle(AppState AppState, MouseEvent e, GraphicsContext gc);
    }
}
