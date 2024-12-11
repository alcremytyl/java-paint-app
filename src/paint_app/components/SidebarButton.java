package paint_app.components;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import paint_app.AppState;
import paint_app.Helpers;

import java.io.File;
import java.util.Arrays;

public enum SidebarButton {
    Add(state -> state.layersProperty().add(new Layer("Untitled"))),
    Remove(SidebarButton::removeCurrent),
    ShiftUp(SidebarButton::currentShiftUp),
    ShiftDown(SidebarButton::currentShiftDown),
    Save(SidebarButton::saveWorkspaceAsImage),
    Undo(AppState::undo),
    Redo(AppState::redo);

    private final ImageView button;
    private final LayerAction event;

    SidebarButton(LayerAction event) {
        this.event = event;
        this.button = Helpers.getIcon(this.name().toLowerCase(), 30, 30);
        this.button.setOnMouseClicked(e -> this.event.handle(AppState.getInstance()));
    }

    public static HBox getButtons() {
        final var buttons = Arrays.stream(SidebarButton.values())
                .map(SidebarButton::getButton)
                .peek(img -> HBox.setHgrow(img, Priority.ALWAYS))
                .toList();


        final var hbox = new HBox();
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(5);
//        hbox.setBackground(AppColor.asBackground(AppColor.Surface0));
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        hbox.getChildren().addAll(buttons);
        hbox.getChildren().add(4, new Separator(Orientation.VERTICAL));

        return hbox;
    }

    private static void removeCurrent(AppState state) {
        final var layers = state.layersProperty().get();
        if (layers.isEmpty()) return;

        final var current = state.currentLayerProperty();

        layers.remove(current.get());
        current.set(layers.getLast());
    }

    private static void currentShiftUp(AppState state) {
        final var layers = state.layersProperty().get();
        final var current = state.currentLayerProperty();

        final int size = layers.size();
        final int idx = layers.indexOf(current.get());

        if (idx == size - 1) {
            state.logger.info("current layer at highest");
        } else {
            current.set(layers.get(idx + 1));
        }
    }

    private static void currentShiftDown(AppState state) {
        final var layers = state.layersProperty().get();
        final var current = state.currentLayerProperty();

        final int idx = layers.indexOf(current.get());

        if (idx == 0) {
            state.logger.info("current layer at lowest");
        } else {
            current.set(layers.get(idx - 1));
        }
    }

    private static void saveWorkspaceAsImage(AppState state) {
        final var workspace = state.getWorkspace();

        if (workspace.getLayers().getChildren().isEmpty()) {
            Helpers.showAlert("Empty canvas.");
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Workspace");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("JPEG Files", "*.jpg"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            workspace.saveAsImage(file);
        }
    }

    public ImageView getButton() {
        return this.button;
    }

    @FunctionalInterface
    public interface LayerAction {
        void handle(AppState state);
    }
}
