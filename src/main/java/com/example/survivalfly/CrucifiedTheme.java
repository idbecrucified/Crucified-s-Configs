package com.example.survivalfly;

import net.minecraft.client.gui.DrawContext;

public class CrucifiedTheme {
    private static String currentTheme = "Sea";

    public static String getCurrentTheme() { return currentTheme; }
    public static void setTheme(String theme) { 
        currentTheme = theme;
        SoundHelper.playThemeChange();
    }

    public static int getPrimaryColor() {
        switch (currentTheme.toLowerCase()) {
            case "gamer": return 0xFF5B21B6;
            case "sea": return 0xFF0369A1;
            case "sun": return 0xFFD97706;
            case "oled": return 0xFF121212;
            case "flash": return 0xFF000000;
            case "natural": return 0xFF15803D;
            case "rock": return 0xFF334155;
            default: return 0xFF1E293B;
        }
    }

    public static int getSecondaryColor() {
        switch (currentTheme.toLowerCase()) {
            case "gamer": return 0xFF9333EA;
            case "sea": return 0xFF0D9488;
            case "sun": return 0xFFF59E0B;
            case "oled": return 0xFF000000;
            case "flash": return 0xFFFFFFFF;
            case "natural": return 0xFF166534;
            case "rock": return 0xFF1E293B;
            default: return 0xFF0F172A;
        }
    }

    public static void renderThemeDecorations(DrawContext context, int x, int y, int width, int height) {
        switch (currentTheme.toLowerCase()) {
            case "sea": renderSeaDecorations(context, x, y, width, height); break;
            case "gamer": renderGamerDecorations(context, x, y, width, height); break;
            case "sun": renderSunDecorations(context, x, y, width, height); break;
            case "oled": renderOledDecorations(context, x, y, width, height); break;
            case "flash": renderFlashDecorations(context, x, y, width, height); break;
            case "natural": renderNaturalDecorations(context, x, y, width, height); break;
            case "rock": renderRockDecorations(context, x, y, width, height); break;
        }
    }

    private static void renderSeaDecorations(DrawContext context, int x, int y, int w, int h) {
        // Waves top accent
        for (int i = 0; i < w - 16; i += 16) {
            context.fill(x + 8 + i, y + 4, x + 14 + i, y + 5, 0xAA38BDF8);
            context.fill(x + 12 + i, y + 5, x + 18 + i, y + 6, 0xAA38BDF8);
        }

        // KRAKEN (Top-Right Monster replacing pink blob)
        int kx = x + w - 48;
        int ky = y + 8;
        context.fill(kx + 8, ky, kx + 32, ky + 16, 0xFF6B21A8); // Kraken Head
        context.fill(kx + 12, ky + 6, kx + 16, ky + 10, 0xFFEF4444); // Glowing Red Eye Left
        context.fill(kx + 24, ky + 6, kx + 28, ky + 10, 0xFFEF4444); // Glowing Red Eye Right
        // Tentacles
        context.fill(kx, ky + 12, kx + 10, ky + 34, 0xFF581C87);
        context.fill(kx + 10, ky + 14, kx + 18, ky + 40, 0xFF6B21A8);
        context.fill(kx + 22, ky + 14, kx + 30, ky + 42, 0xFF6B21A8);
        context.fill(kx + 30, ky + 12, kx + 40, ky + 32, 0xFF581C87);

        // ABUNDANT SCHOOLS OF FISH
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                int fx = x + 15 + (col * 40);
                int fy = y + h - 50 + (row * 14);
                int color = (col % 2 == 0) ? 0xFFF97316 : 0xFF2563EB;
                context.fill(fx, fy, fx + 6, fy + 3, color);
                context.fill(fx - 2, fy - 1, fx, fy + 4, color & 0xCCFFFFFF);
            }
        }
    }

    private static void renderGamerDecorations(DrawContext context, int x, int y, int w, int h) {
        // MONITORS (Top Right Dual Monitors)
        int mx = x + w - 65;
        int my = y + 10;
        context.fill(mx, my, mx + 26, my + 16, 0xFF0284C7); // Monitor 1 Screen
        context.fill(mx + 28, my, mx + 54, my + 16, 0xFF0284C7); // Monitor 2 Screen
        context.fill(mx - 2, my - 2, mx + 56, my + 18, 0xFF1E293B); // Bezels

        // COMPUTER TOWER WITH RGB (Bottom Right)
        int cx = x + w - 30;
        int cy = y + h - 45;
        context.fill(cx, cy, cx + 22, cy + 36, 0xFF0F172A); // Case
        context.fill(cx + 4, cy + 4, cx + 18, cy + 28, 0xAA06B6D4); // Glass Side Panel
        context.fill(cx + 6, cy + 6, cx + 10, cy + 10, 0xFFEC4899); // RGB Fan 1
        context.fill(cx + 6, cy + 14, cx + 10, cy + 18, 0xFF3B82F6); // RGB Fan 2

        // LAPTOP (Bottom Left)
        int lx = x + 12;
        int ly = y + h - 30;
        context.fill(lx + 4, ly, lx + 24, ly + 14, 0xFF334155); // Screen
        context.fill(lx + 6, ly + 2, lx + 22, ly + 12, 0xFF38BDF8); // Screen Display
        context.fill(lx, ly + 14, lx + 28, ly + 18, 0xFF1E293B); // Base Keyboard
    }

    private static void renderSunDecorations(DrawContext context, int x, int y, int w, int h) {
        drawSun(context, x + w - 24, y + 24, 8, 0xFFFACC15);
        drawSun(context, x + 24, y + 24, 5, 0xFFFDE047);
        drawSun(context, x + (w / 2), y + h - 20, 4, 0xFFFBBF24);
    }

    private static void drawSun(DrawContext context, int sx, int sy, int radius, int color) {
        context.fill(sx - radius, sy - radius, sx + radius, sy + radius, color);
        context.fill(sx - radius - 4, sy, sx - radius - 1, sy + 1, color);
        context.fill(sx + radius + 1, sy, sx + radius + 4, sy + 1, color);
        context.fill(sx, sy - radius - 4, sx + 1, sy - radius - 1, color);
        context.fill(sx, sy + radius + 1, sx + 1, sy + radius + 4, color);
    }

    private static void renderOledDecorations(DrawContext context, int x, int y, int w, int h) {
        context.fill(x + 10, y + h - 3, x + w - 10, y + h - 2, 0xFF38BDF8);
        context.fill(x + 10, y + 2, x + w - 10, y + 3, 0xFF38BDF8);
    }

    private static void renderFlashDecorations(DrawContext context, int x, int y, int w, int h) {
        context.fill(x + 2, y + 2, x + w - 2, y + 3, 0xFFFFFFFF);
        context.fill(x + 2, y + h - 3, x + w - 2, y + h - 2, 0xFFFFFFFF);
        drawLightning(context, x + 12, y + 10, 0xFFFFFFFF);
        drawLightning(context, x + w - 24, y + 10, 0xFFFFFFFF);
    }

    private static void drawLightning(DrawContext context, int lx, int ly, int color) {
        context.fill(lx + 6, ly, lx + 14, ly + 6, color);
        context.fill(lx + 2, ly + 5, lx + 10, ly + 11, color);
        context.fill(lx + 5, ly + 10, lx + 8, ly + 18, color);
    }

    private static void renderNaturalDecorations(DrawContext context, int x, int y, int w, int h) {
        // UPPER LUSH TERRAIN (Farther up screen)
        context.fill(x + 10, y + 10, x + w - 10, y + 16, 0xFF15803D); // Upper Hill Grass
        context.fill(x + 10, y + 16, x + w - 10, y + 20, 0xFF78350F); // Upper Hill Dirt

        // Trees on Upper Terraces
        drawTree(context, x + 20, y + 2);
        drawTree(context, x + w - 35, y + 2);

        // Hanging Vines
        context.fill(x + 40, y + 20, x + 42, y + 38, 0xFF22C55E);
        context.fill(x + 80, y + 20, x + 82, y + 42, 0xFF166534);
        context.fill(x + w - 60, y + 20, x + w - 58, y + 35, 0xFF22C55E);

        // Flowers
        context.fill(x + 50, y + 12, x + 53, y + 15, 0xFFEF4444); // Red Flower
        context.fill(x + 110, y + 12, x + 113, y + 15, 0xFFFACC15); // Yellow Flower
    }

    private static void drawTree(DrawContext context, int tx, int ty) {
        context.fill(tx + 6, ty + 10, tx + 10, ty + 18, 0xFF78350F);
        context.fill(tx, ty, tx + 16, ty + 10, 0xFF15803D);
        context.fill(tx + 2, ty - 4, tx + 14, ty, 0xFF22C55E);
    }

    private static void renderRockDecorations(DrawContext context, int x, int y, int w, int h) {
        // ORE GRID IN BACKGROUND
        int[] oreColors = {0xFF38BDF8, 0xFFEAB308, 0xFFEF4444, 0xFF10B981, 0xFFD1D5DB, 0xFF1E293B};
        int oreIdx = 0;

        for (int gx = x + 12; gx < x + w - 18; gx += 26) {
            for (int gy = y + 12; gy < y + h - 18; gy += 26) {
                int color = oreColors[oreIdx % oreColors.length];
                drawOreBlock(context, gx, gy, color);
                oreIdx++;
            }
        }
    }

    private static void drawOreBlock(DrawContext context, int ox, int oy, int mineralColor) {
        context.fill(ox, oy, ox + 10, oy + 10, 0xFF475569); // Stone Block Frame
        context.fill(ox + 2, oy + 2, ox + 5, oy + 5, mineralColor); // Sparkle 1
        context.fill(ox + 6, oy + 5, ox + 8, oy + 8, mineralColor); // Sparkle 2
    }
}
