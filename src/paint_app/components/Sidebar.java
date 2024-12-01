package paint_app.components;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import paint_app.InterfaceColors;
import paint_app.Main;

public class Sidebar extends VBox {
    public Sidebar() {
        final var text_style = "-fx-font-weight: bold; -fx-padding: 10px 0px;";

        final var hist_label = new Label("History");
        hist_label.setTextFill(InterfaceColors.Text);
        hist_label.setStyle(text_style);
        final var history = new Rectangle(300, (double) Main.HEIGHT / 3);

        final var layers_label = new Label("Layers");
        layers_label.setTextFill(InterfaceColors.Text);
        layers_label.setStyle(text_style);
        final var layers = new Rectangle(300, (double) Main.HEIGHT * 2 / 3);

        var sep = new Separator(Orientation.HORIZONTAL);
        sep.setPadding(new Insets(30, 0, 30, 0));

        getChildren().addAll(hist_label, history, sep, layers_label, layers);
    }
}
