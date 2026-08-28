package com.example.survivalfly;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import java.util.ArrayList;
import java.util.List;

public class HudRenderer {
    public static final List<HudElement> ELEMENTS = new ArrayList<>();

    static {
        ELEMENTS.add(new HudElement("FPS Display", 10, 10, 60, 18, true));
        ELEMENTS.add(new HudElement("CPS Display", 10, 32, 60, 18, true));
        ELEMENTS.add(new HudElement("Keystrokes", 10, 54, 54, 54, true));
    }

    public static void renderHud(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden) return;

        int primary = CrucifiedTheme.getPrimaryColor();
        int secondary = CrucifiedTheme.getSecondaryColor();

        int bgColor = (primary & 0x00FFFFFF) | 0x66000000;
        int borderColor = (secondary & 0x00FFFFFF) | 0xAA000000;

        for (HudElement element : ELEMENTS) {
            if (!element.isEnabled()) continue;

            // Rounded Theme Box Container
            ThemedButtonWidget.drawRoundedRect(context, element.x, element.y, element.width, element.height, 4, bgColor);
            ThemedButtonWidget.drawRoundedOutline(context, element.x, element.y, element.width, element.height, 4, borderColor);

            // Render HUD Content inside
            if (element.name.equals("FPS Display")) {
                String fps = client.getCurrentFps() + " FPS";
                context.drawTextWithShadow(client.textRenderer, fps, element.x + 6, element.y + 5, 0xFFFFFFFF);
            } else if (element.name.equals("CPS Display")) {
                String cps = "0 CPS";
                context.drawTextWithShadow(client.textRenderer, cps, element.x + 6, element.y + 5, 0xFFFFFFFF);
            } else if (element.name.equals("Keystrokes")) {
                // Key W
                ThemedButtonWidget.drawRoundedOutline(context, element.x + 18, element.y + 2, 18, 16, 2, borderColor);
                context.drawTextWithShadow(client.textRenderer, "W", element.x + 24, element.y + 6, 0xFFFFFFFF);
                // Keys A, S, D
                ThemedButtonWidget.drawRoundedOutline(context, element.x + 2, element.y + 20, 16, 16, 2, borderColor);
                context.drawTextWithShadow(client.textRenderer, "A", element.x + 7, element.y + 24, 0xFFFFFFFF);

                ThemedButtonWidget.drawRoundedOutline(context, element.x + 20, element.y + 20, 16, 16, 2, borderColor);
                context.drawTextWithShadow(client.textRenderer, "S", element.x + 25, element.y + 24, 0xFFFFFFFF);

                ThemedButtonWidget.drawRoundedOutline(context, element.x + 38, element.y + 20, 16, 16, 2, borderColor);
                context.drawTextWithShadow(client.textRenderer, "D", element.x + 43, element.y + 24, 0xFFFFFFFF);
            }
        }
    }

    public static void resetToDefaults() {
        ELEMENTS.get(0).x = 10; ELEMENTS.get(0).y = 10;
        ELEMENTS.get(1).x = 10; ELEMENTS.get(1).y = 32;
        ELEMENTS.get(2).x = 10; ELEMENTS.get(2).y = 54;
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
