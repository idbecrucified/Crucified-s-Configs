package com.example.survivalfly;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ThemedButtonWidget extends ButtonWidget {

    public ThemedButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION_SUPPLIER);
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        int x = this.getX();
        int y = this.getY();
        int w = this.getWidth();
        int h = this.getHeight();

        int primary = CrucifiedTheme.getPrimaryColor();
        int secondary = CrucifiedTheme.getSecondaryColor();

        // Hover effect lighting adjustment
        if (this.isHovered()) {
            primary = (primary & 0xFF000000) | ((primary & 0x00FEFEFE) >> 1) + 0x007F7F7F;
        }

        // 1px Black Border
        context.fill(x - 1, y - 1, x + w + 1, y + h + 1, 0xFF000000);

        // Theme Gradient Button Background
        context.fillGradient(x, y, x + w, y + h, primary, secondary);

        // Inner border highlight line for a 3D modern glass aesthetic
        context.fill(x, y, x + w, y + 1, 0x44FFFFFF);

        int textColor = this.active ? (this.isHovered() ? 0xFFFF00 : 0xFFFFFF) : 0xA0A0A0;
        context.drawCenteredTextWithShadow(net.minecraft.client.MinecraftClient.getInstance().textRenderer, this.getMessage(), x + w / 2, y + (h - 8) / 2, textColor);
    }
}
