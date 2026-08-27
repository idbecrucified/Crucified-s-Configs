package com.example.survivalfly;

import com.example.survivalfly.screen.CrucifiedModsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class HudRenderer {

    public static class HudElement {
        public String name;
        public int x, y, width, height;
        public final int defaultX, defaultY;

        public HudElement(String name, int defaultX, int defaultY, int width, int height) {
            this.name = name;
            this.defaultX = defaultX;
            this.defaultY = defaultY;
            this.x = defaultX;
            this.y = defaultY;
            this.width = width;
            this.height = height;
        }

        public boolean isHovered(int mouseX, int mouseY) {
            return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        }

        public boolean isEnabled() {
            switch (name) {
                case "Keystrokes": return CrucifiedModsScreen.isKeystrokesEnabled();
                case "FPS": return CrucifiedModsScreen.isFpsEnabled();
                case "CPS": return CrucifiedModsScreen.isCpsEnabled();
                case "Armor": return CrucifiedModsScreen.isArmorEnabled();
                case "Sprint": return CrucifiedModsScreen.isToggleSprintEnabled();
                default: return true;
            }
        }
    }

    public static final List<HudElement> ELEMENTS = new ArrayList<>();
    private static final List<Long> leftClicks = new ArrayList<>();
    private static final List<Long> rightClicks = new ArrayList<>();
    private static boolean lastLeftState = false;
    private static boolean lastRightState = false;

    static {
        ELEMENTS.add(new HudElement("Keystrokes", 20, 20, 74, 50));
        ELEMENTS.add(new HudElement("FPS", 20, 76, 65, 16));
        ELEMENTS.add(new HudElement("CPS", 20, 96, 78, 16));
        ELEMENTS.add(new HudElement("Armor", 20, 116, 75, 72));
        ELEMENTS.add(new HudElement("Sprint", 20, 192, 100, 16));
    }

    public static void resetToDefaults() {
        for (HudElement el : ELEMENTS) {
            el.x = el.defaultX;
            el.y = el.defaultY;
        }
    }

    public static void updateCpsTrackers() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getWindow() == null) return;
        long handle = client.getWindow().getHandle();
        long now = System.currentTimeMillis();

        boolean leftPressed = GLFW.glfwGetMouseButton(handle, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
        boolean rightPressed = GLFW.glfwGetMouseButton(handle, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;

        if (leftPressed && !lastLeftState) leftClicks.add(now);
        if (rightPressed && !lastRightState) rightClicks.add(now);

        lastLeftState = leftPressed;
        lastRightState = rightPressed;

        leftClicks.removeIf(time -> now - time > 1000);
        rightClicks.removeIf(time -> now - time > 1000);
    }

    public static void renderHud(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden || client.player == null) return;

        for (HudElement element : ELEMENTS) {
            if (!element.isEnabled()) continue;

            switch (element.name) {
                case "Keystrokes": renderKeystrokes(context, element); break;
                case "FPS": renderFps(context, element); break;
                case "CPS": renderCps(context, element); break;
                case "Armor": renderArmor(context, element); break;
                case "Sprint": renderSprint(context, element); break;
            }
        }
    }

    private static void renderKeystrokes(DrawContext context, HudElement el) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getWindow() == null) return;
        long handle = client.getWindow().getHandle();
        int size = 22;

        renderKey(context, "W", el.x + size + 2, el.y, size, size, InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_W));
        renderKey(context, "A", el.x, el.y + size + 2, size, size, InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_A));
        renderKey(context, "S", el.x + size + 2, el.y + size + 2, size, size, InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_S));
        renderKey(context, "D", el.x + (size * 2) + 4, el.y + size + 2, size, size, InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_D));
    }

    private static void renderKey(DrawContext context, String label, int x, int y, int w, int h, boolean pressed) {
        MinecraftClient client = MinecraftClient.getInstance();
        int bg = pressed ? 0xEE374151 : 0xCC111827;
        context.fill(x, y, x + w, y + h, bg);
        context.fill(x, y, x + w, y + 1, pressed ? 0xFF60A5FA : 0x33FFFFFF);
        context.drawCenteredTextWithShadow(client.textRenderer, Text.literal(label), x + (w / 2), y + (h / 2) - 4, pressed ? 0xFF60A5FA : 0xFFFFFFFF);
    }

    private static void renderFps(DrawContext context, HudElement el) {
        MinecraftClient client = MinecraftClient.getInstance();
        renderModernBox(context, el.x, el.y, el.width, el.height, "FPS: " + client.getCurrentFps());
    }

    private static void renderCps(DrawContext context, HudElement el) {
        renderModernBox(context, el.x, el.y, el.width, el.height, "CPS: " + leftClicks.size() + " | " + rightClicks.size());
    }

    private static void renderSprint(DrawContext context, HudElement el) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.player.isSprinting()) {
            renderModernBox(context, el.x, el.y, el.width, el.height, "[Sprinting]");
        }
    }

    private static void renderArmor(DrawContext context, HudElement el) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        int yOffset = 0;
        for (ItemStack stack : client.player.getInventory().armor) {
            if (!stack.isEmpty()) {
                context.fill(el.x, el.y + yOffset, el.x + el.width, el.y + yOffset + 16, 0xCC111827);
                context.fill(el.x, el.y + yOffset, el.x + el.width, el.y + yOffset + 1, 0x33FFFFFF);
                context.drawItem(stack, el.x + 2, el.y + yOffset);

                int maxDurability = stack.getMaxDamage();
                int currentDamage = stack.getDamage();
                int durPercent = maxDurability > 0 ? (int) (((maxDurability - currentDamage) / (float) maxDurability) * 100) : 100;

                context.drawTextWithShadow(client.textRenderer, Text.literal(durPercent + "%"), el.x + 22, el.y + yOffset + 4, 0xFFE5E7EB);
                yOffset += 18;
            }
        }
        el.height = Math.max(18, yOffset);
    }

    private static void renderModernBox(DrawContext context, int x, int y, int w, int h, String text) {
        MinecraftClient client = MinecraftClient.getInstance();
        context.fill(x, y, x + w, y + h, 0xCC111827);
        context.fill(x, y, x + w, y + 1, 0x33FFFFFF);
        context.drawTextWithShadow(client.textRenderer, Text.literal(text), x + 6, y + (h - 8) / 2, 0xFFE5E7EB);
    }
}
