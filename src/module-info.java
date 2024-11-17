module paint.app {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.swt;
    requires javafx.media;
    requires java.desktop;

    exports paint_app;
    exports paint_app.components.layer;
    exports paint_app.components;
}