package paint_app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import paint_app.components.Tools;

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
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 800;
    private static final AppState AppState = paint_app.AppState.getInstance();


    public static void main(String[] args) {
        launch(args);
    }

    private static void handleKeyEvents(KeyEvent e) {
        switch (e.getCode()) {
            case KeyCode.X:
                AppState.swapColors();
                break;
            case KeyCode.D:
                AppState.resetColors();
                break;
        }
    }

    @Override
    public void start(Stage stage) {
        var root = new VBox();
        root.setBackground(new Background(new BackgroundFill(InterfaceColors.Base, null, null)));

        var scene = new Scene(root, WIDTH, HEIGHT);
        scene.setOnKeyPressed(Main::handleKeyEvents);

        Rectangle top_panel = new Rectangle();
        top_panel.widthProperty().bind(root.widthProperty());
        top_panel.heightProperty().bind(root.heightProperty().multiply(0.25));
        top_panel.setFill(Color.LIGHTBLUE);

        Rectangle bottom_panel = new Rectangle();
        bottom_panel.widthProperty().bind(root.widthProperty());
        bottom_panel.heightProperty().bind(root.heightProperty().multiply(0.75));
        bottom_panel.setFill(Color.LIGHTGREEN);

        final var tools = new Tools();

        root.getChildren().addAll(tools);


        stage.setScene(scene);
        stage.setTitle("Paint App");
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }
}
