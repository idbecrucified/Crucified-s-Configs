package com.example.survivalfly.renderer;

import com.example.survivalfly.CrucifiedTheme;
import com.example.survivalfly.screen.CrucifiedModsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.lwjgl.glfw.GLFW;

public class HudRenderer {
    private static double originalFov = 70.0;
    private static boolean isZooming = false;

    public static void render(DrawContext context, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden) return;

        // Fixed Fullbright via Night Vision status effect and max gamma
        if (CrucifiedModsScreen.isFullbrightEnabled()) {
            try {
                client.options.getGamma().setValue(12.0D);
                if (client.player != null) {
                    client.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 0, true, false, false));
                }
            } catch (Exception ignored) {}
        }

        // Functional Zoom handler
        handleZoom(client);

        int primaryColor = CrucifiedTheme.getPrimaryColor();
        int secondaryColor = CrucifiedTheme.getSecondaryColor();

        // Render Keystrokes if enabled
        if (CrucifiedModsScreen.isKeystrokesEnabled()) {
            renderKeystrokes(context, client, primaryColor, secondaryColor);
        }

        // Render FPS Display if enabled
        if (CrucifiedModsScreen.isFpsEnabled()) {
            renderFps(context, client, primaryColor, secondaryColor);
        }
    }

    private static void handleZoom(MinecraftClient client) {
        if (!CrucifiedModsScreen.isZoomEnabled()) {
            if (isZooming) {
                client.options.getFov().setValue((int) originalFov);
                isZooming = false;
            }
            return;
        }

        int keyCode = getZoomKeyCode(CrucifiedModsScreen.zoomKey);
        boolean zoomPressed = client.getWindow() != null && InputUtil.isKeyPressed(client.getWindow().getHandle(), keyCode);

        if (zoomPressed) {
            if (!isZooming) {
                originalFov = client.options.getFov().getValue();
                isZooming = true;
            }
            client.options.getFov().setValue((int) (originalFov / CrucifiedModsScreen.zoomIntensity));
        } else {
            if (isZooming) {
                client.options.getFov().setValue((int) originalFov);
                isZooming = false;
            }
        }
    }

    private static int getZoomKeyCode(String keyName) {
        return switch (keyName.toUpperCase()) {
            case "Z" -> GLFW.GLFW_KEY_Z;
            case "V" -> GLFW.GLFW_KEY_V;
            case "LEFT_ALT" -> GLFW.GLFW_KEY_LEFT_ALT;
            default -> GLFW.GLFW_KEY_C;
        };
    }

    private static void renderKeystrokes(DrawContext context, MinecraftClient client, int primaryColor, int secondaryColor) {
        int startX = 10;
        int startY = 10;
        int size = 20;
        int gap = 2;

        boolean w = client.options.forwardKey.isPressed();
        boolean a = client.options.leftKey.isPressed();
        boolean s = client.options.backKey.isPressed();
        boolean d = client.options.rightKey.isPressed();

        // W Key (Top)
        drawKeyBox(context, startX + size + gap, startY, size, size, "W", w, primaryColor, secondaryColor);
        // A Key (Left)
        drawKeyBox(context, startX, startY + size + gap, size, size, "A", a, primaryColor, secondaryColor);
        // S Key (Center)
        drawKeyBox(context, startX + size + gap, startY + size + gap, size, size, "S", s, primaryColor, secondaryColor);
        // D Key (Right)
        drawKeyBox(context, startX + (size + gap) * 2, startY + size + gap, size, size, "D", d, primaryColor, secondaryColor);
    }

    private static void drawKeyBox(DrawContext context, int x, int y, int width, int height, String label, boolean pressed, int primaryColor, int secondaryColor) {
        // Background ONLY renders when holding the key down, styled with the second theme color
        if (pressed) {
            context.fill(x, y, x + width, y + height, 0xAA000000 | (secondaryColor & 0xFFFFFF));
        }
        // Text uses the first theme color when pressed, normal gray when unpressed
        int textColor = pressed ? primaryColor : 0xAAAAAA;
        context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, label, x + width / 2, y + (height - 8) / 2, textColor);
    }

    private static void renderFps(DrawContext context, MinecraftClient client, int primaryColor, int secondaryColor) {
        String fpsText = "FPS: " + client.getCurrentFps();
        int x = 10;
        int y = 80;
        int width = client.textRenderer.getWidth(fpsText) + 8;
        int height = 14;

        // Background uses the second theme color
        context.fill(x, y, x + width, y + height, 0x88000000 | (secondaryColor & 0xFFFFFF));
        // Text uses the first theme color
        context.drawTextWithShadow(client.textRenderer, fpsText, x + 4, y + 3, primaryColor);
    }
}
