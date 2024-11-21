package paint_app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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
 * icons https://icons8.com/icon/set/rectangle/ios-filled
 */

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        var root = new VBox();
//        root.getStylesheets().add("style.css");
//        root.setBackground(new Background(new BackgroundFill(InterfaceColors.Base, null, null)));
//        root.setMinSize(Globals.WIDTH, Globals.HEIGHT);
//
        var scene = new Scene(root, Globals.WIDTH, Globals.HEIGHT);

        Rectangle top_panel = new Rectangle();
        top_panel.widthProperty().bind(root.widthProperty());
        top_panel.heightProperty().bind(root.heightProperty().multiply(0.25));
        top_panel.setFill(Color.LIGHTBLUE);

        Rectangle bottom_panel = new Rectangle();
        bottom_panel.widthProperty().bind(root.widthProperty());
        bottom_panel.heightProperty().bind(root.heightProperty().multiply(0.75));
        bottom_panel.setFill(Color.LIGHTGREEN);

        // Add the panels to the VBox
        root.getChildren().addAll(top_panel, bottom_panel);


//        var top_panel = new Tools();
//        top_panel.minWidthProperty().bind(root.widthProperty());
//        top_panel.maxWidthProperty().bind(root.widthProperty());
//
//        var bottom_panel = new HBox();
//        bottom_panel.setSpacing(10);
//        bottom_panel.minWidthProperty().bind(root.widthProperty());
//        bottom_panel.maxWidthProperty().bind(root.widthProperty());
//        bottom_panel.minHeightProperty().bind(root.heightProperty());
//        bottom_panel.maxHeightProperty().bind(root.heightProperty());
//
//        var workspace = new Workspace();
//        workspace.minWidthProperty().bind(bottom_panel.widthProperty().subtract(210));
//        workspace.minHeightProperty().bind(bottom_panel.heightProperty());
//
//        var dock = makePlaceholder(200, 100, Color.PALEGREEN);
//        dock.minWidth(200);
//        dock.maxWidth(200);
//        dock.heightProperty().bind(bottom_panel.heightProperty());
//
//        root.getChildren().addAll(top_panel, bottom_panel);
//        bottom_panel.getChildren().addAll(workspace, dock);
//

        stage.setScene(scene);
        stage.setTitle("Paint App");
        stage.setWidth(Globals.WIDTH);
        stage.setHeight(Globals.HEIGHT);
        stage.setResizable(false);
        stage.show();
    }
}
