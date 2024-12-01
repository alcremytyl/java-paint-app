package paint_app;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

import java.util.logging.Logger;

public class AppState {
    private static AppState INSTANCE;
    public final Logger logger = Logger.getLogger("Paint App");
    private final SimpleObjectProperty<Color> primary_color = new SimpleObjectProperty<>(Color.BLACK);
    private final SimpleObjectProperty<Color> secondary_color = new SimpleObjectProperty<>(Color.WHITE);
    private final SimpleDoubleProperty brush_size = new SimpleDoubleProperty(12.0);

    private AppState() {
    }


    public static synchronized AppState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppState();
            INSTANCE.setupListeners();
        }
        return INSTANCE;
    }

    private void setupListeners() {
        primary_color.addListener((_, o, n) -> {
            logger.info(String.format("set `primary_color` from %s to %s", o, n));
        });
        secondary_color.addListener((_, o, n) -> {
            logger.info(String.format("set `secondary_color` from %s to %s", o, n));
        });
    }

    public SimpleObjectProperty<Color> primaryColorProperty() {
        return this.primary_color;
    }

    public SimpleObjectProperty<Color> secondaryColorProperty() {
        return this.secondary_color;
    }

    public SimpleDoubleProperty brushSizeProperty() {
        return this.brush_size;
    }

    public void resetColors() {
        logger.info("Resetting colors");
        primary_color.set(Color.BLACK);
        secondary_color.set(Color.WHITE);
    }

    public void swapColors() {
        logger.info("Swapping colors");
        final var tmp = primary_color.get();
        primary_color.set(secondary_color.get());
        secondary_color.set(tmp);
    }
}
