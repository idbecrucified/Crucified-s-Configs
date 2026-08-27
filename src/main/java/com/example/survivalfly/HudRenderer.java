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

        public HudElement(String name, int x, int y, int width, int height) {
            this.name = name;
            this.x = x;
            this.y = y;
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
        ELEMENTS.add(new HudElement("Keystrokes", 20, 20, 72, 48));
        ELEMENTS.add(new HudElement("FPS", 20, 75, 60, 16));
        ELEMENTS.add(new HudElement("CPS", 20, 95, 75, 16));
        ELEMENTS.add(new HudElement("Armor", 20, 115, 70, 70));
        ELEMENTS.add(new HudElement("Sprint", 20, 190, 100, 16));
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

        int primary = CrucifiedTheme.getPrimaryColor();
        int secondary = CrucifiedTheme.getSecondaryColor();

        for (HudElement element : ELEMENTS) {
            if (!element.isEnabled()) continue;

            switch (element.name) {
                case "Keystrokes":
                    renderKeystrokes(context, element, primary, secondary);
                    break;
                case "FPS":
                    renderFps(context, element, primary, secondary);
                    break;
                case "CPS":
                    renderCps(context, element, primary, secondary);
                    break;
                case "Armor":
                    renderArmor(context, element, primary, secondary);
                    break;
                case "Sprint":
                    renderSprint(context, element, primary, secondary);
                    break;
            }
        }
    }

    private static void renderKeystrokes(DrawContext context, HudElement el, int primary, int secondary) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getWindow() == null) return;
        long handle = client.getWindow().getHandle();
        int boxSize = 22;

        renderKey(context, "W", el.x + boxSize + 2, el.y, boxSize, boxSize, InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_W), primary, secondary);
        renderKey(context, "A", el.x, el.y + boxSize + 2, boxSize, boxSize, InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_A), primary, secondary);
        renderKey(context, "S", el.x + boxSize + 2, el.y + boxSize + 2, boxSize, boxSize, InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_S), primary, secondary);
        renderKey(context, "D", el.x + (boxSize * 2) + 4, el.y + boxSize + 2, boxSize, boxSize, InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_D), primary, secondary);
    }

    private static void renderKey(DrawContext context, String label, int x, int y, int w, int h, boolean pressed, int primary, int secondary) {
        MinecraftClient client = MinecraftClient.getInstance();
        context.fill(x - 1, y - 1, x + w + 1, y + h + 1, 0xFF000000);
        context.fillGradient(x, y, x + w, y + h, pressed ? primary : (primary & 0x80FFFFFF), pressed ? secondary : (secondary & 0x80FFFFFF));
        context.drawCenteredTextWithShadow(client.textRenderer, Text.literal(label), x + (w / 2), y + (h / 2) - 4, pressed ? 0xFFFF00 : 0xFFFFFF);
    }

    private static void renderFps(DrawContext context, HudElement el, int primary, int secondary) {
        MinecraftClient client = MinecraftClient.getInstance();
        String text = "FPS: " + client.getCurrentFps();
        renderBoxWithText(context, el.x, el.y, el.width, el.height, text, primary, secondary);
    }

    private static void renderCps(DrawContext context, HudElement el, int primary, int secondary) {
        String text = "CPS: " + leftClicks.size() + " | " + rightClicks.size();
        renderBoxWithText(context, el.x, el.y, el.width, el.height, text, primary, secondary);
    }

    private static void renderSprint(DrawContext context, HudElement el, int primary, int secondary) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.player.isSprinting()) {
            renderBoxWithText(context, el.x, el.y, el.width, el.height, "[Sprinting]", primary, secondary);
        }
    }

    private static void renderArmor(DrawContext context, HudElement el, int primary, int secondary) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        int yOffset = 0;
        for (ItemStack stack : client.player.getInventory().armor) {
            if (!stack.isEmpty()) {
                context.fill(el.x - 1, el.y + yOffset - 1, el.x + el.width + 1, el.y + yOffset + 17, 0xFF000000);
                context.fillGradient(el.x, el.y + yOffset, el.x + el.width, el.y + yOffset + 16, primary, secondary);
                context.drawItem(stack, el.x + 2, el.y + yOffset);

                int maxDurability = stack.getMaxDamage();
                int currentDamage = stack.getDamage();
                int durPercent = maxDurability > 0 ? (int) (((maxDurability - currentDamage) / (float) maxDurability) * 100) : 100;

                context.drawTextWithShadow(client.textRenderer, Text.literal(durPercent + "%"), el.x + 22, el.y + yOffset + 4, 0xFFFFFF);
                yOffset += 18;
            }
        }
        el.height = Math.max(18, yOffset);
    }

    private static void renderBoxWithText(DrawContext context, int x, int y, int w, int h, String text, int primary, int secondary) {
        MinecraftClient client = MinecraftClient.getInstance();
        context.fill(x - 1, y - 1, x + w + 1, y + h + 1, 0xFF000000);
        context.fillGradient(x, y, x + w, y + h, primary, secondary);
        context.drawTextWithShadow(client.textRenderer, Text.literal(text), x + 4, y + (h - 8) / 2, 0xFFFFFF);
    }
}
