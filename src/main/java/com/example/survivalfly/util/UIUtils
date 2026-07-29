package com.example.survivalfly.util;

import net.minecraft.client.gui.DrawContext;

public class UIUtils {
    public static void drawRoundedRect(DrawContext context, int x, int y, int width, int height, int radius, int color) {
        // Center fill boxes
        context.fill(x + radius, y, x + width - radius, y + height, color);
        context.fill(x, y + radius, x + radius, y + height - radius, color);
        context.fill(x + width - radius, y + radius, x + width, y + height - radius, color);

        // Rounded corners pixel mapping
        for (int i = 0; i < radius; i++) {
            int dy = radius - i;
            int dx = (int) Math.sqrt(radius * radius - dy * dy);
            // Top-Left
            context.fill(x + radius - dx, y + i, x + radius, y + i + 1, color);
            // Top-Right
            context.fill(x + width - radius, y + i, x + width - radius + dx, y + i + 1, color);
            // Bottom-Left
            context.fill(x + radius - dx, y + height - i - 1, x + radius, y + height - i, color);
            // Bottom-Right
            context.fill(x + width - radius, y + height - i - 1, x + width - radius + dx, y + height - i, color);
        }
    }
}
