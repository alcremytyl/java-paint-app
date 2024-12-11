package paint_app.components;

import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import paint_app.AppState;
import paint_app.Helpers;

public class Layer extends Canvas implements Cloneable {
    static final Background SELECTED_BG = null;
    static final Background UNSELECTED_BG = null;
    final AppState AppState = paint_app.AppState.getInstance();
    final HBox sidebar_content;
    final ImageView hidden_icon;
    final ImageView preview;
    final ImageView visible_icon;
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

        final var text = new TextField(this.name);
//        text.setStyle("-fx-text-fill: " + AppColor.asHex(AppColor.Text) + "; -fx-background-color: transparent;");

        visible_icon = Helpers.getIcon("visible", 20, 20);
        hidden_icon = Helpers.getIcon("hidden", 20, 20);

        final var visibility_checkbox = new Button();
        visibility_checkbox.setGraphic(visible_icon);
        visibility_checkbox.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");
        visibility_checkbox.setOnAction(_ -> {
            setVisible(!isVisible());
            visibility_checkbox.setGraphic(isVisible() ? visible_icon : hidden_icon);
        });

//        sidebar_content.setBackground(AppColor.asBackground(AppColor.Mantle));
        sidebar_content.setOnMouseClicked(_ -> AppState.currentLayerProperty().set(this));
        sidebar_content.setAlignment(Pos.CENTER_LEFT);
        sidebar_content.getChildren().addAll(visibility_checkbox, preview, text);
        HBox.setHgrow(sidebar_content, Priority.ALWAYS);

        setStyle("-fx-background-color: transparent;");
        updatePreview();
    }

    public HBox getSidebarContent() {
        return this.sidebar_content;
    }

    public void updatePreview() {
        final var snap = new SnapshotParameters();
        snap.setFill(Color.WHITE);
        final var shot = this.snapshot(snap, null);
        preview.setImage(shot);
    }

    public void toSelected() {
        sidebar_content.setBackground(SELECTED_BG);
    }

    public void unSelect() {
        sidebar_content.setBackground(UNSELECTED_BG);
    }

    public Layer clone() {
        Layer clonedLayer = new Layer(this.name);

        var gc = clonedLayer.getGraphicsContext2D();
        gc.drawImage(this.snapshot(null, null), 0, 0);

        clonedLayer.sidebar_content.setBackground(this.sidebar_content.getBackground());
        return clonedLayer;
    }

    public void restoreState(Layer layer) {
        this.getGraphicsContext2D().clearRect(0, 0, this.getWidth(), this.getHeight());

        this.getGraphicsContext2D().drawImage(layer.snapshot(null, null), 0, 0);

        this.name = layer.name;
        this.sidebar_content.setBackground(layer.sidebar_content.getBackground());

        this.updatePreview();
    }
}