package paint_app;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/// Colors from Catppuccin's
/// [Macchiatto](https://catppuccin.com/palette#Macchiato)
public class AppColors {
    public static final Color Rosewater = Color.web("#f4dbd6");
    public static final Color Flamingo = Color.web("#f0c6c6");
    public static final Color Pink = Color.web("#f5bde6");
    public static final Color Mauve = Color.web("#c6a0f6");
    public static final Color Red = Color.web("#ed8796");
    public static final Color Maroon = Color.web("#ee99a0");
    public static final Color Peach = Color.web("#f5a97f");
    public static final Color Yellow = Color.web("#eed49f");
    public static final Color Green = Color.web("#a6da95");
    public static final Color Teal = Color.web("#8bd5ca");
    public static final Color Sky = Color.web("#91d7e3");
    public static final Color Sapphire = Color.web("#7dc4e4");
    public static final Color Blue = Color.web("#8aadf4");
    public static final Color Lavender = Color.web("#b7bdf8");
    public static final Color Text = Color.web("#cad3f5");
    public static final Color Subtext1 = Color.web("#b8c0e0");
    public static final Color Subtext0 = Color.web("#a5adcb");
    public static final Color Overlay2 = Color.web("#939ab7");
    public static final Color Overlay1 = Color.web("#8087a2");
    public static final Color Overlay0 = Color.web("#6e738d");
    public static final Color Surface2 = Color.web("#5b6078");
    public static final Color Surface1 = Color.web("#494d64");
    public static final Color Surface0 = Color.web("#363a4f");
    public static final Color Base = Color.web("#24273a");
    public static final Color Mantle = Color.web("#1e2030");
    public static final Color Crust = Color.web("#181926");

    public static Background asBackground(Color c) {
        return new Background(new BackgroundFill(c, null, null));
    }

    public static Background asBackground(Color c, CornerRadii cr, Insets padding) {
        return new Background(new BackgroundFill(c, cr, padding));
    }

    public static Border asBorder(Color c) {
        return new Border(new BorderStroke(c, null, null, null));
    }

    public static Border asBorder(Color c, BorderStrokeStyle bss, CornerRadii cr, BorderWidths bw) {
        return new Border(new BorderStroke(c, bss, cr, bw));
    }

    public static String asHex(Color c) {
        return c.toString().substring(2);
    }
}
