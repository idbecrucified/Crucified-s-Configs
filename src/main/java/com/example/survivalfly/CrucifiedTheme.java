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
            case "sea": return 0xEE008080;     // Teal
            case "sun": return 0xEEFF8C00;     // Dark Orange
            case "oled": return 0xEE333333;    // Dark Gray
            case "flash": return 0xFFFFFFFF;   // White
            case "natural": return 0xEE4CAF50; // Vibrant Light Emerald
            case "rock": return 0xEECCCCCC;    // Light Gray
            case "gamer":
            default:
                return 0xEEFF1493;             // Pink
        }
    }

    public static int getSecondaryColor() {
        switch (currentTheme.toLowerCase()) {
            case "sea": return 0xEE000080;     // Navy
            case "sun": return 0xEEB22222;     // Dark Red
            case "oled": return 0xFF000000;    // Pure Black
            case "flash": return 0xFF000000;   // Black
            case "natural": return 0xEE0B2E0B; // Deep Dark Forest Green
            case "rock": return 0xEE555555;    // Gray
            case "gamer":
            default:
                return 0xEE8A2BE2;             // Purple
        }
    }
}
