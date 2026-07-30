package com.example.survivalfly;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class HudRenderer {
    public static void renderHud(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden || client.player == null) {
            return;
        }

        TextRenderer tr = client.textRenderer;

        if (CrucifiedsConfigs.fpsCounter) {
            String text = "FPS: " + client.getCurrentFps();
            drawHudBox(context, tr, text, CrucifiedsConfigs.fpsX, CrucifiedsConfigs.fpsY);
        }
        if (CrucifiedsConfigs.cpsDisplay) {
            String text = "CPS: " + CrucifiedsMod.cps;
            drawHudBox(context, tr, text, CrucifiedsConfigs.cpsX, CrucifiedsConfigs.cpsY);
        }
        if (CrucifiedsConfigs.totemCounter) {
            String text = "Totems: " + getTotemCount(client);
            drawHudBox(context, tr, text, CrucifiedsConfigs.totemX, CrucifiedsConfigs.totemY);
        }
        if (CrucifiedsConfigs.armorStatus) {
            String text = "[Armor Status]";
            drawHudBox(context, tr, text, CrucifiedsConfigs.armorX, CrucifiedsConfigs.armorY);
        }
        if (CrucifiedsConfigs.keystrokes) {
            renderKeystrokes(context, tr, CrucifiedsConfigs.keystrokesX, CrucifiedsConfigs.keystrokesY);
        }
    }

    private static void drawHudBox(DrawContext context, TextRenderer tr, String text, int x, int y) {
        int width = tr.getWidth(text) + 6;
        int height = 14;

        if (CrucifiedsConfigs.hudBackground) {
            context.fill(x, y, x + width, y + height, CrucifiedsConfigs.hudBackgroundColor);
        }
        context.drawText(tr, text, x + 3, y + 3, CrucifiedsConfigs.hudTextColor, true);
    }

    private static void renderKeystrokes(DrawContext context, TextRenderer tr, int x, int y) {
        int bg = CrucifiedsConfigs.hudBackgroundColor;
        boolean w = clientKey(net.minecraft.client.option.KeyBinding.W_KEY);
        boolean a = clientKey(net.minecraft.client.option.KeyBinding.A_KEY);
        boolean s = clientKey(net.minecraft.client.option.KeyBinding.S_KEY);
        boolean d = clientKey(net.minecraft.client.option.KeyBinding.D_KEY);

        drawKeyBox(context, tr, "W", x + 18, y, w, bg);
        drawKeyBox(context, tr, "A", x, y + 18, a, bg);
        drawKeyBox(context, tr, "S", x + 18, y + 18, s, bg);
        drawKeyBox(context, tr, "D", x + 36, y + 18, d, bg);
    }

    private static void drawKeyBox(DrawContext context, TextRenderer tr, String label, int x, int y, boolean pressed, int bg) {
        int size = 16;
        if (CrucifiedsConfigs.hudBackground) {
            context.fill(x, y, x + size, y + size, pressed ? CrucifiedTheme.getPrimaryColor() : bg);
        }
        context.drawBorder(x, y, size, size, CrucifiedTheme.getSecondaryColor());
        int tw = tr.getWidth(label);
        context.drawText(tr, label, x + (size - tw) / 2, y + 4, CrucifiedsConfigs.hudTextColor, true);
    }

    private static int getTotemCount(MinecraftClient client) {
        int count = 0;
        if (client.player == null) return 0;
        for (net.minecraft.item.ItemStack stack : client.player.getInventory().main) {
            if (stack.isOf(net.minecraft.item.Items.TOTEM_OF_UNDYING)) {
                count += stack.getCount();
            }
        }
        if (client.player.getOffHandStack().isOf(net.minecraft.item.Items.TOTEM_OF_UNDYING)) {
            count += client.player.getOffHandStack().getCount();
        }
        return count;
    }

    private static boolean clientKey(net.minecraft.client.util.InputUtil.Key key) {
        // Fallback key state checker if needed, or handled via keybindings
        return false; 
    }
}
