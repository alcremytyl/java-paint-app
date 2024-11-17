package paint_app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import paint_app.components.Tools;
import paint_app.components.Workspace;

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
 *
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

    public static Rectangle makePlaceholder(int w, int h, Color c) {
        var rect = new Rectangle();
        rect.setWidth(w);
        rect.setHeight(h);
        rect.setFill(c);
        return rect;
    }

    @Override
    public void start(Stage stage) {
        var root = new VBox();
        root.getStylesheets().add("style.css");
        root.setBackground(new Background(new BackgroundFill(InterfaceColors.Base, null, null)));
        root.setMinSize(Globals.WIDTH, Globals.HEIGHT);

        var scene = new Scene(root, Globals.WIDTH, Globals.HEIGHT);

        var top_panel = new Tools();
        top_panel.minWidthProperty().bind(root.widthProperty());
        top_panel.maxWidthProperty().bind(root.widthProperty());

        var bottom_panel = new HBox();
        bottom_panel.setSpacing(10);
        bottom_panel.minWidthProperty().bind(root.widthProperty());
        bottom_panel.maxWidthProperty().bind(root.widthProperty());
        bottom_panel.minHeightProperty().bind(root.heightProperty());
        bottom_panel.maxHeightProperty().bind(root.heightProperty());

        var workspace = new Workspace();
        workspace.minWidthProperty().bind(bottom_panel.widthProperty().subtract(210));
        workspace.minHeightProperty().bind(bottom_panel.heightProperty());

        var dock = makePlaceholder(200, 100, Color.PALEGREEN);
        dock.minWidth(200);
        dock.maxWidth(200);
        dock.heightProperty().bind(bottom_panel.heightProperty());

        root.getChildren().addAll(top_panel, bottom_panel);
        bottom_panel.getChildren().addAll(workspace, dock);

        stage.setScene(scene);
        stage.setTitle("Paint App");
        stage.setMinWidth(Globals.WIDTH);
        stage.setMinHeight(Globals.HEIGHT);
        stage.show();
    }
}
