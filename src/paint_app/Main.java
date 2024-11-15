package paint_app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import paint_app.layer.Layer;

/* TODO
 * toolbox
 * - brushes
 * - color picker logic
 * layer implementation
 * consult the drawing for other UI aspects to do
 * everything else
 * put everything in boxes so it just pads out as the window grows
 *
 * maybe:
 * - button to move current layer up/down
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

    static Rectangle makePlaceholder(Region root) {
        var rect = new Rectangle();
        rect.widthProperty().bind(root.widthProperty());
        rect.heightProperty().bind(root.heightProperty());
        rect.setFill(Color.RED);


        return rect;
    }

    @Override
    public void start(Stage stage) {
        var root = new HBox();
        root.getStylesheets().add("style.css");
        root.getStyleClass().add("root-theme");

        var lroot = new VBox();
        var scene = new Scene(root, Globals.WIDTH, Globals.HEIGHT);

        var toolbox = new Toolbox();
        // 13.5% of screen
        toolbox.setMaxHeight(Globals.HEIGHT * 27 / 200.0);
        toolbox.setMinHeight(Globals.HEIGHT * 27 / 200.0);

        var placeholder_layer = new Layer();

        root.getChildren().addAll(lroot, makePlaceholder(root));
        lroot.getChildren().addAll(toolbox, placeholder_layer);

        stage.setScene(scene);
        stage.setTitle("Paint App");

        stage.show();
    }
}
