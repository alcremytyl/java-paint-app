package paint_app;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/// Colors from Catppuccin's
/// [Macchiatto](https://catppuccin.com/palette#Macchiato)
public enum AppColor {
    Rosewater("#f4dbd6"),
    Flamingo("#f0c6c6"),
    Pink("#f5bde6"),
    Mauve("#c6a0f6"),
    Red("#ed8796"),
    Maroon("#ee99a0"),
    Peach("#f5a97f"),
    Yellow("#eed49f"),
    Green("#a6da95"),
    Teal("#8bd5ca"),
    Sky("#91d7e3"),
    Sapphire("#7dc4e4"),
    Blue("#8aadf4"),
    Lavender("#b7bdf8"),
    Text("#cad3f5"),
    Subtext1("#b8c0e0"),
    Subtext0("#a5adcb"),
    Overlay2("#939ab7"),
    Overlay1("#8087a2"),
    Overlay0("#6e738d"),
    Surface2("#5b6078"),
    Surface1("#494d64"),
    Surface0("#363a4f"),
    Base("#24273a"),
    Mantle("#1e2030"),
    Crust("#181926");

    private final Color color;

    AppColor(String c) {
        this.color = Color.web(c);
    }

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

    public Background asBackground() {
        return asBackground(color);
    }

    public Background asBackground(CornerRadii cr, Insets padding) {
        return asBackground(color, cr, padding);
    }

    public String asHex() {
        return "#" + color.toString().substring(2);
    }
}
