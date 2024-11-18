package paint_app.components;

import javafx.geometry.Point2D;
import javafx.scene.input.GestureEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import paint_app.Globals;
import paint_app.InterfaceColors;

public class Workspace extends StackPane {
    StackPane layers;
    Point2D canvas_pos;
    Point2D mouse_pos;
    boolean is_dragging = false;
    Scale scale = new Scale(1.0, 1.0);

    public Workspace() {
        super();

        final var bg1 = new Background(new BackgroundFill(InterfaceColors.Crust, null, null));
        final var bg2 = new Background(new BackgroundFill(Color.WHITESMOKE, null, null));

        setBackground(bg1);

        layers = new StackPane();
        layers.setBackground(bg2);
        // TODO: auto scaling when res too big
        layers.setPrefSize(500, 500);
        layers.setMaxSize(500, 500);
        layers.getTransforms().add(scale);

        // FIXME: zooming not going beyond 0.7 after zooming out
        setOnScroll(e -> {
            System.out.println(scale);
            if (!isMouseInWorkspace(e)) {
                return;
            }

            var delta = (e.getDeltaY() > 0 ? 1 : -1) * Globals.ZOOM_SCALE_FACTOR;
            var new_x = scale.getX() + delta;
            var new_y = scale.getY() + delta;

            System.out.println("Attempting scale: " + new_x);
            System.out.println("Is valid scale range: " + (new_x >= Globals.ZOOM_SCALE_MIN && new_x <= Globals.ZOOM_SCALE_MAX));

            if (new_x >= Globals.ZOOM_SCALE_MIN && new_x <= Globals.ZOOM_SCALE_MAX) {
                scale.setX(new_x);
                scale.setY(new_y);
                layers.getTransforms().setAll(scale);
            }
        });

        getChildren().add(layers);
    }

    private boolean isMouseInWorkspace(GestureEvent e) {
        double x1 = e.getSceneX();
        double y1 = e.getSceneY();
        double x2 = getBoundsInLocal().getMinX();
        double y2 = getBoundsInLocal().getMinY();
        double w = getBoundsInLocal().getWidth();
        double h = getBoundsInLocal().getHeight();

        return (x1 >= x2 && x1 <= x2 + w)
                && (y1 >= y2 && y1 <= y2 + h);
    }
}
