package com.example.survivalfly;

public class CrucifiedTheme {
    public static String currentTheme = "Gamer";

    public static String getCurrentTheme() {
        return currentTheme != null ? currentTheme : "Gamer";
    }

    public static int getPrimaryColor() {
        String theme = getCurrentTheme();
        switch (theme) {
            case "Sea":
                return 0xFF00BFFF;
            case "Sun":
                return 0xFFFF7F00;
            case "OLED":
                return 0xFF444444;
            case "Flash":
                return 0xFFFFFFFF;
            case "Natural":
                return 0xFF228B22;
            case "Rock":
                return 0xFF708090;
            default:
                return 0xFFFF007F;
        }
    }

    public static int getSecondaryColor() {
        String theme = getCurrentTheme();
        switch (theme) {
            case "Sea":
                return 0xFF00FFFF;
            case "Sun":
                return 0xFFFFD700;
            case "OLED":
                return 0xFF666666;
            case "Flash":
                return 0xFFCCCCCC;
            case "Natural":
                return 0xFF32CD32;
            case "Rock":
                return 0xFFA9A9A9;
            default:
                return 0xFFFF69B4;
        }
    }

    public static int getBackgroundColor() {
        String theme = getCurrentTheme();
        if ("OLED".equals(theme)) {
            return 0xFF000000;
        }
        if ("Flash".equals(theme)) {
            return 0xFF0A0A0A;
        }
        return 0xCC1a1c23;
    }

    public static int getHeaderColor() {
        String theme = getCurrentTheme();
        if ("OLED".equals(theme)) {
            return 0xFF111111;
        }
        if ("Flash".equals(theme)) {
            return 0xFF1C1C1C;
        }
        return 0xCC252836;
    }

    public static int getAccentColor() {
        return getPrimaryColor();
    }
}
