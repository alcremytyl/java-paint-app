package paint_app.components;

import javafx.scene.image.ImageView;
import paint_app.AppState;
import paint_app.Helpers;

import java.util.Arrays;
import java.util.List;

public enum LayerButton {
    Add(state -> state.layersProperty().add(new Layer("Untitled"))),
    Remove(state -> state.layersProperty().remove(state.currentLayerProperty().get())),
    ShiftUp(state -> {
        final var layers = state.layersProperty().get();
        final var current = state.currentLayerProperty();

        final int size = layers.size();
        final int idx = layers.indexOf(current.get());

        if (idx == size - 1) {
            state.logger.info("current layer at highest");
        } else {
            current.set(layers.get(idx + 1));
        }
    }),
    ShiftDown(state -> {
        final var layers = state.layersProperty().get();
        final var current = state.currentLayerProperty();

        final int idx = layers.indexOf(current.get());

        if (idx == 0) {
            state.logger.info("current layer at lowest");
        } else {
            current.set(layers.get(idx - 1));
        }
    });

    private final ImageView icon;
    private final LayerAction event;

    LayerButton(LayerAction event) {
        this.event = event;
        this.icon = Helpers.getIcon(this.name().toLowerCase(), 30, 30);
        this.icon.setOnMouseClicked(e -> this.event.handle(AppState.getInstance()));
    }

    public static List<ImageView> getButtons() {
        return Arrays.stream(LayerButton.values())
                .map(LayerButton::getButton)
                .toList();
    }

    public ImageView getButton() {
        return this.icon;
    }

//    public LayerAction getEvent() {
//        return this.event;
//    }

    @FunctionalInterface
    public interface LayerAction {
        void handle(AppState state);
    }
}
