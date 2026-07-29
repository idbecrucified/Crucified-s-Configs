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
            if (client.options.hudHidden || client.player == null) return;

            TextRenderer textRenderer = client.textRenderer;

            // 1. FPS Counter
            if (CrucifiedsConfigs.fpsCounter) {
                String fpsText = "FPS: " + client.getCurrentFps();
                context.drawTextWithShadow(textRenderer, fpsText, CrucifiedsConfigs.fpsCounterX, CrucifiedsConfigs.fpsCounterY, 0xFFDA70D6);
            }

            // 2. Totem Counter
            if (CrucifiedsConfigs.totemCounter) {
                int totems = getTotemCount(client);
                if (totems > 0) {
                    context.drawTextWithShadow(textRenderer, "Totems: " + totems, CrucifiedsConfigs.totemCounterX, CrucifiedsConfigs.totemCounterY, 0xFFFF69B4);
                }
            }

            // 3. Keystrokes (W, A, S, D)
            if (CrucifiedsConfigs.keystrokes) {
                renderKeystrokes(context, client, textRenderer);
            }

            // 4. CPS Display
            if (CrucifiedsConfigs.cpsDisplay) {
                context.drawTextWithShadow(textRenderer, "CPS: 0", 5, 45, 0xFFDA70D6);
            }

            // 5. Armor Status
            if (CrucifiedsConfigs.armorStatus) {
                renderArmorStatus(context, client);
            }
        });
    }

    private static int getTotemCount(MinecraftClient client) {
        int count = 0;
        if (client.player.getMainHandStack().isOf(Items.TOTEM_OF_UNDYING)) count += client.player.getMainHandStack().getCount();
        if (client.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) count += client.player.getOffHandStack().getCount();
        for (int i = 0; i < client.player.getInventory().size(); i++) {
            ItemStack stack = client.player.getInventory().getStack(i);
            if (stack.isOf(Items.TOTEM_OF_UNDYING)) {
                count += stack.getCount();
            }
        }
        return count;
    }

    private static void renderKeystrokes(DrawContext context, MinecraftClient client, TextRenderer textRenderer) {
        int startX = 5;
        int startY = 65;
        
        boolean w = client.options.forwardKey.isPressed();
        boolean a = client.options.leftKey.isPressed();
        boolean s = client.options.backKey.isPressed();
        boolean d = client.options.rightKey.isPressed();

        // W key
        context.fill(startX + 20, startY, startX + 38, startY + 18, w ? 0x99DA70D6 : 0x661A111E);
        context.drawCenteredTextWithShadow(textRenderer, "W", startX + 29, startY + 5, 0xFFFFFF);

        // A key
        context.fill(startX, startY + 20, startX + 18, startY + 38, a ? 0x99DA70D6 : 0x661A111E);
        context.drawCenteredTextWithShadow(textRenderer, "A", startX + 9, startY + 25, 0xFFFFFF);

        // S key
        context.fill(startX + 20, startY + 20, startX + 38, startY + 38, s ? 0x99DA70D6 : 0x661A111E);
        context.drawCenteredTextWithShadow(textRenderer, "S", startX + 29, startY + 25, 0xFFFFFF);

        // D key
        context.fill(startX + 40, startY + 20, startX + 58, startY + 38, d ? 0x99DA70D6 : 0x661A111E);
        context.drawCenteredTextWithShadow(textRenderer, "D", startX + 49, startY + 25, 0xFFFFFF);
    }

    private static void renderArmorStatus(DrawContext context, MinecraftClient client) {
        int x = client.getWindow().getScaledWidth() - 25;
        int y = client.getWindow().getScaledHeight() / 2 - 50;

        for (int i = 3; i >= 0; i--) {
            ItemStack stack = client.player.getInventory().armor.get(i);
            if (!stack.isEmpty()) {
                context.drawItem(stack, x, y);
                y += 18;
            }
        }
    }
}
