package com.example.survivalfly;

import com.example.survivalfly.screen.CrucifiedModsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class HudRenderer {

    public static void renderHud(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden) return;

        int primary = CrucifiedTheme.getPrimaryColor();
        int secondary = CrucifiedTheme.getSecondaryColor();

        // 1. Render Keystrokes HUD
        if (CrucifiedModsScreen.isKeystrokesEnabled() && client.getWindow() != null) {
            long handle = client.getWindow().getHandle();
            int startX = 20;
            int startY = 20;
            int boxSize = 22;

            renderKey(context, "W", startX + boxSize + 2, startY, boxSize, boxSize, InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_W), primary, secondary);
            renderKey(context, "A", startX, startY + boxSize + 2, boxSize, boxSize, InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_A), primary, secondary);
            renderKey(context, "S", startX + boxSize + 2, startY + boxSize + 2, boxSize, boxSize, InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_S), primary, secondary);
            renderKey(context, "D", startX + (boxSize * 2) + 4, startY + boxSize + 2, boxSize, boxSize, InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_D), primary, secondary);
        }

        // 2. Render FPS HUD with Theme Gradient
        if (CrucifiedModsScreen.isFpsEnabled()) {
            String fpsText = "FPS: " + client.getCurrentFps();
            int textWidth = client.textRenderer.getWidth(fpsText);
            int fpsX = 20;
            int fpsY = 75;
            int padding = 6;

            // Black 1px border
            context.fill(fpsX - 1, fpsY - 1, fpsX + textWidth + (padding * 2) + 1, fpsY + 14 + 1, 0xFF000000);
            // Theme Gradient Background
            context.fillGradient(fpsX, fpsY, fpsX + textWidth + (padding * 2), fpsY + 14, primary, secondary);
            // Text shadow render
            context.drawTextWithShadow(client.textRenderer, fpsText, fpsX + padding, fpsY + 3, 0xFFFFFF);
        }
    }

    private static void renderKey(DrawContext context, String keyLabel, int x, int y, int width, int height, boolean pressed, int primary, int secondary) {
        MinecraftClient client = MinecraftClient.getInstance();
        
        // Dark outer outline
        context.fill(x - 1, y - 1, x + width + 1, y + height + 1, 0xFF000000);

        if (pressed) {
            // Bright gradient active state
            context.fillGradient(x, y, x + width, y + height, primary, secondary);
            context.drawCenteredTextWithShadow(client.textRenderer, keyLabel, x + (width / 2), y + (height / 2) - 4, 0xFFFFFF);
        } else {
            // Translucent subtle gradient inactive state
            context.fillGradient(x, y, x + width, y + height, (primary & 0x80FFFFFF), (secondary & 0x80FFFFFF));
            context.drawCenteredTextWithShadow(client.textRenderer, keyLabel, x + (width / 2), y + (height / 2) - 4, 0xDDDDDD);
        }
    }
}
