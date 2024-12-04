package paint_app.components;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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

    public void useGraphicsContext(GraphicsContextUser gc) {
        gc.use(getGraphicsContext2D());
        this.updatePreview();
    }

    private void updatePreview() {
        final var snap = new SnapshotParameters();
        snap.setFill(Color.WHITE);
        final var shot = this.snapshot(snap, null);
        preview.setImage(shot);
    }

    public void toHighlight() {
        sidebar_content.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
// TODO: urgent, focus on getting this to work Mr. GPT
//        addEventFilter(MouseEvent.MOUSE_PRESSED, );

    }

    public void unHighlight() {
        sidebar_content.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
    }

    public interface GraphicsContextUser {
        void use(GraphicsContext gc);
    }
}
