package paint_app.layer;

import javafx.scene.canvas.Canvas;

public class Layer {
    String name;
    int priority;
    Canvas canvas;

    Layer() {
    }

    Layer(String name, int priority, Canvas canvas) {
        this.name = name;
        this.priority = priority;
        this.canvas = canvas;
    }

    public String getName() {
        return name;
    }

    public Layer setName(String name) {
        this.name = name;
        return this;
    }

    public int getPriority() {
        return priority;
    }

    public Layer setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Layer setCanvas(Canvas canvas) {
        this.canvas = canvas;
        return this;
    }
}
