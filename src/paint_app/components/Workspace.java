package paint_app.components;

import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import paint_app.AppState;

public class Workspace extends StackPane {
    private static final AppState AppState = paint_app.AppState.getInstance();

    public Workspace() {
        setMinSize(800, 600);
        setMaxSize(800, 600);
        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        setPadding(new Insets(100));

        this.setAlignment(Pos.BOTTOM_CENTER);

        AppState.layersProperty().addListener((ListChangeListener<? super Node>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    System.out.println("Added: " + change.getAddedSubList());
                }
                if (change.wasRemoved()) {
                    System.out.println("Removed: " + change.getRemoved());
                }
            }
        });

        getChildren().setAll(AppState.layersProperty());

/*
        AppState.layersProperty().addListener((a, b, c) -> {
        });
*/
    }

}
