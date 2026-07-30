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

    public static int getGradientStart() {
        return switch (currentTheme.toLowerCase()) {
            case "sea" -> 0xFF003366;
            case "sun" -> 0xFF663300;
            case "oled" -> 0xFF0A0A0A;
            case "flash" -> 0xFF222222;
            case "natural" -> 0xFF0A2E0A;
            case "rock" -> 0xFF2A2A2A;
            default -> 0xFF220011;
        };
    }

    public static int getGradientEnd() {
        return switch (currentTheme.toLowerCase()) {
            case "sea" -> 0xFF001122;
            case "sun" -> 0xFF221100;
            case "oled" -> 0xFF000000;
            case "flash" -> 0xFF000000;
            case "natural" -> 0xFF020A02;
            case "rock" -> 0xFF111111;
            default -> 0xFF0D0008;
        };
    }
}
