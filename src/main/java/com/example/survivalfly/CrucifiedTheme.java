package com.example.survivalfly;

import net.minecraft.client.gui.DrawContext;

public class CrucifiedTheme {
    private static String currentTheme = "Rock";

    public static String getCurrentTheme() { return currentTheme; }
    public static void setTheme(String theme) { 
        currentTheme = theme;
        SoundHelper.playThemeChange();
    }

    // Main Accent Color (Glows, Title Highlights, Primary Accents)
    public static int getPrimaryColor() {
        switch (currentTheme.toLowerCase()) {
            case "gamer": return 0xFFA855F7;   // Electric Purple
            case "sea": return 0xFF0284C7;     // Deep Ocean Blue
            case "sun": return 0xFFF59E0B;     // Amber Orange
            case "oled": return 0xFFFFFFFF;    // Pure White
            case "flash": return 0xFFE4E4E7;   // Studio Silver
            case "natural": return 0xFF10B981; // Emerald Green
            case "rock": return 0xFF38BDF8;    // Quartz Cyan
            default: return 0xFF38BDF8;
        }
    }

    // Button / Active Highlight Color (Semi-transparent secondary fill/borders)
    public static int getSecondaryColor() {
        switch (currentTheme.toLowerCase()) {
            case "gamer": return 0xFFC084FC;
            case "sea": return 0xFF38BDF8;
            case "sun": return 0xFFFBBF24;
            case "oled": return 0xFFD4D4D8;
            case "flash": return 0xFFA1A1AA;
            case "natural": return 0xFF34D399;
            case "rock": return 0xFF7DD3FC;
            default: return 0xFF7DD3FC;
        }
    }

    // Lunar-style Semi-Transparent Panel Background
    public static void renderThemeDecorations(DrawContext context, int x, int y, int width, int height) {
        int primary = getPrimaryColor();
        
        // 1. Base Semi-Transparent Dark Glass Backdrop (85% Opacity Dark Slate)
        context.fill(x, y, x + width, y + height, 0xD90F172A);

        // 2. Top Theme Accent Bar (Primary Color Fade)
        int accentAlpha = (primary & 0x00FFFFFF) | 0xBB000000;
        context.fillGradient(x, y, x + width, y + 3, accentAlpha, (primary & 0x00FFFFFF) | 0x22000000);

        // 3. Subtle Outer Glass Border
        context.fill(x, y, x + width, y + 1, 0x33FFFFFF);
        context.fill(x, y + height - 1, x + width, y + height, 0x1AFFFFFF);
        context.fill(x, y, x + 1, y + height, 0x22FFFFFF);
        context.fill(x + width - 1, y, x + width, y + height, 0x22FFFFFF);
    }
}
