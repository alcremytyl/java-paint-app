package paint_app.components;

import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import paint_app.AppState;
import paint_app.Helpers;

/**
 * Represents a Layer for {@link paint_app.components.Workspace}
 */
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
        final var preview_pane = new StackPane(preview); // for border to render
        preview_pane.getStyleClass().add("preview");

        final var text = new TextField(this.name);
        text.setPrefWidth(120);

        // ensure text fills up entire box
        text.textProperty().addListener((obs, o, n) -> {
            var t = new Text(n);
            t.setFont(text.getFont());
            if (t.getLayoutBounds().getWidth() + 15 > text.getWidth()) {
                text.setText(o);
            }
        });

        visible_icon = Helpers.getIcon("visible", 20, 20);
        hidden_icon = Helpers.getIcon("hidden", 20, 20);

        final var visibility_checkbox = new Button();
        visibility_checkbox.setGraphic(visible_icon);
        visibility_checkbox.setOnAction(_ -> {
            setVisible(!isVisible());
            visibility_checkbox.setGraphic(isVisible() ? visible_icon : hidden_icon);
        });

//        sidebar_content.setBackground(AppColor.asBackground(AppColor.Mantle));
        sidebar_content = new HBox(visibility_checkbox, preview_pane, text);
        sidebar_content.setPrefSize(250, 30);
//        sidebar_content.setSpacing(20);
        sidebar_content.getStyleClass().add("sidebar-layer");
        sidebar_content.setOnMouseClicked(_ -> AppState.currentLayerProperty().set(this));
        sidebar_content.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(sidebar_content, Priority.ALWAYS);

        setStyle("-fx-background-color: transparent;");
        updatePreview();
    }

    /**
     * Returns the sidebar content.
     *
     * @return The HBox representing the sidebar content.
     */
    public HBox getSidebarContent() {
        return this.sidebar_content;
    }

    /// Updates the side content preview.
    public void updatePreview() {
        final var snap = new SnapshotParameters();
        snap.setFill(Color.WHITE);
        final var shot = this.snapshot(snap, null);
        preview.setImage(shot);
    }

    ///  Applies `selected` theme
    public void toSelected() {
        sidebar_content.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
    }

    ///  Removes `selected` theme
    public void unSelect() {
//        sidebar_content.setBackground(UNSELECTED_BG);
        sidebar_content.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
    }

    public Layer clone() {
        Layer clonedLayer = new Layer(this.name);

        var gc = clonedLayer.getGraphicsContext2D();
        gc.drawImage(this.snapshot(null, null), 0, 0);

        clonedLayer.sidebar_content.setBackground(this.sidebar_content.getBackground());
        return clonedLayer;
    }

    /**
     * Restores the state of the layer from another layer.
     *
     * @param layer The layer from which to restore the state.
     */
    public void restoreState(Layer layer) {
        this.getGraphicsContext2D().clearRect(0, 0, this.getWidth(), this.getHeight());

        this.getGraphicsContext2D().drawImage(layer.snapshot(null, null), 0, 0);

        this.name = layer.name;
        this.sidebar_content.setBackground(layer.sidebar_content.getBackground());

        this.updatePreview();
    }
}