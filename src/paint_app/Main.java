package paint_app;

import javafx.application.Application;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import paint_app.components.Sidebar;
import paint_app.components.Toolbar;
import paint_app.components.Workspace;


/* References
 * css https://openjfx.io/javadoc/18/javafx.graphics/javafx/scene/doc-files/cssref.html
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

        // TODO: use this to make sidebar and workspace sync up
        {
            var a = new HBox();
            SimpleListProperty<Node> ls = new SimpleListProperty<>(a.getChildren());

            ls.addListener((ListChangeListener<? super Node>) change -> {
                while (change.next()) {
                    if (change.wasAdded()) {
                        System.out.println("Added: " + change.getAddedSubList());
                    }
                    if (change.wasRemoved()) {
                        System.out.println("Removed: " + change.getRemoved());
                    }
                }
            });
        }

        System.exit(0);
        var root = new HBox();
        root.setBackground(new Background(new BackgroundFill(InterfaceColors.Base, null, null)));

        var scene = new Scene(root, WIDTH, HEIGHT);
        scene.setOnKeyPressed(Main::handleKeyEvents);

        final var left_box = new VBox();
        final var tools = new Toolbar();
        final var workspace = new Workspace();
        final var sidebar = new Sidebar();
        final var spacer = new Region();

        spacer.prefHeightProperty()
                .bind(left_box.heightProperty()
                        .subtract(workspace.heightProperty())
                        .subtract(tools.heightProperty())
                        .divide(2));

        stage.setOnShown(e -> {

            System.out.println(spacer.getHeight());
        });
        sidebar.setMinHeight(HEIGHT);

        left_box.getChildren().addAll(tools, spacer, workspace);
        left_box.setMinWidth(WIDTH - 300);
        left_box.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(left_box, sidebar);


        stage.setScene(scene);
        stage.setTitle("Paint App");
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }
}