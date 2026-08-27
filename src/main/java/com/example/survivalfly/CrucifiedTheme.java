package com.example.survivalfly;

import net.minecraft.client.gui.DrawContext;

public class CrucifiedTheme {
    private static String currentTheme = "Sea";

    public static String getCurrentTheme() { return currentTheme; }
    public static void setTheme(String theme) { currentTheme = theme; }

    public static int getPrimaryColor() {
        switch (currentTheme.toLowerCase()) {
            case "gamer": return 0xFF5B21B6; // Deep Violet
            case "sea": return 0xFF0369A1;   // Oceanic Blue
            case "sun": return 0xFFD97706;   // Warm Amber
            case "oled": return 0xFF121212;  // OLED Gray
            case "flash": return 0xFFB91C1C; // Crimson Red
            case "natural": return 0xFF15803D;// Forest Green
            case "rock": return 0xFF475569;  // Slate Gray
            default: return 0xFF1E293B;
        }
    }

    public static int getSecondaryColor() {
        switch (currentTheme.toLowerCase()) {
            case "gamer": return 0xFF9333EA; // Neon Purple
            case "sea": return 0xFF0D9488;   // Cyan / Teal
            case "sun": return 0xFFF59E0B;   // Sun Yellow
            case "oled": return 0xFF000000;  // True Black
            case "flash": return 0xFFF59E0B; // Yellow Gold
            case "natural": return 0xFF166534;// Dark Moss
            case "rock": return 0xFF1E293B;  // Dark Rock
            default: return 0xFF0F172A;
        }
    }

    public static void renderThemeDecorations(DrawContext context, int x, int y, int width, int height) {
        switch (currentTheme.toLowerCase()) {
            case "sea":
                renderSeaDecorations(context, x, y, width, height);
                break;
            case "gamer":
                renderGamerDecorations(context, x, y, width, height);
                break;
            case "sun":
                renderSunDecorations(context, x, y, width, height);
                break;
            case "oled":
                renderOledDecorations(context, x, y, width, height);
                break;
            case "flash":
                renderFlashDecorations(context, x, y, width, height);
                break;
            case "natural":
                renderNaturalDecorations(context, x, y, width, height);
                break;
            case "rock":
                renderRockDecorations(context, x, y, width, height);
                break;
        }
    }

    private static void renderSeaDecorations(DrawContext context, int x, int y, int w, int h) {
        // Waves top accent
        for (int i = 0; i < w - 16; i += 16) {
            context.fill(x + 8 + i, y + 4, x + 14 + i, y + 5, 0xAA38BDF8);
            context.fill(x + 12 + i, y + 5, x + 18 + i, y + 6, 0xAA38BDF8);
        }
        // Seagrass left bottom
        int gX = x + 8;
        int gY = y + h - 6;
        context.fill(gX, gY - 18, gX + 2, gY, 0xDD22C55E);
        context.fill(gX + 3, gY - 24, gX + 5, gY, 0xDD15803D);
        context.fill(gX + 6, gY - 14, gX + 8, gY, 0xDD4ADE80);

        // Seagrass right bottom
        int rX = x + w - 12;
        context.fill(rX, gY - 20, rX + 2, gY, 0xDD22C55E);
        context.fill(rX + 3, gY - 12, rX + 5, gY, 0xDD15803D);

        // Fish (Clownfish bottom-right)
        int fX = x + w - 38;
        int fY = y + h - 16;
        context.fill(fX, fY, fX + 9, fY + 5, 0xFFF97316);     // Body
        context.fill(fX + 3, fY, fX + 5, fY + 5, 0xFFFFFFFF); // White Stripe
        context.fill(fX - 4, fY - 1, fX, fY + 6, 0xFFEA580C);  // Tail Fin
        context.fill(fX + 7, fY + 1, fX + 8, fY + 2, 0xFF000000); // Eye
    }

    private static void renderGamerDecorations(DrawContext context, int x, int y, int w, int h) {
        // Neon corner brackets
        context.fill(x + 2, y + 2, x + 12, y + 4, 0xFFA855F7);
        context.fill(x + 2, y + 2, x + 4, y + 12, 0xFFA855F7);
        // Pixel Crosshair top right
        int cx = x + w - 14;
        int cy = y + 12;
        context.fill(cx - 3, cy, cx + 4, cy + 1, 0xFF06B6D4);
        context.fill(cx, cy - 3, cx + 1, cy + 4, 0xFF06B6D4);
    }

    private static void renderSunDecorations(DrawContext context, int x, int y, int w, int h) {
        // Sun icon top right
        int sx = x + w - 16;
        int sy = y + 16;
        context.fill(sx - 4, sy - 4, sx + 5, sy + 5, 0xFFFACC15);
        context.fill(sx - 8, sy, sx - 5, sy + 1, 0xFFFDE047);
        context.fill(sx + 6, sy, sx + 9, sy + 1, 0xFFFDE047);
        context.fill(sx, sy - 8, sx + 1, sy - 5, 0xFFFDE047);
        context.fill(sx, sy + 6, sx + 1, sy + 9, 0xFFFDE047);
    }

    private static void renderOledDecorations(DrawContext context, int x, int y, int w, int h) {
        // Minimalist bottom glow strip
        context.fill(x + 10, y + h - 3, x + w - 10, y + h - 2, 0xFF6366F1);
    }

    private static void renderFlashDecorations(DrawContext context, int x, int y, int w, int h) {
        // Lightning bolt icon
        int lx = x + 10;
        int ly = y + h - 22;
        context.fill(lx + 4, ly, lx + 9, ly + 5, 0xFFFACC15);
        context.fill(lx + 1, ly + 4, lx + 6, ly + 9, 0xFFFDE047);
        context.fill(lx + 3, ly + 8, lx + 5, ly + 14, 0xFFFEF08A);
    }

    private static void renderNaturalDecorations(DrawContext context, int x, int y, int w, int h) {
        // Leaves top left
        int lx = x + 6;
        int ly = y + 6;
        context.fill(lx, ly, lx + 5, ly + 5, 0xFF4ADE80);
        context.fill(lx + 4, ly + 4, lx + 9, ly + 9, 0xFF22C55E);
        context.fill(lx + 8, ly + 8, lx + 12, ly + 12, 0xFF15803D);
    }

    private static void renderRockDecorations(DrawContext context, int x, int y, int w, int h) {
        // Diamond ore sparkles
        int rx = x + w - 18;
        int ry = y + h - 18;
        context.fill(rx, ry, rx + 2, ry + 2, 0xFF38BDF8);
        context.fill(rx + 5, ry + 3, rx + 7, ry + 5, 0xFF38BDF8);
        context.fill(rx + 2, ry - 4, rx + 4, ry - 2, 0xFF94A3B8);
    }
}
