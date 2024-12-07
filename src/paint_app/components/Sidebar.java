package paint_app.components;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import paint_app.AppState;
import paint_app.InterfaceColors;
import paint_app.Main;

public class Sidebar extends VBox {
    private final AppState AppState = paint_app.AppState.getInstance();
    private final VBox layers = new VBox();
    private final TextField textInputField = new TextField();

    public Sidebar() {
        final var text_style = "-fx-font-weight: bold; -fx-padding: 10px 0px;";

        final var hist_label = new Label("History");
        hist_label.setTextFill(InterfaceColors.Text);
        hist_label.setStyle(text_style);
        final var history = new Rectangle(300, (double) Main.HEIGHT / 3);

        final var layers_label = new Label("Layers");
        layers_label.setTextFill(InterfaceColors.Text);
        layers_label.setStyle(text_style);

        var sep = new Separator(Orientation.HORIZONTAL);
        sep.setPadding(new Insets(30, 0, 30, 0));

        textInputField.setPromptText("Enter text:");
        textInputField.setVisible(false);
        textInputField.textProperty().addListener((observable, oldValue, newValue) -> {
            AppState.setTextToDraw(newValue);
        })

        ChangeListener<Tool> toolChangeListener = (observable, oldTool, newTool) -> {
            if (newTool == Tool.TEXT) {
                textInputField.setVisible(true);
            } else {
                textInputField.setVisible(false);
            }
        };
        
        AppState.currentToolProperty().addListener(toolChangeListener);

        getChildren().addAll(hist_label, history, sep, layers_label, layers, textInputField);
    }

    public VBox getLayers() {
        return this.layers;
    }
}

