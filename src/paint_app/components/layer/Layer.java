package paint_app.components.layer;

import javafx.scene.canvas.Canvas;

public class Layer extends Canvas implements Comparable<Layer> {
    String name;
    int priority;

    public Layer(String name, int priority) {
        super();
        setStyle("-fx-background-color: transparent;");
        this.name = name;
        this.priority = priority;
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

    @Override
    public int compareTo(Layer o) {
        return Integer.compare(this.priority, o.getPriority());
    }
}
