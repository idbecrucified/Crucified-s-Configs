package com.example.survivalfly;

public class CrucifiedTheme {
    private static String currentTheme = "Gamer";

    public static String getCurrentTheme() {
        return currentTheme;
    }

    public static String getCurrentThemeName() {
        return currentTheme;
    }

    public static void setTheme(String themeName) {
        currentTheme = themeName;
    }

    public static int getPrimaryColor() {
        switch (currentTheme.toLowerCase()) {
            case "sea": return 0xFF006699;
            case "sun": return 0xFFCC6600;
            case "oled": return 0xFF111111;
            case "flash": return 0xFF999900;
            case "natural": return 0xFF2E7D32;
            case "rock": return 0xFF4E342E;
            case "gamer":
            default:
                return 0xFF800020;
        }
    }

    public static int getSecondaryColor() {
        switch (currentTheme.toLowerCase()) {
            case "sea": return 0xFF003366;
            case "sun": return 0xFF993300;
            case "oled": return 0xFF000000;
            case "flash": return 0xFF666600;
            case "natural": return 0xFF1B5E20;
            case "rock": return 0xFF3E2723;
            case "gamer":
            default:
                return 0xFF4A0010;
        }
    }
}
