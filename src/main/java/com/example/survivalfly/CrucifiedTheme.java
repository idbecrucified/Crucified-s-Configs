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
            case "gamer": return 0xFF1E1035;
            case "sea": return 0xFF0F172A;
            case "sun": return 0xFF2D1B04;
            case "oled": return 0xFF050505;
            case "flash": return 0xFF18181B;
            case "natural": return 0xFF062E1B;
            case "rock": return 0xFF1E2430;
            default: return 0xFF0F172A;
        }
    }

    public static int getSecondaryColor() {
        switch (currentTheme.toLowerCase()) {
            case "gamer": return 0xFF2E1052;
            case "sea": return 0xFF0284C7;
            case "sun": return 0xFF78350F;
            case "oled": return 0xFF000000;
            case "flash": return 0xFF27272A;
            case "natural": return 0xFF047857;
            case "rock": return 0xFF0F172A;
            default: return 0xFF1E293B;
        }
    }

    public static void renderThemeDecorations(DrawContext context, int x, int y, int width, int height) {
        switch (currentTheme.toLowerCase()) {
            case "rock": renderModernMultiOreBackground(context, x, y, width, height); break;
            case "sea": renderModernSea(context, x, y, width, height); break;
            case "gamer": renderModernGamer(context, x, y, width, height); break;
            case "natural": renderModernNatural(context, x, y, width, height); break;
            case "sun": renderModernSun(context, x, y, width, height); break;
            case "oled": renderModernOled(context, x, y, width, height); break;
            case "flash": renderModernFlash(context, x, y, width, height); break;
        }
    }

    private static void renderModernMultiOreBackground(DrawContext context, int x, int y, int w, int h) {
        int tileSize = 32;
        int[] oreColors = {
            0xFF00F0FF, // Diamond
            0xFFFFD700, // Gold
            0xFF00FF66, // Emerald
            0xFFFF2222, // Redstone
            0xFF1E90FF, // Lapis
            0xFFBF55EC, // Amethyst
            0xFFFF7F50, // Copper
            0xFFE0E0E0  // Iron
        };

        for (int ty = y; ty < y + h; ty += tileSize) {
            for (int tx = x; tx < x + w; tx += tileSize) {
                int right = Math.min(tx + tileSize, x + w);
                int bottom = Math.min(ty + tileSize, y + h);

                // Modern smooth slate base
                context.fillGradient(tx, ty, right, bottom, 0xDD293548, 0xDD1B222D);
                context.fill(tx, ty, right, ty + 1, 0x22FFFFFF);

                // Geometric ore vein accents
                int hash = (tx * 31 + ty * 17) & 0x7FFFFFFF;
                int oreColor = oreColors[hash % oreColors.length];

                int vx = tx + (hash % (tileSize - 12)) + 2;
                int vy = ty + ((hash / 7) % (tileSize - 12)) + 2;

                if (vx + 10 < right && vy + 10 < bottom) {
                    context.fill(vx, vy, vx + 8, vy + 4, oreColor);
                    context.fill(vx + 4, vy + 4, vx + 10, vy + 7, (oreColor & 0x00FFFFFF) | 0xAA000000);
                    context.fill(vx + 2, vy + 2, vx + 5, vy + 4, 0xEEFFFFFF);
                }
            }
        }
    }

    private static void renderModernSea(DrawContext context, int x, int y, int w, int h) {
        context.fillGradient(x, y, x + w, y + 4, 0xAA00F0FF, 0x0000F0FF);
        context.fillGradient(x, y + h - 4, x + w, y + h, 0x000284C7, 0xAA0284C7);
    }

    private static void renderModernGamer(DrawContext context, int x, int y, int w, int h) {
        context.fill(x, y, x + w, y + 2, 0xFFEC4899);
        context.fill(x, y + h - 2, x + w, y + h, 0xFF8B5CF6);
        context.fill(x, y + 2, x + 2, y + h - 2, 0xFF3B82F6);
    }

    private static void renderModernNatural(DrawContext context, int x, int y, int w, int h) {
        context.fillGradient(x, y, x + w, y + 6, 0xAA10B981, 0x0010B981);
        context.fill(x + 10, y + 10, x + w - 10, y + 11, 0x3310B981);
    }

    private static void renderModernSun(DrawContext context, int x, int y, int w, int h) {
        context.fillGradient(x, y, x + w, y + 8, 0xAAF59E0B, 0x00F59E0B);
    }

    private static void renderModernOled(DrawContext context, int x, int y, int w, int h) {
        context.fill(x, y, x + w, y + 1, 0x33FFFFFF);
        context.fill(x, y + h - 1, x + w, y + h, 0x33FFFFFF);
    }

    private static void renderModernFlash(DrawContext context, int x, int y, int w, int h) {
        context.fill(x, y, x + w, y + 1, 0xFFFFFFFF);
        context.fill(x, y + h - 1, x + w, y + h, 0xFFFFFFFF);
    }
}
