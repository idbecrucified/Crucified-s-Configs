package com.example.survivalfly;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;

public class HudRenderer {
    public static void register() {
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || client.options.hudHidden) return;

            if (CrucifiedsConfigs.fpsCounter) {
                String fpsText = "FPS: " + MinecraftClient.getInstance().getCurrentFps();
                context.drawTextWithShadow(client.textRenderer, fpsText, CrucifiedsConfigs.fpsCounterX, CrucifiedsConfigs.fpsCounterY, 0xEC4899);
            }

            if (CrucifiedsConfigs.totemCounter) {
                int totems = 0;
                for (ItemStack stack : client.player.getInventory().main) {
                    if (stack.isOf(Items.TOTEM_OF_UNDYING)) totems += stack.getCount();
                }
                if (client.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
                    totems += client.player.getOffHandStack().getCount();
                }

                if (totems > 0) {
                    context.drawItem(new ItemStack(Items.TOTEM_OF_UNDYING), CrucifiedsConfigs.totemCounterX, CrucifiedsConfigs.totemCounterY);
                    context.drawTextWithShadow(client.textRenderer, "§dx" + totems, CrucifiedsConfigs.totemCounterX + 18, CrucifiedsConfigs.totemCounterY + 4, 0xFFFFFF);
                }
            }
        });
    }
}
