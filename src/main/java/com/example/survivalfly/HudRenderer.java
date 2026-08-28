package com.example.survivalfly;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class HudRenderer {
    public static final List<HudElement> ELEMENTS = new ArrayList<>();
    
    private static final List<Long> leftClicks = new ArrayList<>();
    private static final List<Long> rightClicks = new ArrayList<>();
    private static boolean wasLeftPressed = false;
    private static boolean wasRightPressed = false;

    static {
        ELEMENTS.add(new HudElement("FPS Display", 10, 10, 60, 18, true));
        ELEMENTS.add(new HudElement("CPS Display", 10, 32, 60, 18, true));
        ELEMENTS.add(new HudElement("Keystrokes", 10, 54, 54, 72, true)); // Height updated for spacebar
        ELEMENTS.add(new HudElement("Armor Status", 10, 130, 60, 18, true));
    }

    public static void updateCpsTrackers() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        long currentTime = System.currentTimeMillis();

        boolean leftPressed = client.options.attackKey.isPressed();
        if (leftPressed && !wasLeftPressed) leftClicks.add(currentTime);
        wasLeftPressed = leftPressed;

        boolean rightPressed = client.options.useKey.isPressed();
        if (rightPressed && !wasRightPressed) rightClicks.add(currentTime);
        wasRightPressed = rightPressed;

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

            if (element.name.equals("Armor Status")) {
                List<ItemStack> activeItems = new ArrayList<>();
                // Armor pieces
                for (int i = 3; i >= 0; i--) {
                    ItemStack armor = client.player.getInventory().getArmorStack(i);
                    if (!armor.isEmpty()) activeItems.add(armor);
                }
                // Offhand item (Shield / Totem / etc)
                ItemStack offhand = client.player.getOffHandStack();
                if (!offhand.isEmpty()) activeItems.add(offhand);

                // Mainhand item (Shield / Totem / etc)
                ItemStack mainhand = client.player.getMainHandStack();
                if (!mainhand.isEmpty() && (mainhand.getItem().toString().contains("shield") || mainhand.getItem().toString().contains("totem"))) {
                    activeItems.add(mainhand);
                }

                // Dynamic hiding if no armor or valid items equipped
                if (activeItems.isEmpty()) continue;

                // Adjust box height dynamically to match amount of active items
                element.height = activeItems.size() * 18 + 4;
                ThemedButtonWidget.drawRoundedRect(context, element.x, element.y, element.width, element.height, 4, bgColor);
                ThemedButtonWidget.drawRoundedOutline(context, element.x, element.y, element.width, element.height, 4, borderColor);

                int slotY = element.y + 3;
                for (ItemStack stack : activeItems) {
                    context.drawItem(stack, element.x + 4, slotY - 1);
                    String label = stack.isDamageable() ? String.valueOf(stack.getMaxDamage() - stack.getDamage()) : stack.getCount() + "x";
                    context.drawTextWithShadow(client.textRenderer, label, element.x + 24, slotY + 3, 0xFFFFFFFF);
                    slotY += 18;
                }
                continue;
            }

            ThemedButtonWidget.drawRoundedRect(context, element.x, element.y, element.width, element.height, 4, bgColor);
            ThemedButtonWidget.drawRoundedOutline(context, element.x, element.y, element.width, element.height, 4, borderColor);

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
                boolean jump = client.options.jumpKey.isPressed();

                renderKey(context, "W", element.x + 18, element.y + 2, 18, 16, w, borderColor);
                renderKey(context, "A", element.x + 2, element.y + 20, 16, 16, a, borderColor);
                renderKey(context, "S", element.x + 20, element.y + 20, 16, 16, s, borderColor);
                renderKey(context, "D", element.x + 38, element.y + 20, 16, 16, d, borderColor);
                // Spacebar
                renderKey(context, "───", element.x + 2, element.y + 38, 50, 14, jump, borderColor);
            }
        }
    }

    private static void renderKey(DrawContext context, String label, int x, int y, int w, int h, boolean pressed, int borderColor) {
        int fill = pressed ? 0x88FFFFFF : 0x33000000;
        int textColor = 0xFFFFFFFF; // Always white to fix black text flashing

        ThemedButtonWidget.drawRoundedRect(context, x, y, w, h, 3, fill);
        ThemedButtonWidget.drawRoundedOutline(context, x, y, w, h, 3, borderColor);
        MinecraftClient client = MinecraftClient.getInstance();
        int textX = x + (w - client.textRenderer.getWidth(label)) / 2;
        int textY = y + (h - 8) / 2;
        context.drawTextWithShadow(client.textRenderer, label, textX, textY, textColor);
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
