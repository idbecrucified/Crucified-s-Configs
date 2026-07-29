package com.example.survivalfly;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class HudRenderer implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden) {
            return;
        }

        TextRenderer textRenderer = client.textRenderer;

        // Render CPS counter example
        String cpsText = "CPS: " + SurvivalFlyClient.getCps();
        context.drawTextWithShadow(textRenderer, cpsText, 10, 10, 0xFFFFFF);
    }
}
