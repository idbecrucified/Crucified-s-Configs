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
            String fpsText = "FPS: " + MinecraftClient.getCurrentFps();
            drawElement(context, textRenderer, fpsText, CrucifiedsConfigs.fpsX, CrucifiedsConfigs.fpsY);
        }

        // 2. Keystrokes (Placeholder layout for W, A, S, D)
        if (CrucifiedsConfigs.keystrokes) {
            drawElement(context, textRenderer, "[W] [A] [S] [D]", CrucifiedsConfigs.keystrokesX, CrucifiedsConfigs.keystrokesY);
        }

        // 3. Armor Status
        if (CrucifiedsConfigs.armorStatus) {
            String armorText = "Armor: " + getArmorDurability(client);
            drawElement(context, textRenderer, armorText, CrucifiedsConfigs.armorX, CrucifiedsConfigs.armorY);
        }

        // 4. CPS Display
        if (CrucifiedsConfigs.cpsDisplay) {
            String cpsText = "CPS: 0"; // Hook up to your actual CPS tracker if available
            drawElement(context, textRenderer, cpsText, CrucifiedsConfigs.cpsX, CrucifiedsConfigs.cpsY);
        }

        // 5. Totem Counter
        if (CrucifiedsConfigs.totemCounter) {
            int totems = getTotemCount(client);
            String totemText = "Totems: " + totems;
            drawElement(context, textRenderer, totemText, CrucifiedsConfigs.totemX, CrucifiedsConfigs.totemY);
        }
    }

    private static void drawElement(DrawContext context, TextRenderer textRenderer, String text, int x, int y) {
        int width = textRenderer.getWidth(text) + 6;
        int height = 14;

        // Render background if enabled in configs
        if (CrucifiedsConfigs.hudBackground) {
            context.fill(x, y, x + width, y + height, CrucifiedsConfigs.hudBackgroundColor);
        }

        // Render text using config text color
        context.drawText(textRenderer, text, x + 3, y + 3, CrucifiedsConfigs.hudTextColor, true);
    }

    private static int getArmorDurability(MinecraftClient client) {
        if (client.player == null) return 0;
        int totalDurability = 0;
        for (ItemStack stack : client.player.getArmorItems()) {
            if (!stack.isEmpty() && stack.isDamageable()) {
                totalDurability += (stack.getMaxDamage() - stack.getDamage());
            }
        }
        return totalDurability;
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
