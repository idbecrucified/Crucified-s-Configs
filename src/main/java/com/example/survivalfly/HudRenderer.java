package com.example.survivalfly;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class HudRenderer implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden || client.player == null) {
            return;
        }

        TextRenderer textRenderer = client.textRenderer;
        int yOffset = 10;

        // 1. FPS Counter
        if (SurvivalFlyClient.fpsCounter) {
            context.drawTextWithShadow(textRenderer, "FPS: " + client.getCurrentFps(), 10, yOffset, 0xFFFFFF);
            yOffset += 12;
        }

        // 2. CPS Display
        if (SurvivalFlyClient.cpsDisplay) {
            context.drawTextWithShadow(textRenderer, "CPS: " + SurvivalFlyClient.getCps(), 10, yOffset, 0xFFFFFF);
            yOffset += 12;
        }

        // 3. Toggle Sprint Indicator
        if (SurvivalFlyClient.toggleSprint && client.player.isSprinting()) {
            context.drawTextWithShadow(textRenderer, "[Sprinting]", 10, yOffset, 0x55FF55);
            yOffset += 12;
        }

        // 4. Totem Counter
        if (SurvivalFlyClient.totemCounter) {
            int totems = getTotemCount(client);
            if (totems > 0) {
                context.drawTextWithShadow(textRenderer, "Totems: " + totems, 10, yOffset, 0xFFA500);
                yOffset += 12;
            }
        }

        // 5. Keystrokes widget display (Top Right)
        if (SurvivalFlyClient.keystrokes) {
            int kx = client.getWindow().getScaledWidth() - 70;
            int ky = 10;
            context.fill(kx, ky, kx + 60, ky + 60, 0x77000000);
            
            boolean w = client.options.forwardKey.isPressed();
            boolean a = client.options.leftKey.isPressed();
            boolean s = client.options.backKey.isPressed();
            boolean d = client.options.rightKey.isPressed();

            context.drawCenteredTextWithShadow(textRenderer, "W", kx + 30, ky + 6, w ? 0xFF55FF : 0xFFFFFF);
            context.drawTextWithShadow(textRenderer, "A", kx + 10, ky + 26, a ? 0xFF55FF : 0xFFFFFF);
            context.drawCenteredTextWithShadow(textRenderer, "S", kx + 30, ky + 26, s ? 0xFF55FF : 0xFFFFFF);
            context.drawTextWithShadow(textRenderer, "D", kx + 50, ky + 26, d ? 0xFF55FF : 0xFFFFFF);
        }
    }

    private int getTotemCount(MinecraftClient client) {
        int count = 0;
        if (client.player == null) return 0;
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
}
