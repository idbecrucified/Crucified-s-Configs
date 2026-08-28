package com.example.survivalfly;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ThemedButtonWidget extends ButtonWidget {

    public ThemedButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION_SUPPLIER);
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        boolean hovered = this.isHovered();

        int primaryColor = CrucifiedTheme.getPrimaryColor();
        int secondaryColor = CrucifiedTheme.getSecondaryColor();

        // 1. Semi-Transparent Background Fill
        int bgAlpha = hovered ? 0x99 : 0x55;
        int bgColor = (primaryColor & 0x00FFFFFF) | (bgAlpha << 24);
        drawRoundedRect(context, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 5, bgColor);

        // 2. Smooth Rounded Theme Border
        int borderAlpha = hovered ? 0xFF : 0x88;
        int borderColor = (secondaryColor & 0x00FFFFFF) | (borderAlpha << 24);
        drawRoundedOutline(context, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 5, borderColor);

        // 3. Centered Text
        int textColor = hovered ? 0xFFFFFFFF : 0xFFE2E8F0;
        int textX = this.getX() + (this.getWidth() - client.textRenderer.getWidth(this.getMessage())) / 2;
        int textY = this.getY() + (this.getHeight() - 8) / 2;
        context.drawTextWithShadow(client.textRenderer, this.getMessage(), textX, textY, textColor);
    }

    // Smooth Filled Rounded Box
    public static void drawRoundedRect(DrawContext context, int x, int y, int width, int height, int radius, int color) {
        // Center cross
        context.fill(x + radius, y, x + width - radius, y + height, color);
        context.fill(x, y + radius, x + radius, y + height - radius, color);
        context.fill(x + width - radius, y + radius, x + width, y + height - radius, color);

        // Corner curve fills
        for (int i = 0; i < radius; i++) {
            int dx = (int) Math.round(Math.sqrt(radius * radius - (radius - i) * (radius - i)));
            // Top Left
            context.fill(x + radius - dx, y + i, x + radius, y + i + 1, color);
            // Top Right
            context.fill(x + width - radius, y + i, x + width - radius + dx, y + i + 1, color);
            // Bottom Left
            context.fill(x + radius - dx, y + height - 1 - i, x + radius, y + height - i, color);
            // Bottom Right
            context.fill(x + width - radius, y + height - 1 - i, x + width - radius + dx, y + height - i, color);
        }
    }

    // Smooth Outline
    public static void drawRoundedOutline(DrawContext context, int x, int y, int width, int height, int radius, int color) {
        // Straight Edges
        context.fill(x + radius, y, x + width - radius, y + 1, color);
        context.fill(x + radius, y + height - 1, x + width - radius, y + height, color);
        context.fill(x, y + radius, x + 1, y + height - radius, color);
        context.fill(x + width - 1, y + radius, x + width, y + height - radius, color);

        // Curve Arcs
        for (int i = 0; i < radius; i++) {
            int dx = (int) Math.round(Math.sqrt(radius * radius - (radius - i) * (radius - i)));
            // Top Left
            context.fill(x + radius - dx, y + i, x + radius - dx + 1, y + i + 1, color);
            // Top Right
            context.fill(x + width - radius + dx - 1, y + i, x + width - radius + dx, y + i + 1, color);
            // Bottom Left
            context.fill(x + radius - dx, y + height - 1 - i, x + radius - dx + 1, y + height - i, color);
            // Bottom Right
            context.fill(x + width - radius + dx - 1, y + height - 1 - i, x + width - radius + dx, y + height - i, color);
        }
    }
}
