package com.example.survivalfly;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ThemedButtonWidget extends ButtonWidget {

    public ThemedButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, button -> {
            SoundHelper.playClick();
            onPress.onPress(button);
        }, DEFAULT_NARRATION_SUPPLIER);
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        int x = this.getX();
        int y = this.getY();
        int w = this.getWidth();
        int h = this.getHeight();

        int baseBg = 0xDD111827;
        int hoverBg = 0xDD1F2937;
        int activeBg = this.isHovered() ? hoverBg : baseBg;
        int accent = CrucifiedTheme.getSecondaryColor();

        // Sleek outer border & backdrop
        context.fill(x, y, x + w, y + h, activeBg);
        context.fill(x, y, x + w, y + 1, 0x33FFFFFF);
        
        // Left accent indicator bar
        if (this.isHovered()) {
            context.fill(x, y, x + 2, y + h, accent);
        }

        int textColor = this.active ? (this.isHovered() ? 0xFFFFFFFF : 0xFFD1D5DB) : 0xFF6B7280;
        context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, this.getMessage(), x + w / 2, y + (h - 8) / 2, textColor);
    }
}
