package com.example.survivalfly;

public class CrucifiedTheme {
    public static int getPrimaryColor() {
        switch (CrucifiedsConfigs.currentTheme) {
            case "Sea": return 0xFF00BFFF;     // Deep Sky Blue
            case "Sun": return 0xFFFF8C00;     // Dark Orange
            case "OLED": return 0xFF777777;    // Gray Accent
            case "Flash": return 0xFFFFFFFF;   // White
            case "Natural": return 0xFF32CD32; // Lime Green
            case "Rock": return 0xFF8A8A8A;    // Stone Gray
            case "Gamer":
            default: return 0xFFDA70D6;        // Orchid/Pink
        }
    }

    public static int getSecondaryColor() {
        switch (CrucifiedsConfigs.currentTheme) {
            case "Sea": return 0xFF00FFFF;     // Cyan
            case "Sun": return 0xFFFFD700;     // Gold/Yellow
            case "OLED": return 0xFF333333;    // Dark Gray
            case "Flash": return 0xFF000000;   // Black
            case "Natural": return 0xFF006400; // Dark Green
            case "Rock": return 0xFF555555;    // Dark Rock Gray
            case "Gamer":
            default: return 0xFFFF69B4;        // Hot Pink
        }
    }

    public static int getFillColor() {
        switch (CrucifiedsConfigs.currentTheme) {
            case "Sea": return 0x5500BFFF;
            case "Sun": return 0x55FF8C00;
            case "OLED": return 0x99000000;
            case "Flash": return 0x88FFFFFF;
            case "Natural": return 0x5532CD32;
            case "Rock": return 0x55708090;
            case "Gamer":
            default: return 0x55DA70D6;
        }
    }
}
