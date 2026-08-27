package com.example.survivalfly;

import net.minecraft.client.gui.DrawContext;

public class CrucifiedTheme {
    private static String currentTheme = "Gamer";

    public static String getCurrentTheme() { return currentTheme; }
    public static String getCurrentThemeName() { return currentTheme; }
    public static void setTheme(String themeName) { currentTheme = themeName; }

    public static int getPrimaryColor() {
        switch (currentTheme.toLowerCase()) {
            case "sea": return 0xEE008080;     // Teal
            case "sun": return 0xEEFF8C00;     // Dark Orange
            case "oled": return 0xEE333333;    // Dark Gray
            case "flash": return 0xFFFFFFFF;   // White
            case "natural": return 0xEE4CAF50; // Emerald Green
            case "rock": return 0xEECCCCCC;    // Light Gray
            case "gamer":
            default:
                return 0xEEFF1493;             // Neon Pink
        }
    }

    public static int getSecondaryColor() {
        switch (currentTheme.toLowerCase()) {
            case "sea": return 0xEE000080;     // Navy
            case "sun": return 0xEEB22222;     // Dark Red
            case "oled": return 0xFF000000;    // Pure Black
            case "flash": return 0xFF000000;   // Black
            case "natural": return 0xEE0B2E0B; // Deep Dark Green
            case "rock": return 0xEE555555;    // Gray
            case "gamer":
            default:
                return 0xEE8A2BE2;             // Deep Purple
        }
    }

    // Draws custom theme graphics & accent lines inside screens
    public static void renderThemeDecorations(DrawContext context, int x, int y, int width, int height) {
        int primary = getPrimaryColor();
        int secondary = getSecondaryColor();

        // Decorative top glow accent line
        context.fillGradient(x + 10, y + 2, x + width - 10, y + 4, primary, secondary);

        // Theme-specific graphics
        switch (currentTheme.toLowerCase()) {
            case "gamer":
                // Crosshair accent lines in header corner
                context.fill(x + width - 25, y + 10, x + width - 15, y + 12, primary);
                context.fill(x + width - 21, y + 6, x + width - 19, y + 16, primary);
                break;
            case "sea":
                // Wave accent bars
                context.fill(x + 12, y + height - 12, x + 40, y + height - 10, primary);
                context.fill(x + 20, y + height - 8, x + 48, y + height - 6, secondary);
                break;
            case "sun":
                // Corner sunflare dot matrix
                context.fill(x + 12, y + 10, x + 16, y + 14, primary);
                context.fill(x + 18, y + 10, x + 20, y + 12, secondary);
                break;
            case "oled":
                // Cyber grid corner lines
                context.fill(x + 8, y + 8, x + 30, y + 9, 0xFF444444);
                context.fill(x + 8, y + 8, x + 9, y + 30, 0xFF444444);
                break;
            case "flash":
                // Lightning diagonal slash graphic
                context.fill(x + width - 20, y + 8, x + width - 10, y + 10, 0xFFFFFFFF);
                context.fill(x + width - 16, y + 10, x + width - 12, y + 18, 0xFF888888);
                break;
            case "natural":
                // Emerald leaf accent block
                context.fill(x + width - 22, y + 10, x + width - 14, y + 18, 0xEE4CAF50);
                context.fill(x + width - 18, y + 14, x + width - 10, y + 22, 0xEE2E7D32);
                break;
            case "rock":
                // Stone block accents
                context.fill(x + 10, y + height - 16, x + 22, y + height - 10, 0xEE888888);
                context.fill(x + 24, y + height - 16, x + 30, y + height - 10, 0xEE444444);
                break;
        }
    }
}
