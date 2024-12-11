module paint.app {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.swt;
    requires javafx.media;
    requires jdk.xml.dom;
    requires java.logging;
    requires jdk.jdi;
    requires org.jetbrains.annotations;
    requires java.desktop;
    requires javafx.swing;

    exports paint_app;
    exports paint_app.components;
}