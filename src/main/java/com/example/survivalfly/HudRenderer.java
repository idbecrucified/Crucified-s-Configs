package com.example.survivalfly;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class HudRenderer {

    public static void renderHud(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.options.hudHidden || client.player == null) {
            return;
        }

        TextRenderer textRenderer = client.textRenderer;

        // 1. FPS Counter
        if (CrucifiedsConfigs.fpsCounter) {
            String fpsText = "FPS: " + client.getCurrentFps();
            drawBoxElement(context, textRenderer, fpsText, CrucifiedsConfigs.fpsX, CrucifiedsConfigs.fpsY);
        }

        // 2. Keystrokes (WASD Real Layout)
        if (CrucifiedsConfigs.keystrokes) {
            renderKeystrokes(context, textRenderer, client, CrucifiedsConfigs.keystrokesX, CrucifiedsConfigs.keystrokesY);
        }

        // 3. Armor Status (Icons & Durability)
        if (CrucifiedsConfigs.armorStatus) {
            renderArmorStatus(context, client, CrucifiedsConfigs.armorX, CrucifiedsConfigs.armorY);
        }

        // 4. CPS Display
        if (CrucifiedsConfigs.cpsDisplay) {
            drawBoxElement(context, textRenderer, "CPS: 0", CrucifiedsConfigs.cpsX, CrucifiedsConfigs.cpsY);
        }

        // 5. Totem Counter
        if (CrucifiedsConfigs.totemCounter) {
            int totems = getTotemCount(client);
            drawBoxElement(context, textRenderer, "Totems: " + totems, CrucifiedsConfigs.totemX, CrucifiedsConfigs.totemY);
        }
    }

    private static void drawBoxElement(DrawContext context, TextRenderer textRenderer, String text, int x, int y) {
        int width = textRenderer.getWidth(text) + 6;
        int height = 14;

        if (CrucifiedsConfigs.hudBackground) {
            context.fill(x, y, x + width, y + height, CrucifiedsConfigs.hudBackgroundColor);
        }
        context.drawText(textRenderer, text, x + 3, y + 3, CrucifiedsConfigs.hudTextColor, true);
    }

    private static void renderKeystrokes(DrawContext context, TextRenderer textRenderer, MinecraftClient client, int x, int y) {
        boolean wPressed = client.options.forwardKey.isPressed();
        boolean aPressed = client.options.leftKey.isPressed();
        boolean sPressed = client.options.backKey.isPressed();
        boolean dPressed = client.options.rightKey.isPressed();

        int bgActive = CrucifiedsTheme.getPrimaryColor();
        int bgNormal = CrucifiedsConfigs.hudBackgroundColor;

        // Key W (Top Center)
        drawKeyBox(context, textRenderer, "W", x + 18, y, wPressed, bgNormal, bgActive);
        // Key A (Bottom Left)
        drawKeyBox(context, textRenderer, "A", x, y + 18, aPressed, bgNormal, bgActive);
        // Key S (Bottom Center)
        drawKeyBox(context, textRenderer, "S", x + 18, y + 18, sPressed, bgNormal, bgActive);
        // Key D (Bottom Right)
        drawKeyBox(context, textRenderer, "D", x + 36, y + 18, dPressed, bgNormal, bgActive);
    }

    private static void drawKeyBox(DrawContext context, TextRenderer textRenderer, String label, int x, int y, boolean pressed, int normalBg, int activeBg) {
        int size = 16;
        if (CrucifiedsConfigs.hudBackground) {
            context.fill(x, y, x + size, y + size, pressed ? activeBg : normalBg);
        }
        context.drawBorder(x, y, size, size, CrucifiedsTheme.getSecondaryColor());
        int textWidth = textRenderer.getWidth(label);
        context.drawText(textRenderer, label, x + (size - textWidth) / 2, y + 4, CrucifiedsConfigs.hudTextColor, true);
    }

    private static void renderArmorStatus(DrawContext context, MinecraftClient client, int x, int y) {
        if (client.player == null) return;
        int offsetX = 0;
        // Iterate through armor items: Boots, Leggings, Chestplate, Helmet
        for (ItemStack stack : client.player.getArmorItems()) {
            if (!stack.isEmpty()) {
                context.drawItem(stack, x + offsetX, y);
                context.drawItemInSlot(client.textRenderer, stack, x + offsetX, y);
                offsetX += 18;
            }
        }
    }

    private static int getTotemCount(MinecraftClient client) {
        if (client.player == null) return 0;
        int count = 0;
        if (client.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING) {
            count += client.player.getOffHandStack().getCount();
        }
        for (ItemStack stack : client.player.getInventory().main) {
            if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                count += stack.getCount();
            }
        }
        return count;
    }
}
