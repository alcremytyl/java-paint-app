package paint_app.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import paint_app.AppState;

public class Workspace extends StackPane {
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 600;

    private static final AppState AppState = paint_app.AppState.getInstance();

    public Workspace() {
        setMinSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        setMaxSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        setPadding(new Insets(100));
        setAlignment(Pos.BOTTOM_CENTER);
        getChildren().setAll(AppState.layersProperty());

    }
}
