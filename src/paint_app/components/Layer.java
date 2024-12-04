package paint_app.components;

import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import paint_app.AppState;
import paint_app.InterfaceColors;

public class Layer extends Canvas {
    final ImageView preview;
    final HBox sidebar_content;
    AppState AppState = paint_app.AppState.getInstance();
    String name;
    CheckBox visible_checkbox;

    EventHandler<MouseEvent> handler;

    public Layer(String name) {
        super(800, 600);
        this.name = name;

        preview = new ImageView();
        preview.setFitWidth(50);
        preview.setFitHeight(30);

        sidebar_content = new HBox();
        sidebar_content.setPrefSize(250, 30);
        sidebar_content.setSpacing(35);

        final var text = new Label(this.name);
        text.setTextFill(InterfaceColors.Text);
        visible_checkbox = new CheckBox();
//        visible_checkbox.setGraphic();
        sidebar_content.getChildren().addAll(visible_checkbox, preview, text);
        sidebar_content.setOnMouseClicked(e -> {
            AppState.currentLayerProperty().set(this);
        });


        setStyle("-fx-background-color: transparent;");
        updatePreview();

    }

    public HBox getSidebarContent() {
        return this.sidebar_content;
    }

//    public void useGraphicsContext(GraphicsContextUser gc) {
//        gc.use(getGraphicsContext2D());
//        this.updatePreview();
//    }

    private void updatePreview() {
        final var snap = new SnapshotParameters();
        snap.setFill(Color.WHITE);
        final var shot = this.snapshot(snap, null);
        preview.setImage(shot);
    }

    public void toSelected() {

        final EventHandler<MouseEvent> handler = e -> {
            AppState.currentToolProperty().get().getEvent().handle(e, getGraphicsContext2D());
            updatePreview();
        };

        sidebar_content.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));


        addEventFilter(MouseEvent.MOUSE_CLICKED, handler);
        addEventFilter(MouseEvent.MOUSE_DRAGGED, handler);
    }

    public void unSelect() {
        sidebar_content.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        // TODO: removal
//        removeEventFilter(MouseEvent.MOUSE_CLICKED, );
    }

//    public interface GraphicsContextUser {
//        void use(GraphicsContext gc);
//    }
}
