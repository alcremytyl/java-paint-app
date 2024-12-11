package paint_app.components;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import paint_app.AppColors;
import paint_app.AppState;
import paint_app.Helpers;

import java.util.Arrays;

public enum SidebarButton {
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
    }),
    Save(state -> state.saveWorkspaceAsImage(state.workspaceProperty().get())),
    Undo(state -> {
        state.undo();
    }),
    Redo(state -> {
        state.redo();
    });

    private final ImageView icon;
    private final LayerAction event;

    SidebarButton(LayerAction event) {
        this.event = event;
        this.icon = Helpers.getIcon(this.name().toLowerCase(), 30, 30);
        this.icon.setOnMouseClicked(e -> this.event.handle(AppState.getInstance()));
    }

    public static HBox getButtons() {
        final var buttons = Arrays.stream(SidebarButton.values())
                .map(SidebarButton::getButton)
                .peek(img -> HBox.setHgrow(img, Priority.ALWAYS))
                .toList();


        final var hbox = new HBox();
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(5);
        hbox.setBackground(AppColors.asBackground(AppColors.Surface0));
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        hbox.getChildren().addAll(buttons);
        hbox.getChildren().add(4, new Separator(Orientation.VERTICAL));

        return hbox;
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
