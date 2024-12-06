package paint_app.components;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import paint_app.AppState;
import paint_app.InterfaceColors;

import java.util.Objects;

public class Layer extends Canvas {
    final ImageView visible_icon;
    final ImageView hidden_icon;


    final ImageView preview;
    final HBox sidebar_content;
    AppState AppState = paint_app.AppState.getInstance();
    String name;

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

        final var visible_file = Objects.requireNonNull(Layer.class.getResource("/icons/visible.png")).toString();
        final var hidden_file = Objects.requireNonNull(Layer.class.getResource("/icons/hidden.png")).toString();

        // TODO: center images
        visible_icon = new ImageView(new Image(visible_file));
        visible_icon.setFitWidth(20);
        visible_icon.setFitHeight(20);

        hidden_icon = new ImageView(new Image(hidden_file));
        hidden_icon.setFitWidth(20);
        hidden_icon.setFitHeight(20);

        final var visibility_checkbox = new Button();
        visibility_checkbox.setGraphic(visible_icon);
        visibility_checkbox.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");
        visibility_checkbox.setOnAction(e -> {
            setVisible(!isVisible());
            final var icon = isVisible() ? visible_icon : hidden_icon;
            visibility_checkbox.setGraphic(icon);
        });


        sidebar_content.getChildren().addAll(visibility_checkbox, preview, text);
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

    public void updatePreview() {
        final var snap = new SnapshotParameters();
        snap.setFill(Color.WHITE);
        final var shot = this.snapshot(snap, null);
        preview.setImage(shot);
    }

    public void toSelected() {
        sidebar_content.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
    }

    public void unSelect() {
        sidebar_content.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
    }

    public interface GraphicsContextUser {
        void use(GraphicsContext gc);
    }
}
