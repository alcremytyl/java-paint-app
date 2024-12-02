package paint_app.components;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;

public class Layer extends Canvas {
    public GraphicsContext gc;
    private String name;


    public Layer(String name) {
        setStyle("-fx-background-color: transparent;");
        this.name = name;
        gc = this.getGraphicsContext2D();
    }

    public String getName() {
        return name;
    }

    public Layer setName(String name) {
        this.name = name;
        return this;
    }

    public HBox asSidebarInteractible() {
        final var box = new HBox();
        // TODO everything
        return box;
    }
}
