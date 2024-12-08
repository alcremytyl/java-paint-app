package paint_app.components;

import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import paint_app.AppColors;
import paint_app.AppState;
import paint_app.Helpers;

public class Layer extends Canvas {
    static final Background SELECTED_BG = AppColors.asBackground(AppColors.Crust);
    static final Background UNSELECTED_BG = AppColors.asBackground(Color.TRANSPARENT);
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
        text.setStyle("-fx-text-fill: " + AppColors.asHex(AppColors.Text) + "; -fx-background-color: transparent;");
        System.out.println(AppColors.asHex(AppColors.Text));

        visible_icon = Helpers.getIcon("visible", 20, 20);
        hidden_icon = Helpers.getIcon("hidden", 20, 20);

        final var visibility_checkbox = new Button();
        visibility_checkbox.setGraphic(visible_icon);
        visibility_checkbox.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");
        visibility_checkbox.setOnAction(e -> {
            setVisible(!isVisible());
            visibility_checkbox.setGraphic(isVisible() ? visible_icon : hidden_icon);
        });

        sidebar_content.setBackground(AppColors.asBackground(AppColors.Mantle));
        sidebar_content.setOnMouseClicked(e -> AppState.currentLayerProperty().set(this));
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

    public interface GraphicsContextUser {
        void use(GraphicsContext gc);
    }
}