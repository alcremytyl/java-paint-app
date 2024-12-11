package paint_app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import paint_app.components.Sidebar;
import paint_app.components.Toolbar;
import paint_app.components.Workspace;

import static paint_app.AppState.HEIGHT;
import static paint_app.AppState.WIDTH;

/* TODO (descending priority):
    debug history feature (to the dungeon we go)
    finish up toolbar tools
    load style.css and replace its placeholders with AppColors
    shrink layer sidebar text field to fit only itself, expands too far right
    help menu
*/

/* References
 * css
 * https://openjfx.io/javadoc/23/javafx.graphics/javafx/scene/doc-files/cssref.html
 * docs https://fxdocs.github.io/docs/html5/
 * API https://openjfx.io/javadoc/23/
 * colors https://catppuccin.com/palette#Macchiato/
 * icons https://icons8.com/icon/set/rectangle/ios-filled
 */

public class Main extends Application {
    private static final AppState AppState = paint_app.AppState.getInstance();

    public static void main(String[] args) {
        launch(args);
    }

    private static void handleKeyEvents(KeyEvent e) {
        final var color_prop1 = AppState.primaryColorProperty();
        final var color_prop2 = AppState.primaryColorProperty();
        final var current = AppState.currentLayerProperty().get();

        switch (e.getCode()) {
            case D: // reset to default colors
                color_prop1.set(Color.BLACK);
                color_prop2.set(Color.WHITE);
                break;
            case X: // swap colors
                final var tmp = color_prop1.get();
                color_prop1.set(color_prop2.get());
                color_prop2.set(tmp);
                break;
            case Z: // history
                if (e.isControlDown()) {
                    if (e.isShiftDown()) AppState.undo();
                    else AppState.redo();
                }
                break;
            case DELETE: // reset current layer
                if (current == null) break;

                current.getGraphicsContext2D().
                        clearRect(0, 0, Workspace.CANVAS_WIDTH, Workspace.CANVAS_HEIGHT);
                current.updatePreview();
                break;
        }
    }

    @Override
    public void start(Stage stage) {
        final var tools = new Toolbar();
        final var workspace = new Workspace();
        final var sidebar = new Sidebar();
        final var spacer = new Region();
        final var left_box = new VBox(tools, spacer, workspace);

        spacer.prefHeightProperty().bind(left_box.heightProperty()
                .subtract(workspace.heightProperty())
                .subtract(tools.heightProperty())
                .divide(2));

        sidebar.setMinHeight(HEIGHT);

        left_box.setMinWidth(WIDTH - 300);
        left_box.setAlignment(Pos.TOP_CENTER);

        var root = new HBox(left_box, sidebar);
        root.getStylesheets().add("data:text/css," + Helpers.loadCss());

        var scene = new Scene(root, WIDTH, HEIGHT);
        scene.setOnKeyPressed(Main::handleKeyEvents);

        stage.setScene(scene);
        stage.setTitle("Paint App");
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();

        AppState.synchronizeLayerComponents(workspace, sidebar);
    }
}
