package paint_app.components;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import paint_app.AppColors;
import paint_app.AppState;

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

    }
}
