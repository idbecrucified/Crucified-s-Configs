package com.example.survivalfly;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class HudRenderer {
    public static void register() {
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.options.hudHidden || client.options.debugEnabled || client.player == null) return;
            renderAllHuds(context, client, false);
        });
    }

    public static void renderAllHuds(DrawContext context, MinecraftClient client, boolean editing) {
        TextRenderer textRenderer = client.textRenderer;
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        // 1. FPS Counter
        if (CrucifiedsConfigs.fpsCounter) {
            String fpsText = "FPS: " + client.getCurrentFps();
            if (editing) context.fill(CrucifiedsConfigs.fpsCounterX - 2, CrucifiedsConfigs.fpsCounterY - 2, CrucifiedsConfigs.fpsCounterX + 60, CrucifiedsConfigs.fpsCounterY + 12, 0x55DA70D6);
            context.drawTextWithShadow(textRenderer, fpsText, CrucifiedsConfigs.fpsCounterX, CrucifiedsConfigs.fpsCounterY, 0xFFDA70D6);
        }

        // 2. Totem Counter
        if (CrucifiedsConfigs.totemCounter && client.player != null) {
            int totems = getTotemCount(client);
            if (totems > 0 || editing) {
                String totemText = "Totems: " + totems;
                if (editing) context.fill(CrucifiedsConfigs.totemCounterX - 2, CrucifiedsConfigs.totemCounterY - 2, CrucifiedsConfigs.totemCounterX + 70, CrucifiedsConfigs.totemCounterY + 12, 0x55DA70D6);
                context.drawTextWithShadow(textRenderer, totemText, CrucifiedsConfigs.totemCounterX, CrucifiedsConfigs.totemCounterY, 0xFFFF69B4);
            }
        }

        // 3. Keystrokes
        if (CrucifiedsConfigs.keystrokes) {
            if (editing) context.fill(CrucifiedsConfigs.keystrokesX - 2, CrucifiedsConfigs.keystrokesY - 2, CrucifiedsConfigs.keystrokesX + 62, CrucifiedsConfigs.keystrokesY + 42, 0x55DA70D6);
            renderKeystrokes(context, client, textRenderer, CrucifiedsConfigs.keystrokesX, CrucifiedsConfigs.keystrokesY);
        }

        // 4. CPS Display
        if (CrucifiedsConfigs.cpsDisplay) {
            String cpsText = "CPS: " + SurvivalFlyClient.getCps();
            if (editing) context.fill(CrucifiedsConfigs.cpsDisplayX - 2, CrucifiedsConfigs.cpsDisplayY - 2, CrucifiedsConfigs.cpsDisplayX + 50, CrucifiedsConfigs.cpsDisplayY + 12, 0x55DA70D6);
            context.drawTextWithShadow(textRenderer, cpsText, CrucifiedsConfigs.cpsDisplayX, CrucifiedsConfigs.cpsDisplayY, 0xFFDA70D6);
        }

        // 5. Armor Status
        if (CrucifiedsConfigs.armorStatus && client.player != null) {
            int ax = CrucifiedsConfigs.armorStatusX < 0 ? screenWidth + CrucifiedsConfigs.armorStatusX : CrucifiedsConfigs.armorStatusX;
            int ay = CrucifiedsConfigs.armorStatusY < 0 ? (screenHeight / 2) + CrucifiedsConfigs.armorStatusY : CrucifiedsConfigs.armorStatusY;
            if (editing) context.fill(ax - 2, ay - 2, ax + 18, ay + 70, 0x55DA70D6);
            renderArmorStatus(client, context, ax, ay);
        }
    }

    private static int getTotemCount(MinecraftClient client) {
        int count = 0;
        if (client.player == null) return 0;
        // Single unified inventory scan prevents double-counting main/offhand items
        for (int i = 0; i < client.player.getInventory().size(); i++) {
            ItemStack stack = client.player.getInventory().getStack(i);
            if (stack.isOf(Items.TOTEM_OF_UNDYING)) {
                count += stack.getCount();
            }
        }
        return count;
    }

    private static void renderKeystrokes(DrawContext context, MinecraftClient client, TextRenderer textRenderer, int startX, int startY) {
        boolean w = client.options.forwardKey.isPressed();
        boolean a = client.options.leftKey.isPressed();
        boolean s = client.options.backKey.isPressed();
        boolean d = client.options.rightKey.isPressed();

        context.fill(startX + 20, startY, startX + 38, startY + 18, w ? 0x99DA70D6 : 0x661A111E);
        context.drawCenteredTextWithShadow(textRenderer, "W", startX + 29, startY + 5, 0xFFFFFF);

        context.fill(startX, startY + 20, startX + 18, startY + 38, a ? 0x99DA70D6 : 0x661A111E);
        context.drawCenteredTextWithShadow(textRenderer, "A", startX + 9, startY + 25, 0xFFFFFF);

        context.fill(startX + 20, startY + 20, startX + 38, startY + 38, s ? 0x99DA70D6 : 0x661A111E);
        context.drawCenteredTextWithShadow(textRenderer, "S", startX + 29, startY + 25, 0xFFFFFF);

        context.fill(startX + 40, startY + 20, startX + 58, startY + 38, d ? 0x99DA70D6 : 0x661A111E);
        context.drawCenteredTextWithShadow(textRenderer, "D", startX + 49, startY + 25, 0xFFFFFF);
    }

    private static void renderArmorStatus(MinecraftClient client, DrawContext context, int x, int y) {
        int currentY = y;
        for (int i = 3; i >= 0; i--) {
            ItemStack stack = client.player.getInventory().armor.get(i);
            if (!stack.isEmpty()) {
                context.drawItem(stack, x, currentY);
                currentY += 18;
            }
        }
    }
}
