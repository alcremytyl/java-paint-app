package paint_app;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.util.logging.Logger;

public class AppState {
    private static AppState INSTANCE;

    // background for smoother experience binding to buttons
    // use getFills().getFirst() when drawing
    private final SimpleObjectProperty<Background> primary_color = new SimpleObjectProperty<>(new Background(new BackgroundFill(Color.BLACK, null, null)));
    private final SimpleObjectProperty<Background> secondary_color = new SimpleObjectProperty<>(new Background(new BackgroundFill(Color.WHITE, null, null)));

    private final SimpleDoubleProperty brush_size = new SimpleDoubleProperty(12.0);
    private Logger logger;

    private AppState() {
    }


    public static synchronized AppState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppState();
        }
        return INSTANCE;
    }

    public void attachLogger(Logger logger) {
        this.logger = logger;

        primary_color.addListener((_, o, n) -> {
            logger.info(String.format("set `primary_color` from %s to %s", o, n));
        });
        secondary_color.addListener((_, o, n) -> {
            logger.info(String.format("set `secondary_color` from %s to %s", o, n));
        });
    }

    public SimpleObjectProperty<Background> primaryColorProperty() {
        return this.primary_color;
    }

    public SimpleObjectProperty<Background> secondaryColorProperty() {
        return this.secondary_color;
    }

    public SimpleDoubleProperty brushSizeProperty() {
        return this.brush_size;
    }
}
