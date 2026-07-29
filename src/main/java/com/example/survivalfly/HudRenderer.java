package com.example.survivalfly;

import com.example.survivalfly.util.UIUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;

public class HudRenderer {
    public static void renderHud(DrawContext context, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden || client.player == null) return;

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        // 1. Keystrokes HUD
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

            UIUtils.drawRoundedRect(context, startX + size + gap, startY, size, size, 3, wPressed ? pressCol : bgCol);
            context.drawCenteredTextWithShadow(client.textRenderer, "W", startX + size + gap + size / 2, startY + 6, 0xFFFFFFFF);

            UIUtils.drawRoundedRect(context, startX, startY + size + gap, size, size, 3, aPressed ? pressCol : bgCol);
            context.drawCenteredTextWithShadow(client.textRenderer, "A", startX + size / 2, startY + size + gap + 6, 0xFFFFFFFF);

            UIUtils.drawRoundedRect(context, startX + size + gap, startY + size + gap, size, size, 3, sPressed ? pressCol : bgCol);
            context.drawCenteredTextWithShadow(client.textRenderer, "S", startX + size + gap + size / 2, startY + size + gap + 6, 0xFFFFFFFF);

            UIUtils.drawRoundedRect(context, startX + (size + gap) * 2, startY + size + gap, size, size, 3, dPressed ? pressCol : bgCol);
            context.drawCenteredTextWithShadow(client.textRenderer, "D", startX + (size + gap) * 2 + size / 2, startY + size + gap + 6, 0xFFFFFFFF);
        }

        // 2. CPS Display HUD
        if (CrucifiedsConfigs.cpsDisplay) {
            String cpsText = "CPS: 0";
            UIUtils.drawRoundedRect(context, 10, 10, 60, 18, 3, 0x88000000);
            context.drawTextWithShadow(client.textRenderer, cpsText, 15, 15, 0xFFFFFFFF);
        }

        // 3. Toggle Sprint Status HUD
        if (CrucifiedsConfigs.toggleSprint) {
            String sprintText = client.player.isSprinting() ? "[Sprinting]" : "[Sprint: Off]";
            context.drawTextWithShadow(client.textRenderer, sprintText, 10, 35, 0xFF55FF55);
        }

        // 4. Armor Status HUD
        if (CrucifiedsConfigs.armorStatus) {
            int x = 10;
            int y = screenHeight - 60;
            for (ItemStack stack : client.player.getArmorItems()) {
                if (!stack.isEmpty()) {
                    context.drawItem(stack, x, y);
                    y -= 20;
                }
            }
        }

        // 5. Totem Counter HUD
        if (CrucifiedsConfigs.totemCounter) {
            int totemCount = 0;
            for (ItemStack stack : client.player.getInventory().main) {
                if (stack.isOf(Items.TOTEM_OF_UNDYING)) {
                    totemCount += stack.getCount();
                }
            }
            if (client.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
                totemCount += client.player.getOffHandStack().getCount();
            }

            int totemX = screenWidth / 2 - 10;
            int totemY = screenHeight - 65;
            
            UIUtils.drawRoundedRect(context, totemX - 25, totemY - 4, 60, 24, 4, 0x88000000);
            context.drawItem(new ItemStack(Items.TOTEM_OF_UNDYING), totemX - 20, totemY);
            context.drawTextWithShadow(client.textRenderer, "x" + totemCount, totemX + 2, totemY + 6, 0xFFFFFFFF);
        }
    }
}
