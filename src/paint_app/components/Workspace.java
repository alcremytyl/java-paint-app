package paint_app.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Workspace extends StackPane {
    public Workspace() {
        setMinSize(800, 600);
        setMaxSize(800, 600);
        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        setPadding(new Insets(100));

        this.setAlignment(Pos.BOTTOM_CENTER);
    }

}
