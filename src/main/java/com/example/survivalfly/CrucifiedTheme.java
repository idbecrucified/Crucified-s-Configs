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
            case "gamer": return 0xFF180E29;
            case "sea": return 0xFF0B1329;
            case "sun": return 0xFF241407;
            case "oled": return 0xFF000000;
            case "flash": return 0xFF121215;
            case "natural": return 0xFF052014;
            case "rock": return 0xFF181C24;
            default: return 0xFF0B1329;
        }
    }

    public static int getSecondaryColor() {
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

    // Modern Seamless Slate & Multi-Ore Backdrop (Vector Crystalline Aesthetics)
    private static void renderModernMultiOreBackground(DrawContext context, int x, int y, int w, int h) {
        int tileSize = 40;
        int[] oreColors = {
            0xFF00F0FF, // Diamond
            0xFFFFD700, // Gold
            0xFF10B981, // Emerald
            0xFFEF4444, // Redstone
            0xFF3B82F6, // Lapis
            0xFFA855F7, // Amethyst
            0xFFF97316, // Copper
            0xFFE2E8F0  // Iron
        };

        for (int ty = y; ty < y + h; ty += tileSize) {
            for (int tx = x; tx < x + w; tx += tileSize) {
                int right = Math.min(tx + tileSize, x + w);
                int bottom = Math.min(ty + tileSize, y + h);

                // Smooth modern slate base gradient with micro-borders
                context.fillGradient(tx, ty, right, bottom, 0xEE222A38, 0xEE141923);
                context.fill(tx, ty, right, ty + 1, 0x1AFFFFFF);
                context.fill(tx, ty, tx + 1, bottom, 0x1AFFFFFF);

                int hash = Math.abs((tx * 31 + ty * 17) ^ 0x55555555);
                int oreColor = oreColors[hash % oreColors.length];

                int vx = tx + (hash % (tileSize - 16)) + 4;
                int vy = ty + ((hash / 11) % (tileSize - 16)) + 4;

                if (vx + 14 < right && vy + 14 < bottom) {
                    // Modern glowing geometric ore crystal clusters (No blocky pixels)
                    int glowColor = (oreColor & 0x00FFFFFF) | 0x33000000;
                    context.fill(vx - 2, vy - 2, vx + 14, vy + 10, glowColor);

                    // Angular crystal facet shapes
                    context.fillGradient(vx, vy, vx + 8, vy + 5, oreColor, (oreColor & 0x00FFFFFF) | 0xAA000000);
                    context.fillGradient(vx + 4, vy + 3, vx + 12, vy + 8, oreColor, 0xEEFFFFFF);
                    context.fill(vx + 2, vy + 1, vx + 5, vy + 3, 0xFFFFFFFF); // Specular highlight
                }
            }
        }
    }

    // Modern Deep Ocean Backdrop with Layered Volumetric Light Rays & Wave Vectors
    private static void renderModernSea(DrawContext context, int x, int y, int w, int h) {
        context.fillGradient(x, y, x + w, y + h, 0xEE0B1329, 0xEE0369A1);

        // Translucent light beam accents
        for (int i = 0; i < w; i += 60) {
            context.fillGradient(x + i, y, x + i + 25, y + h, 0x1500F0FF, 0x0000F0FF);
        }

        // Layered sleek wave lines
        context.fill(x, y + (h / 3), x + w, y + (h / 3) + 1, 0x2238BDF8);
        context.fill(x, y + ((h * 2) / 3), x + w, y + ((h * 2) / 3) + 1, 0x1138BDF8);
    }

    // Modern Cyberpunk / RGB Gamer Graphics with Diagonal Laser Accents & Neon Glow
    private static void renderModernGamer(DrawContext context, int x, int y, int w, int h) {
        context.fillGradient(x, y, x + w, y + h, 0xEE180E29, 0xEE2E1052);

        // Neon edge ambient glow
        context.fillGradient(x, y, x + w, y + 4, 0xAAC084FC, 0x00C084FC);
        context.fillGradient(x, y + h - 4, x + w, y + h, 0x00EC4899, 0xAAEC4899);

        // Modern geometric matrix lines
        for (int i = 0; i < h; i += 16) {
            context.fill(x, y + i, x + w, y + i + 1, 0x0AFFFFFF);
        }
    }

    // Modern Emerald Forest Backdrop with Angular Geometric Leaf Overlay
    private static void renderModernNatural(DrawContext context, int x, int y, int w, int h) {
        context.fillGradient(x, y, x + w, y + h, 0xEE052014, 0xEE064E3B);

        // Geometric leaf shapes
        for (int i = 0; i < w; i += 45) {
            int leafY = y + ((i * 7) % Math.max(1, h - 20));
            context.fillGradient(x + i, leafY, x + i + 16, leafY + 16, 0x2210B981, 0x0510B981);
            context.fill(x + i + 2, leafY + 2, x + i + 14, leafY + 3, 0x3334D399);
        }
    }

    // Modern Sunset Graphics with Radial-Style Layered Gradient Rays
    private static void renderModernSun(DrawContext context, int x, int y, int w, int h) {
        context.fillGradient(x, y, x + w, y + h, 0xEE451A03, 0xEE78350F);

        // Radiant twilight accent bars
        context.fillGradient(x, y, x + w, y + 6, 0xAAF59E0B, 0x00F59E0B);
        context.fillGradient(x, y + (h / 2) - 10, x + w, y + (h / 2) + 10, 0x15F97316, 0x00F97316);
    }

    // Ultra-Sleek OLED Pure Black Frame with Crisp Wireframe Highlights
    private static void renderModernOled(DrawContext context, int x, int y, int w, int h) {
        context.fill(x, y, x + w, y + h, 0xFF000000);
        context.fill(x, y, x + w, y + 1, 0x44FFFFFF);
        context.fill(x, y + h - 1, x + w, y + h, 0x44FFFFFF);
        context.fill(x, y, x + 1, y + h, 0x44FFFFFF);
        context.fill(x + w - 1, y, x + w, y + h, 0x44FFFFFF);
    }

    // Modern High-Contrast Silver Studio Backdrop with Specular Lines
    private static void renderModernFlash(DrawContext context, int x, int y, int w, int h) {
        context.fillGradient(x, y, x + w, y + h, 0xEE27272A, 0xEE18181B);
        context.fill(x, y, x + w, y + 2, 0xFFFFFFFF);
        context.fill(x, y + h - 2, x + w, y + h, 0xFFE4E4E7);
    }
}
