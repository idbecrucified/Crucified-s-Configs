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

        int bgAlpha = hovered ? 0xBB : 0x77;
        int bgColor = (primaryColor & 0x00FFFFFF) | (bgAlpha << 24);
        drawRoundedRect(context, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 5, bgColor);

        int borderAlpha = hovered ? 0xFF : 0x99;
        int borderColor = (secondaryColor & 0x00FFFFFF) | (borderAlpha << 24);
        drawRoundedOutline(context, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 5, borderColor);

        int textColor = hovered ? 0xFFFFFFFF : 0xFFE2E8F0;
        int textX = this.getX() + (this.getWidth() - client.textRenderer.getWidth(this.getMessage())) / 2;
        int textY = this.getY() + (this.getHeight() - 8) / 2;
        context.drawTextWithShadow(client.textRenderer, this.getMessage(), textX, textY, textColor);
    }

    // Fixed Correct Corner Quadrant Math
    public static void drawRoundedRect(DrawContext context, int x, int y, int width, int height, int radius, int color) {
        context.fill(x + radius, y, x + width - radius, y + height, color);
        context.fill(x, y + radius, x + radius, y + height - radius, color);
        context.fill(x + width - radius, y + radius, x + width, y + height - radius, color);

        for (int r = 0; r < radius; r++) {
            for (int c = 0; c < radius; c++) {
                if ((radius - r - 1) * (radius - r - 1) + (radius - c - 1) * (radius - c - 1) >= radius * radius) {
                    // Fill background cuts to smooth outer boundaries cleanly
                    continue;
                }
                // Fill true corner pixels
                int dx = radius - c - 1;
                int dy = radius - r - 1;
                if (dx * dx + dy * dy <= radius * radius) {
                    context.fill(x + radius - 1 - dx, y + radius - 1 - dy, x + radius - dx, y + radius - dy, color);
                    context.fill(x + width - radius + dx, y + radius - 1 - dy, x + width - radius + dx + 1, y + radius - dy, color);
                    context.fill(x + radius - 1 - dx, y + height - radius + dy, x + radius - dx, y + height - radius + dy + 1, color);
                    context.fill(x + width - radius + dx, y + height - radius + dy, x + width - radius + dx + 1, y + height - radius + dy + 1, color);
                }
            }
        }
    }

    public static void drawRoundedOutline(DrawContext context, int x, int y, int width, int height, int radius, int color) {
        context.fill(x + radius, y, x + width - radius, y + 1, color);
        context.fill(x + radius, y + height - 1, x + width - radius, y + height, color);
        context.fill(x, y + radius, x + 1, y + height - radius, color);
        context.fill(x + width - 1, y + radius, x + width, y + height - radius, color);

        for (int r = 0; r < radius; r++) {
            for (int c = 0; c < radius; c++) {
                int dx = radius - c - 1;
                int dy = radius - r - 1;
                int distSq = dx * dx + dy * dy;
                if (distSq <= radius * radius && distSq >= (radius - 1.5) * (radius - 1.5)) {
                    context.fill(x + radius - 1 - dx, y + radius - 1 - dy, x + radius - dx, y + radius - dy, color);
                    context.fill(x + width - radius + dx, y + radius - 1 - dy, x + width - radius + dx + 1, y + radius - dy, color);
                    context.fill(x + radius - 1 - dx, y + height - radius + dy, x + radius - dx, y + radius - dy + 1, color);
                    context.fill(x + width - radius + dx, y + height - radius + dy, x + width - radius + dx + 1, y + height - radius + dy + 1, color);
                }
            }
        }
    }
}
