package paint_app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import paint_app.components.Sidebar;
import paint_app.components.Toolbar;
import paint_app.components.Workspace;

import java.util.Objects;

/* TODO (descending priority):
    debug history feature (to the dungeon we go)
    finish up toolbar tools
    load style.css and replace its placeholders with AppColors
    shrink layer sidebar text field to fit only itself, expands too far right
    file saving
    help menu
*/

/* References
 * css
 * https://openjfx.io/javadoc/23/javafx.graphics/javafx/scene/doc-files/cssref.html
 * docs https://fxdocs.github.io/docs/html5/
 * API https://openjfx.io/javadoc/23/
 * colors https://catppuccin.com/palette#Macchiato
 * icons https://icons8.com/icon/set/rectangle/ios-filled
 */

public class Main extends Application {
    public static final double ZOOM_FACTOR = 1.0;
    public static final double ZOOM_SPEED = 0.1;
    public static final int HEIGHT = 800;
    public static final int WIDTH = 1280;
    private static final AppState AppState = paint_app.AppState.getInstance();

    public static void main(String[] args) {
        launch(args);
    }

    private static void handleKeyEvents(KeyEvent e) {
        final var color_prop1 = AppState.primaryColorProperty();
        final var color_prop2 = AppState.primaryColorProperty();

        switch (e.getCode()) {
            case KeyCode.X:
                final var tmp = color_prop1.get();
                color_prop1.set(color_prop2.get());
                color_prop2.set(tmp);
                break;
            case KeyCode.D:
                color_prop1.set(Color.BLACK);
                color_prop2.set(Color.WHITE);
                break;
        }
    }

    @Override
    public void start(Stage stage) {
        var root = new HBox();
        root.setBackground(AppColors.asBackground(AppColors.Base));

        var scene = new Scene(root, WIDTH, HEIGHT);
        scene.setOnKeyPressed(Main::handleKeyEvents);

        final var left_box = new VBox();
        final var tools = new Toolbar();
        final var workspace = new Workspace();
        final var sidebar = new Sidebar();
        final var spacer = new Region();

        spacer.prefHeightProperty().bind(left_box.heightProperty()
                .subtract(workspace.heightProperty())
                .subtract(tools.heightProperty())
                .divide(2));

        sidebar.setMinHeight(HEIGHT);

        left_box.getChildren().addAll(tools, spacer, workspace);
        left_box.setMinWidth(WIDTH - 300);
        left_box.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(left_box, sidebar);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Paint App");
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();

        AppState.synchronizeLayerComponents(workspace, sidebar);
        AppState.workspaceProperty().set(workspace);
    }
}
