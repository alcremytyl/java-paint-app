package paint_app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import paint_app.components.Layer;
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

            // TODO: delete when done
            case KeyCode.A:
                System.out.println(AppState.layersProperty());
                AppState.layersProperty().add(new Layer(""));
                break;
        }
    }

    @Override
    public void start(Stage stage) {
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

        sidebar.setMinHeight(HEIGHT);

//        left_box.getChildren().addAll(tools, spacer, workspace);
        left_box.setMinWidth(WIDTH - 300);
        left_box.setAlignment(Pos.TOP_CENTER);
//        root.getChildren().addAll(left_box, sidebar);

        var l1 = new Layer("aa");
        var l2 = new Layer("bb");

        l1.useGraphicsContext(gc -> {
            gc.setFill(Color.BLACK);
            gc.fillOval(200, 200, 200, 100);
        });

        l2.useGraphicsContext(gc -> {
            gc.setFill(Color.RED);
            gc.fillOval(250, 250, 100, 100);
        });

        var vbox = new VBox();
        vbox.setSpacing(20);

        var pa = new StackPane();
        pa.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        pa.getChildren().addAll(l1, l2);

        vbox.getChildren().addAll(pa, l1.asSidebarInteractive(), l2.asSidebarInteractive());
        root.getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.setTitle("Paint App");
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();


    }
}