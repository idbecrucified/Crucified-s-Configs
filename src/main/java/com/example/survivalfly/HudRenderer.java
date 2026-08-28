package com.example.survivalfly;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class HudRenderer {
    public static final List<HudElement> ELEMENTS = new ArrayList<>();
    
    // CPS Tracking Lists
    private static final List<Long> leftClicks = new ArrayList<>();
    private static final List<Long> rightClicks = new ArrayList<>();
    private static boolean wasLeftPressed = false;
    private static boolean wasRightPressed = false;

    static {
        ELEMENTS.add(new HudElement("FPS Display", 10, 10, 60, 18, true));
        ELEMENTS.add(new HudElement("CPS Display", 10, 32, 60, 18, true));
        ELEMENTS.add(new HudElement("Keystrokes", 10, 54, 54, 54, true));
        ELEMENTS.add(new HudElement("Armor Status", 10, 112, 60, 64, true));
    }

    public static void updateCpsTrackers() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        long currentTime = System.currentTimeMillis();

        // Left Click Detection
        boolean leftPressed = client.options.attackKey.isPressed();
        if (leftPressed && !wasLeftPressed) {
            leftClicks.add(currentTime);
        }
        wasLeftPressed = leftPressed;

        // Right Click Detection
        boolean rightPressed = client.options.useKey.isPressed();
        if (rightPressed && !wasRightPressed) {
            rightClicks.add(currentTime);
        }
        wasRightPressed = rightPressed;

        // Cleanup clicks older than 1 second (1000ms)
        leftClicks.removeIf(time -> time < currentTime - 1000);
        rightClicks.removeIf(time -> time < currentTime - 1000);
    }

    public static void renderHud(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden || client.player == null) return;

        int primary = CrucifiedTheme.getPrimaryColor();
        int secondary = CrucifiedTheme.getSecondaryColor();

        int bgColor = (primary & 0x00FFFFFF) | 0x77000000;
        int borderColor = (secondary & 0x00FFFFFF) | 0xAA000000;

        for (HudElement element : ELEMENTS) {
            if (!element.isEnabled()) continue;

            ThemedButtonWidget.drawRoundedRect(context, element.x, element.y, element.width, element.height, 6, bgColor);
            ThemedButtonWidget.drawRoundedOutline(context, element.x, element.y, element.width, element.height, 6, borderColor);

            if (element.name.equals("FPS Display")) {
                String fps = client.getCurrentFps() + " FPS";
                context.drawTextWithShadow(client.textRenderer, fps, element.x + 6, element.y + 5, 0xFFFFFFFF);
            } 
            else if (element.name.equals("CPS Display")) {
                String cps = leftClicks.size() + " CPS";
                context.drawTextWithShadow(client.textRenderer, cps, element.x + 6, element.y + 5, 0xFFFFFFFF);
            } 
            else if (element.name.equals("Keystrokes")) {
                boolean w = client.options.forwardKey.isPressed();
                boolean a = client.options.leftKey.isPressed();
                boolean s = client.options.backKey.isPressed();
                boolean d = client.options.rightKey.isPressed();

                renderKey(context, "W", element.x + 18, element.y + 2, 18, 16, w, borderColor);
                renderKey(context, "A", element.x + 2, element.y + 20, 16, 16, a, borderColor);
                renderKey(context, "S", element.x + 20, element.y + 20, 16, 16, s, borderColor);
                renderKey(context, "D", element.x + 38, element.y + 20, 16, 16, d, borderColor);
            } 
            else if (element.name.equals("Armor Status")) {
                int slotY = element.y + 2;
                // Render armor items from Helmet (3) down to Boots (0)
                for (int i = 3; i >= 0; i--) {
                    ItemStack stack = client.player.getInventory().getArmorStack(i);
                    if (!stack.isEmpty()) {
                        context.drawItem(stack, element.x + 4, slotY);
                        int durability = stack.getMaxDamage() - stack.getDamage();
                        String durStr = stack.isDamageable() ? String.valueOf(durability) : "100%";
                        context.drawTextWithShadow(client.textRenderer, durStr, element.x + 24, slotY + 4, 0xFFFFFFFF);
                    }
                    slotY += 15;
                }
            }
        }
    }

    private static void renderKey(DrawContext context, String label, int x, int y, int w, int h, boolean pressed, int borderColor) {
        int fill = pressed ? 0x99FFFFFF : 0x33000000;
        ThemedButtonWidget.drawRoundedRect(context, x, y, w, h, 3, fill);
        ThemedButtonWidget.drawRoundedOutline(context, x, y, w, h, 3, borderColor);
        MinecraftClient client = MinecraftClient.getInstance();
        context.drawTextWithShadow(client.textRenderer, label, x + (w - 6) / 2, y + (h - 8) / 2, pressed ? 0xFF000000 : 0xFFFFFFFF);
    }

    public static void resetToDefaults() {
        ELEMENTS.get(0).x = 10; ELEMENTS.get(0).y = 10;
        ELEMENTS.get(1).x = 10; ELEMENTS.get(1).y = 32;
        ELEMENTS.get(2).x = 10; ELEMENTS.get(2).y = 54;
        ELEMENTS.get(3).x = 10; ELEMENTS.get(3).y = 112;
    }

    public static class HudElement {
        public String name;
        public int x, y, width, height;
        private boolean enabled;

        public HudElement(String name, int x, int y, int width, int height, boolean enabled) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.enabled = enabled;
        }

        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
        public boolean isHovered(int mouseX, int mouseY) {
            return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        }
    }
}
