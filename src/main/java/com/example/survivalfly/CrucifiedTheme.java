package com.example.survivalfly;

import net.minecraft.client.gui.DrawContext;

public class CrucifiedTheme {
    private static String currentTheme = "Rock";

    public static String getCurrentTheme() { return currentTheme; }
    public static void setTheme(String theme) { 
        currentTheme = theme;
        SoundHelper.playThemeChange();
    }

    public static int getPrimaryColor() {
        switch (currentTheme.toLowerCase()) {
            case "gamer": return 0xFFA855F7;   
            case "sea": return 0xFF0284C7;     
            case "sun": return 0xFFF59E0B;     
            case "oled": return 0xFFFFFFFF;    
            case "flash": return 0xFFE4E4E7;   
            case "natural": return 0xFF10B981; 
            case "rock": return 0xFF38BDF8;    
            default: return 0xFF38BDF8;
        }
    }

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

    public static void renderThemeDecorations(DrawContext context, int x, int y, int width, int height) {
        int primary = getPrimaryColor();
        int secondary = getSecondaryColor();

        // 1. Main Semi-Transparent Glass Container with Rounded Corners
        ThemedButtonWidget.drawRoundedRect(context, x, y, width, height, 6, 0xD90F172A);

        // 2. Rounded Outer Accent Border
        int borderAlpha = (secondary & 0x00FFFFFF) | 0xAA000000;
        ThemedButtonWidget.drawRoundedOutline(context, x, y, width, height, 6, borderAlpha);

        // 3. Top Accent Line
        context.fill(x + 6, y + 2, x + width - 6, y + 4, (primary & 0x00FFFFFF) | 0xDD000000);
    }
}
