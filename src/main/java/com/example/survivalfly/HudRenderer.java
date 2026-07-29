package com.example.survivalfly;

import com.example.survivalfly.util.UIUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class HudRenderer {
    public static void renderHud(DrawContext context, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden || client.player == null) return;

        int screenWidth = client.getWindow().getScaledWidth();

        // Render Keystrokes cleanly at top right without excessive spacing
        if (CrucifiedsConfigs.keystrokes) {
            int startX = screenWidth - 75;
            int startY = 10;
            int size = 20;
            int gap = 2;

            boolean wPressed = client.options.forwardKey.isPressed();
            boolean aPressed = client.options.leftKey.isPressed();
            boolean sPressed = client.options.backKey.isPressed();
            boolean dPressed = client.options.rightKey.isPressed();

            int bgCol = 0x88000000;
            int pressCol = 0x88FFFFFF;

            // W Key (Top Center)
            UIUtils.drawRoundedRect(context, startX + size + gap, startY, size, size, 3, wPressed ? pressCol : bgCol);
            context.drawCenteredTextWithShadow(client.textRenderer, "W", startX + size + gap + size / 2, startY + 6, 0xFFFFFFFF);

            // A Key (Bottom Left)
            UIUtils.drawRoundedRect(context, startX, startY + size + gap, size, size, 3, aPressed ? pressCol : bgCol);
            context.drawCenteredTextWithShadow(client.textRenderer, "A", startX + size / 2, startY + size + gap + 6, 0xFFFFFFFF);

            // S Key (Bottom Middle)
            UIUtils.drawRoundedRect(context, startX + size + gap, startY + size + gap, size, size, 3, sPressed ? pressCol : bgCol);
            context.drawCenteredTextWithShadow(client.textRenderer, "S", startX + size + gap + size / 2, startY + size + gap + 6, 0xFFFFFFFF);

            // D Key (Bottom Right)
            UIUtils.drawRoundedRect(context, startX + (size + gap) * 2, startY + size + gap, size, size, 3, dPressed ? pressCol : bgCol);
            context.drawCenteredTextWithShadow(client.textRenderer, "D", startX + (size + gap) * 2 + size / 2, startY + size + gap + 6, 0xFFFFFFFF);
        }
    }
}
