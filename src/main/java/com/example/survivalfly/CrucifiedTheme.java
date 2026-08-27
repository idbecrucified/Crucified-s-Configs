package com.example.survivalfly;

import net.minecraft.client.gui.DrawContext;

public class CrucifiedTheme {
    private static String currentTheme = "Sea";

    public static String getCurrentTheme() { return currentTheme; }
    public static void setTheme(String theme) { currentTheme = theme; }

    public static int getPrimaryColor() {
        switch (currentTheme.toLowerCase()) {
            case "gamer": return 0xFF5B21B6;   // Deep Violet
            case "sea": return 0xFF0369A1;     // Oceanic Blue
            case "sun": return 0xFFD97706;     // Amber Gold
            case "oled": return 0xFF121212;    // OLED Gray
            case "flash": return 0xFF000000;   // Pure Black (Monochrome)
            case "natural": return 0xFF15803D; // Forest Green
            case "rock": return 0xFF334155;    // Slate Gray
            default: return 0xFF1E293B;
        }
    }

    public static int getSecondaryColor() {
        switch (currentTheme.toLowerCase()) {
            case "gamer": return 0xFF9333EA;   // Neon Purple
            case "sea": return 0xFF0D9488;     // Cyan / Teal
            case "sun": return 0xFFF59E0B;     // Bright Sun Yellow
            case "oled": return 0xFF000000;    // Deep Pitch Black
            case "flash": return 0xFFFFFFFF;   // Pure White Accent
            case "natural": return 0xFF166534; // Dark Moss
            case "rock": return 0xFF1E293B;    // Deep Rock
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
        // Ocean Waves across the top
        for (int i = 0; i < w - 16; i += 16) {
            context.fill(x + 8 + i, y + 4, x + 14 + i, y + 5, 0xAA38BDF8);
            context.fill(x + 12 + i, y + 5, x + 18 + i, y + 6, 0xAA38BDF8);
        }

        // Seagrass Left & Right
        int gY = y + h - 6;
        context.fill(x + 8, gY - 18, x + 10, gY, 0xDD22C55E);
        context.fill(x + 11, gY - 24, x + 13, gY, 0xDD15803D);
        context.fill(x + 14, gY - 14, x + 16, gY, 0xDD4ADE80);

        context.fill(x + w - 12, gY - 20, x + w - 10, gY, 0xDD22C55E);
        context.fill(x + w - 9, gY - 12, x + w - 7, gY, 0xDD15803D);

        // SHARK (Bottom-Left)
        int sx = x + 18;
        int sy = y + h - 22;
        context.fill(sx + 4, sy + 3, sx + 24, sy + 10, 0xFF64748B); // Body
        context.fill(sx + 10, sy, sx + 14, sy + 4, 0xFF475569);    // Dorsal Fin
        context.fill(sx, sy + 1, sx + 5, sy + 12, 0xFF475569);     // Tail Fin
        context.fill(sx + 12, sy + 7, sx + 22, sy + 10, 0xFFF8FAFC); // White Belly
        context.fill(sx + 21, sy + 5, sx + 22, sy + 6, 0xFF000000); // Eye
        context.fill(sx + 22, sy + 8, sx + 24, sy + 9, 0xFFFFFFFF); // Sharp Teeth

        // Clownfish (Orange)
        int f1X = x + w - 40;
        int f1Y = y + h - 18;
        context.fill(f1X, f1Y, f1X + 9, f1Y + 5, 0xFFF97316);
        context.fill(f1X + 3, f1Y, f1X + 5, f1Y + 5, 0xFFFFFFFF);
        context.fill(f1X - 4, f1Y - 1, f1X, f1Y + 6, 0xFFEA580C);
        context.fill(f1X + 7, f1Y + 1, f1X + 8, f1Y + 2, 0xFF000000);

        // Blue Tang (Blue Fish)
        int f2X = x + w - 65;
        int f2Y = y + h - 28;
        context.fill(f2X, f2Y, f2X + 8, f2Y + 5, 0xFF2563EB);
        context.fill(f2X + 2, f2Y + 1, f2X + 5, f2Y + 4, 0xFFFACC15);
        context.fill(f2X - 3, f2Y, f2X, f2Y + 5, 0xFF1D4ED8);

        // Pink Tropical Fish
        int f3X = x + w - 28;
        int f3Y = y + 18;
        context.fill(f3X, f3Y, f3X + 7, f3Y + 4, 0xFFEC4899);
        context.fill(f3X - 3, f3Y - 1, f3X, f3Y + 5, 0xFFDB2777);
    }

    private static void renderGamerDecorations(DrawContext context, int x, int y, int w, int h) {
        // CONTROLLER (Top Right)
        int cx = x + w - 42;
        int cy = y + 10;
        context.fill(cx, cy, cx + 32, cy + 16, 0xFF1E1B4B); // Controller Body
        context.fill(cx + 4, cy + 4, cx + 10, cy + 6, 0xFF06B6D4); // D-Pad H
        context.fill(cx + 6, cy + 2, cx + 8, cy + 8, 0xFF06B6D4);  // D-Pad V
        context.fill(cx + 22, cy + 4, cx + 24, cy + 6, 0xFFEF4444); // A Button
        context.fill(cx + 25, cy + 2, cx + 27, cy + 4, 0xFF3B82F6); // B Button
        context.fill(cx + 25, cy + 6, cx + 27, cy + 8, 0xFF10B981); // X Button
        context.fill(cx + 28, cy + 4, cx + 30, cy + 6, 0xFFF59E0B); // Y Button

        // KEYBOARD (Bottom Left)
        int kx = x + 10;
        int ky = y + h - 22;
        context.fill(kx, ky, kx + 45, ky + 14, 0xFF0F172A); // Base
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                context.fill(kx + 3 + (col * 5), ky + 2 + (row * 4), kx + 6 + (col * 5), ky + 4 + (row * 4), 0xFFA855F7);
            }
        }

        // GAMING MOUSE (Bottom Right)
        int mx = x + w - 20;
        int my = y + h - 24;
        context.fill(mx, my, mx + 10, my + 16, 0xFF1F2937); // Mouse Body
        context.fill(mx + 4, my + 2, mx + 6, my + 6, 0xFF06B6D4); // Scroll Wheel
        context.fill(mx + 1, my + 1, mx + 4, my + 5, 0xFF374151); // Left Click
        context.fill(mx + 6, my + 1, mx + 9, my + 5, 0xFF374151); // Right Click
    }

    private static void renderSunDecorations(DrawContext context, int x, int y, int w, int h) {
        // Main Large Sun (Top Right)
        drawSun(context, x + w - 24, y + 24, 8, 0xFFFACC15);

        // Secondary Sun (Top Left)
        drawSun(context, x + 24, y + 24, 5, 0xFFFDE047);

        // Mini Sun (Bottom Center)
        drawSun(context, x + (w / 2), y + h - 20, 4, 0xFFFBBF24);
    }

    private static void drawSun(DrawContext context, int sx, int sy, int radius, int color) {
        context.fill(sx - radius, sy - radius, sx + radius, sy + radius, color);
        // Rays
        context.fill(sx - radius - 4, sy, sx - radius - 1, sy + 1, color);
        context.fill(sx + radius + 1, sy, sx + radius + 4, sy + 1, color);
        context.fill(sx, sy - radius - 4, sx + 1, sy - radius - 1, color);
        context.fill(sx, sy + radius + 1, sx + 1, sy + radius + 4, color);
    }

    private static void renderOledDecorations(DrawContext context, int x, int y, int w, int h) {
        // Minimalist neon glow lines
        context.fill(x + 10, y + h - 3, x + w - 10, y + h - 2, 0xFF38BDF8);
        context.fill(x + 10, y + 2, x + w - 10, y + 3, 0xFF38BDF8);
    }

    private static void renderFlashDecorations(DrawContext context, int x, int y, int w, int h) {
        // MONOCHROME BLACK & WHITE Theme with White Lightning Bolts
        context.fill(x + 2, y + 2, x + w - 2, y + 3, 0xFFFFFFFF);
        context.fill(x + 2, y + h - 3, x + w - 2, y + h - 2, 0xFFFFFFFF);

        // White Lightning Bolt (Left)
        drawLightning(context, x + 12, y + 10, 0xFFFFFFFF);

        // White Lightning Bolt (Right)
        drawLightning(context, x + w - 24, y + 10, 0xFFFFFFFF);
    }

    private static void drawLightning(DrawContext context, int lx, int ly, int color) {
        context.fill(lx + 6, ly, lx + 14, ly + 6, color);
        context.fill(lx + 2, ly + 5, lx + 10, ly + 11, color);
        context.fill(lx + 5, ly + 10, lx + 8, ly + 18, color);
    }

    private static void renderNaturalDecorations(DrawContext context, int x, int y, int w, int h) {
        // TREES (Left & Right)
        drawTree(context, x + 8, y + h - 35);
        drawTree(context, x + w - 24, y + h - 35);

        // Grass Blocks (Bottom)
        int gy = y + h - 8;
        context.fill(x + 2, gy, x + w - 2, gy + 6, 0xFF78350F); // Dirt
        context.fill(x + 2, gy - 2, x + w - 2, gy, 0xFF22C55E);  // Grass Top

        // Moss Clumps
        context.fill(x + 30, gy - 5, x + 45, gy - 2, 0xFF15803D);
        context.fill(x + w - 50, gy - 5, x + w - 35, gy - 2, 0xFF166534);
    }

    private static void drawTree(DrawContext context, int tx, int ty) {
        context.fill(tx + 6, ty + 12, tx + 10, ty + 24, 0xFF78350F); // Trunk
        context.fill(tx, ty, tx + 16, ty + 14, 0xFF15803D);          // Leaf Canopy Layer 1
        context.fill(tx + 2, ty - 6, tx + 14, ty, 0xFF22C55E);       // Leaf Canopy Layer 2
    }

    private static void renderRockDecorations(DrawContext context, int x, int y, int w, int h) {
        // ORES IN BACKGROUND
        // Diamond Ore (Cyan)
        drawOre(context, x + 15, y + 15, 0xFF38BDF8);
        drawOre(context, x + w - 30, y + h - 25, 0xFF38BDF8);

        // Gold Ore (Yellow)
        drawOre(context, x + w - 25, y + 20, 0xFFEAB308);
        drawOre(context, x + 40, y + h - 20, 0xFFEAB308);

        // Iron Ore (Tan/Gray)
        drawOre(context, x + 25, y + h - 40, 0xFFD1D5DB);

        // Redstone Ore (Red)
        drawOre(context, x + w - 45, y + 40, 0xFFEF4444);
    }

    private static void drawOre(DrawContext context, int ox, int oy, int color) {
        context.fill(ox, oy, ox + 6, oy + 6, 0xFF475569); // Stone Base
        context.fill(ox + 1, oy + 1, ox + 3, oy + 3, color); // Mineral Gem
        context.fill(ox + 4, oy + 3, ox + 5, oy + 5, color);
    }
}
