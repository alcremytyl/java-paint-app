package paint_app.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

//        AppState.layersProperty().addListener((ListChangeListener<? super Node>) change -> {
//            while (change.next()) {
//                if (change.wasAdded()) {
//                    this.getChildren().addAll(change.getAddedSubList());
//                } else if (change.wasRemoved()) {
//                    this.getChildren().removeAll(change.getRemoved());
//                }
//            }
//        });

        getChildren().setAll(AppState.layersProperty());

    }
}
