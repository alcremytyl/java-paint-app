package paint_app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/* TODO
 * toolbox
 * - brushes
 * - color picker logic
 * layer implementation
 * consult the drawing for other UI aspects to do
 * everything else
 */

/* References
 * css https://openjfx.io/javadoc/18/javafx.graphics/javafx/scene/doc-files/cssref.html
 * docs https://fxdocs.github.io/docs/html5/
 * API https://openjfx.io/javadoc/23/
 * colors https://catppuccin.com/palette#Macchiato
 */

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        var root = new VBox();
        var scene = new Scene(root, Constants.WIDTH, Constants.HEIGHT);

        var toolbox = new Toolbox();
        // 13.5% of screen
        toolbox.setMaxHeight(Constants.HEIGHT * 27 / 200.0);
        toolbox.setMinHeight(Constants.HEIGHT * 27 / 200.0);

        stage.setOnShown(e -> {
            System.out.println(toolbox.getHeight());
            System.out.println(toolbox.getChildren().get(1));
        });

        root.getChildren().add(toolbox);
        root.getStylesheets().add("style.css");
        root.getStyleClass().add("root-theme");

        stage.setScene(scene);
        stage.setTitle("Paint App");

        stage.show();
    }
}
