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
            case "gamer": return 0xFF0F172A;
            case "sea": return 0xFF0A192F;
            case "sun": return 0xFF1C1008;
            case "oled": return 0xFF000000;
            case "flash": return 0xFF18181B;
            case "natural": return 0xFF062016;
            case "rock": return 0xFF0F172A;
            default: return 0xFF0F172A;
        }
    }

    public static int getSecondaryColor() {
        switch (currentTheme.toLowerCase()) {
            case "gamer": return 0xFFA855F7;
            case "sea": return 0xFF38BDF8;
            case "sun": return 0xFFF59E0B;
            case "oled": return 0xFFFFFFFF;
            case "flash": return 0xFFF4F4F5;
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

    // Modern Multi-Ore Slate Vector Theme (No pixel art, no grid tiles)
    private static void renderModernMultiOreBackground(DrawContext context, int x, int y, int w, int h) {
        // Deep modern dark slate base gradient
        context.fillGradient(x, y, x + w, y + h, 0xF20F172A, 0xF21E293B);

        // Continuous ambient lighting bands representing ore spectrums (Diamond, Gold, Emerald, Amethyst, Redstone, Lapis, Copper)
        int[] oreGlows = {
            0x3000F0FF, // Diamond Blue
            0x25FFD700, // Gold Yellow
            0x2510B981, // Emerald Green
            0x25A855F7, // Amethyst Purple
            0x25EF4444, // Redstone Red
            0x253B82F6, // Lapis Blue
            0x25F97316  // Copper Orange
        };

        int bandWidth = Math.max(1, w / oreGlows.length);
        for (int i = 0; i < oreGlows.length; i++) {
            int bx = x + (i * bandWidth);
            int bw = (i == oreGlows.length - 1) ? (x + w - bx) : bandWidth;
            int color = oreGlows[i];

            // Smooth top-to-bottom volumetric gradient sweep
            context.fillGradient(bx, y, bx + bw, y + h, color, 0x00000000);
            
            // Modern vector accent light line on top edge
            context.fill(bx, y, bx + bw, y + 2, (color & 0x00FFFFFF) | 0xDD000000);
        }

        // Sleek top/bottom glassmorphic edge highlights
        context.fill(x, y, x + w, y + 1, 0x33FFFFFF);
        context.fill(x, y + h - 1, x + w, y + h, 0x1AFFFFFF);
    }

    private static void renderModernSea(DrawContext context, int x, int y, int w, int h) {
        context.fillGradient(x, y, x + w, y + h, 0xF20A192F, 0xF20284C7);
        context.fillGradient(x, y, x + w, y + 4, 0xAA38BDF8, 0x0038BDF8);
    }

    private static void renderModernGamer(DrawContext context, int x, int y, int w, int h) {
        context.fillGradient(x, y, x + w, y + h, 0xF20F172A, 0xF23B0764);
        context.fillGradient(x, y, x + w, y + 3, 0xFFA855F7, 0x00A855F7);
        context.fillGradient(x, y + h - 3, x + w, y + h, 0x00EC4899, 0xFFEC4899);
    }

    private static void renderModernNatural(DrawContext context, int x, int y, int w, int h) {
        context.fillGradient(x, y, x + w, y + h, 0xF2062016, 0xF2064E3B);
        context.fillGradient(x, y, x + w, y + 3, 0xFF10B981, 0x0010B981);
    }

    private static void renderModernSun(DrawContext context, int x, int y, int w, int h) {
        context.fillGradient(x, y, x + w, y + h, 0xF21C1008, 0xF278350F);
        context.fillGradient(x, y, x + w, y + 4, 0xFFF59E0B, 0x00F59E0B);
    }

    private static void renderModernOled(DrawContext context, int x, int y, int w, int h) {
        context.fill(x, y, x + w, y + h, 0xFF000000);
        context.fill(x, y, x + w, y + 1, 0x33FFFFFF);
        context.fill(x, y + h - 1, x + w, y + h, 0x33FFFFFF);
    }

    private static void renderModernFlash(DrawContext context, int x, int y, int w, int h) {
        context.fillGradient(x, y, x + w, y + h, 0xF227272A, 0xF218181B);
        context.fill(x, y, x + w, y + 2, 0xFFFFFFFF);
        context.fill(x, y + h - 2, x + w, y + h, 0xFFE4E4E7);
    }
}
