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

        // Semi-Transparent Background Fill
        int bgAlpha = hovered ? 0x99 : 0x55;
        int bgColor = (primaryColor & 0x00FFFFFF) | (bgAlpha << 24);
        drawRoundedRect(context, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 4, bgColor);

        // Rounded Theme Border
        int borderAlpha = hovered ? 0xFF : 0x88;
        int borderColor = (secondaryColor & 0x00FFFFFF) | (borderAlpha << 24);
        drawRoundedOutline(context, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 4, borderColor);

        // Centered Text
        int textColor = hovered ? 0xFFFFFFFF : 0xFFE2E8F0;
        int textX = this.getX() + (this.getWidth() - client.textRenderer.getWidth(this.getMessage())) / 2;
        int textY = this.getY() + (this.getHeight() - 8) / 2;
        context.drawTextWithShadow(client.textRenderer, this.getMessage(), textX, textY, textColor);
    }

    public static void drawRoundedRect(DrawContext context, int x, int y, int width, int height, int radius, int color) {
        context.fill(x + radius, y, x + width - radius, y + height, color);
        context.fill(x, y + radius, x + radius, y + height - radius, color);
        context.fill(x + width - radius, y + radius, x + width, y + height - radius, color);
    }

    public static void drawRoundedOutline(DrawContext context, int x, int y, int width, int height, int radius, int color) {
        context.fill(x + radius, y, x + width - radius, y + 1, color);
        context.fill(x + radius, y + height - 1, x + width - radius, y + height, color);
        context.fill(x, y + radius, x + 1, y + height - radius, color);
        context.fill(x + width - 1, y + radius, x + width, y + height - radius, color);
    }
}
