package com.example.survivalfly;

public class CrucifiedTheme {
    public static String currentTheme = "Gamer";

    public static int getPrimaryColor() {
        switch (currentTheme) {
            case "Sea":
                return 0xFF00BFFF;     // Blue
            case "Sun":
                return 0xFFFF7F00;     // Orange
            case "OLED":
                return 0xFF444444;    // Gray accent
            case "Flash":
                return 0xFFFFFFFF;   // White
            case "Natural":
                return 0xFF228B22; // Forest Green
            case "Rock":
                return 0xFF708090;    // Slate Gray
            default:
                return 0xFFFF007F;        // Gamer (Hot Pink)
        }
    }

    public static int getSecondaryColor() {
        switch (currentTheme) {
            case "Sea":
                return 0xFF00FFFF;     // Cyan
            case "Sun":
                return 0xFFFFD700;     // Yellow
            case "OLED":
                return 0xFF666666;    // Lighter Gray outline
            case "Flash":
                return 0xFFCCCCCC;   // Off-White
            case "Natural":
                return 0xFF32CD32; // Lime Green
            case "Rock":
                return 0xFFA9A9A9;    // Dark Gray Rock
            default:
                return 0xFFFF69B4;        // Gamer (Pink)
        }
    }

    public static int getBackgroundColor() {
        if ("OLED".equals(currentTheme)) {
            return 0xFF000000; // Pure black for OLED
        }
        if ("Flash".equals(currentTheme)) {
            return 0xFF0A0A0A; // Pitch black/dark contrast
        }
        return 0xCC1a1c23;
    }

    public static int getHeaderColor() {
        if ("OLED".equals(currentTheme)) {
            return 0xFF111111;
        }
        if ("Flash".equals(currentTheme)) {
            return 0xFF1C1C1C;
        }
        return 0xCC252836;
    }

    public static int getAccentColor() {
        return getPrimaryColor();
    }
}
