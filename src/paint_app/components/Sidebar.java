package paint_app.components;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import paint_app.AppColors;
import paint_app.AppState;
import paint_app.Main;

public class Sidebar extends VBox {
    private static final String TILEDPANE_TEXT_THEME = String.format("""
             -fx-font-weight: bold;
             -fx-padding: 10px 0px;
             -fx-text-fill: %s;
             .accordion {
             -fx-background-color: %s;
            }""", AppColors.asHex(AppColors.Text), AppColors.asHex(AppColors.Mantle));

    private final AppState AppState = paint_app.AppState.getInstance();
    private final VBox layers = new VBox();

    public Sidebar() {
        final var text_style = "-fx-font-weight: bold; -fx-padding: 10px 0px;";

        final var hist_label = new Label("History");
        hist_label.setTextFill(AppColors.Text);
        hist_label.setStyle(text_style);

        final var history = new Rectangle(300, (double) Main.HEIGHT / 3);

        final var layers_label = new Label("Layers");
        layers_label.setTextFill(AppColors.Text);
        layers_label.setStyle(text_style);


        layers.setSpacing(10);
        layers.setBackground(AppColors.asBackground(AppColors.Mantle));
        layers.setPrefWidth(Double.MAX_VALUE);

        // setBackground does nothing for ScrollPane specifically
        final var layers_scrollpane = new ScrollPane(layers);
        layers_scrollpane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        layers_scrollpane.setFitToWidth(true);
        layers_scrollpane.setFitToHeight(true);
        layers_scrollpane.setPrefWidth(Double.MAX_VALUE);
        VBox.setVgrow(layers_scrollpane, Priority.ALWAYS);

        /** TODO
         * add
         * remove
         * shift up/down
         *
         */
        final var layer_manager = new HBox();
        layer_manager.setPadding(new Insets(10));
        layer_manager.setBackground(AppColors.asBackground(AppColors.Surface0));
        layer_manager.getChildren().addAll(LayerButton.getButtons());
        layer_manager.setAlignment(Pos.BOTTOM_CENTER);

        final var layer_pane = new TitledPane("layers", layers_scrollpane);

        final var spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        final var sep = new Separator(Orientation.HORIZONTAL);
        sep.setPadding(new Insets(30, 0, 0, 0));

        getChildren().addAll(hist_label, history, layer_pane, spacer, sep, layer_manager);
    }


    public VBox getLayers() {
        return this.layers;
    }
}
