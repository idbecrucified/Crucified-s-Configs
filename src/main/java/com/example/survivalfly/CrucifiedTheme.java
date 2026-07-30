package com.example.survivalfly;

public class CrucifiedTheme {
    private static String currentTheme = "Gamer";

    public static void setTheme(String theme) {
        currentTheme = theme;
    }

    public static String getCurrentTheme() {
        return currentTheme;
    }

    public static int getPrimaryColor() {
        return switch (currentTheme.toLowerCase()) {
            case "sea" -> 0x00AAFF;     // Blue/Cyan
            case "sun" -> 0xFF8800;     // Orange
            case "oled" -> 0x1A1A1A;    // Dark gray / black
            case "flash" -> 0xFFFFFF;   // White
            case "natural" -> 0x228B22; // Forest Green
            case "rock" -> 0x777777;    // Rock Gray
            default -> 0xFF3355;        // Gamer (Default vibrant pink/red)
        };
    }

    public static int getSecondaryColor() {
        return switch (currentTheme.toLowerCase()) {
            case "sea" -> 0x00FFFF;     // Cyan
            case "sun" -> 0xFFFF00;     // Yellow
            case "oled" -> 0x555555;    // Gray accents
            case "flash" -> 0x000000;   // Black
            case "natural" -> 0x00FF00; // Lime Green
            case "rock" -> 0x444444;    // Dark Rock Gray
            default -> 0x9900CC;        // Gamer secondary (Purple)
        };
    }
}
