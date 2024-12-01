package paint_app.components.layer;

import javafx.scene.canvas.Canvas;

public class Layer extends Canvas {
    String name;

    public Layer(String name) {
        setStyle("-fx-background-color: transparent;");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Layer setName(String name) {
        this.name = name;
        return this;
    }
}
