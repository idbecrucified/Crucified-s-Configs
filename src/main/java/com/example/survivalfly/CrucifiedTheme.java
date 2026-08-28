package com.example.survivalfly;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class CrucifiedTheme {
    private static String currentTheme = "Rock";

    // Textures (assets/survivalfly/textures/gui/...)
    private static final Identifier ROCK_BG = Identifier.of("survivalfly", "textures/gui/rock_bg.png");
    private static final Identifier SEA_BG = Identifier.of("survivalfly", "textures/gui/sea_bg.png");
    private static final Identifier GAMER_BG = Identifier.of("survivalfly", "textures/gui/gamer_bg.png");
    private static final Identifier NATURAL_BG = Identifier.of("survivalfly", "textures/gui/natural_bg.png");
    private static final Identifier SUN_BG = Identifier.of("survivalfly", "textures/gui/sun_bg.png");
    private static final Identifier OLED_BG = Identifier.of("survivalfly", "textures/gui/oled_bg.png");
    private static final Identifier FLASH_BG = Identifier.of("survivalfly", "textures/gui/flash_bg.png");

    public static String getCurrentTheme() { return currentTheme; }
    public static void setTheme(String theme) { 
        currentTheme = theme;
        SoundHelper.playThemeChange();
    }

    public static int getPrimaryColor() {
        return 0xFF0F172A;
    }

    public static int getSecondaryColor() {
        return 0xFF38BDF8;
    }

    public static void renderThemeDecorations(DrawContext context, int x, int y, int width, int height) {
        Identifier texture = getTextureForTheme();
        if (texture != null) {
            // Draws and stretches your PNG to fit the panel exactly
            context.drawTexture(texture, x, y, 0, 0, width, height, width, height);
        }
    }

    private static Identifier getTextureForTheme() {
        switch (currentTheme.toLowerCase()) {
            case "rock": return ROCK_BG;
            case "sea": return SEA_BG;
            case "gamer": return GAMER_BG;
            case "natural": return NATURAL_BG;
            case "sun": return SUN_BG;
            case "oled": return OLED_BG;
            case "flash": return FLASH_BG;
            default: return ROCK_BG;
        }
    }
}
