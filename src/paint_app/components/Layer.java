package paint_app.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import paint_app.InterfaceColors;

public class Layer extends Canvas {
    String name;
    ObjectProperty<ImageView> preview = new SimpleObjectProperty<>();


    public Layer(String name) {
        super(800, 600);
        this.name = name;

        preview.set(new ImageView());
        preview.get().setFitWidth(50);
        preview.get().setFitHeight(30);

        setStyle("-fx-background-color: transparent;");
        updatePreview();
    }

    public HBox asSidebarInteractive() {
        final var box = new HBox();
        box.setPrefSize(250, 30);
        box.setSpacing(35);

        final var visible_checkbox = new CheckBox();
        // TODO: this
        // visible_checkbox.setGraphic();


        final var text = new Label(this.name);
        text.setTextFill(InterfaceColors.Text);

        box.getChildren().addAll(visible_checkbox, preview.get(), text);
        return box;
    }

    public void useGraphicsContext(GraphicsContextUser gc) {
        gc.use(getGraphicsContext2D());
        this.updatePreview();
    }

    private void updatePreview() {
        final var snap = new SnapshotParameters();
        snap.setFill(Color.WHITE);
        final var shot = this.snapshot(snap, null);
        preview.get().setImage(shot);
    }

    public interface GraphicsContextUser {
        void use(GraphicsContext gc);
    }
}
