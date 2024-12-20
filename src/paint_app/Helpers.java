package paint_app;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.effect.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import paint_app.components.Layer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Function;

/// A utility class containing helper methods for the application.
public class Helpers {
    private static final AppState AppState = paint_app.AppState.getInstance();

    /**
     * Retrieves an icon image based on the given name and returns it as an ImageView.
     * The icon is expected to be located in the `/icons/` directory, with the file name
     * corresponding to the provided `name` parameter (with a `.png` extension).
     *
     * @param name The name of the icon file (without the `.png` extension).
     * @return An ImageView containing the icon image.
     * @throws NullPointerException if the icon resource cannot be found. This will occur if
     *                              the `name` does not correspond to a valid icon file
     *                              located in the `/icons/` directory.
     */
    public static ImageView getIcon(String name) {
        final var resource = Layer.class.getResource(String.format("/icons/%s.png", name));

        if (resource == null) {
            AppState.logger.severe("icon " + name + " not found");
            System.exit(1);
        }

        final var to_white = new ColorAdjust();
        to_white.setBrightness(1);

        final var add_border = new DropShadow();
        add_border.setColor(Color.BLACK);
        add_border.setRadius(7);
        add_border.setSpread(1);
        add_border.setBlurType(BlurType.ONE_PASS_BOX);

        final var effects = new Blend();
        effects.setMode(BlendMode.SRC_OVER);
        effects.setTopInput(to_white);
        effects.setBottomInput(add_border);

        final var image = new ImageView(resource.toString());
        image.setEffect(effects);

        return image;
    }

    /**
     * Retrieves an icon image based on the given name and resizes it to the specified width and height.
     * The icon is expected to be located in the `/icons/` directory, with the file name
     * corresponding to the provided `name` parameter (with a `.png` extension).
     * The image is then resized to the provided width and height using `setFitWidth` and `setFitHeight`.
     *
     * @param name       The name of the icon file (without the `.png` extension).
     * @param fit_width  The width to which the icon image should be resized.
     * @param fit_height The height to which the icon image should be resized.
     * @return An ImageView containing the icon image, resized to the specified dimensions.
     * @throws NullPointerException if the icon resource cannot be found. This will occur if
     *                              the `name` does not correspond to a valid icon file
     *                              located in the `/icons/` directory.
     */
    public static ImageView getIcon(String name, double fit_width, double fit_height) {
        final var img = getIcon(name);
        img.setFitWidth(fit_width);
        img.setFitHeight(fit_height);
        return img;
    }

    /**
     * Creates a VBox that contains a GridPane of nodes generated by the provided generator function.
     * The grid layout adapts based on the specified size:
     * - If the size is 10 or fewer, the nodes are arranged in a single horizontal row.
     * - If the size is greater than 10, the nodes are arranged in a 2-column layout.
     *
     * @param size      The number of nodes to create and display in the grid.
     * @param generator A function that generates a `Node` for each index from 0 to `size - 1`.
     * @return A VBox containing the GridPane with the generated nodes.
     */
    public static VBox createNodeGrid(int size, Function<Integer, Node> generator) {
        var box = new VBox();
        box.setAlignment(Pos.CENTER);

        var grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);

        for (int i = 0; i < size; i++) {
            var node = generator.apply(i);

            if (size <= 10) {
                grid.add(node, i, 0);
            } else {
                grid.add(node, i / 2, i % 2);
            }
        }


        box.getChildren().addAll(grid);
        return box;
    }

    /**
     * Displays an informational alert with the specified message.
     *
     * @param text The content message to display in the alert.
     */
    public static void showAlert(String text) {
        final var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Paint App");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.show();
    }

    public static String loadCss() {
        String source = "";

        try {
            Path path = Path.of(Objects.requireNonNull(Helpers.class.getResource("/style.css")).toURI());
            source = Files.readString(path);

        } catch (Exception e) {

        }
        for (var c : AppColor.values()) {
            source = source.replace("{{" + c.name() + "}}", c.asHex());
        }

        return source;
    }
}
