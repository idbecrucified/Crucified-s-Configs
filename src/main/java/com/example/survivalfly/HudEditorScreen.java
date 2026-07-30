package com.example.survivalfly;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class HudEditorScreen extends Screen {
    private String selectedHud = null;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;

    public HudEditorScreen() {
        super(Text.literal("HUD Editor"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Done"),
            button -> this.close()
        ).dimensions(centerX - 100, this.height - 30, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, "Drag HUD elements to reposition them. Press Right Shift or Done to exit.", this.width / 2, 10, 0xFFFFFFFF);

        // Render actual live HUD elements so you drag the real hud components directly
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer tr = client.textRenderer;

        if (CrucifiedsConfigs.fpsCounter) {
            drawInteractiveHud(context, tr, "FPS: 60", CrucifiedsConfigs.fpsX, CrucifiedsConfigs.fpsY, 60, 14, mouseX, mouseY, "FPS");
        }
        if (CrucifiedsConfigs.keystrokes) {
            drawInteractiveKeystrokes(context, tr, CrucifiedsConfigs.keystrokesX, CrucifiedsConfigs.keystrokesY, mouseX, mouseY);
        }
        if (CrucifiedsConfigs.armorStatus) {
            drawInteractiveHud(context, tr, "[Armor Status]", CrucifiedsConfigs.armorX, CrucifiedsConfigs.armorY, 72, 16, mouseX, mouseY, "Armor");
        }
        if (CrucifiedsConfigs.cpsDisplay) {
            drawInteractiveHud(context, tr, "CPS: 10", CrucifiedsConfigs.cpsX, CrucifiedsConfigs.cpsY, 50, 14, mouseX, mouseY, "CPS");
        }
        if (CrucifiedsConfigs.totemCounter) {
            drawInteractiveHud(context, tr, "Totems: 1", CrucifiedsConfigs.totemX, CrucifiedsConfigs.totemY, 65, 14, mouseX, mouseY, "Totems");
        }

        super.render(context, mouseX, mouseY, delta);
    }

    private void drawInteractiveHud(DrawContext context, TextRenderer textRenderer, String text, int x, int y, int width, int height, int mouseX, int mouseY, String id) {
        boolean hovered = isInside(mouseX, mouseY, x, y, width, height);

        if (CrucifiedsConfigs.hudBackground) {
            context.fill(x, y, x + width, y + height, CrucifiedsConfigs.hudBackgroundColor);
        }
        context.drawText(textRenderer, text, x + 3, y + 3, CrucifiedsConfigs.hudTextColor, true);

        // Subtle highlight outline only when hovered or actively dragging this element
        if (hovered || id.equals(selectedHud)) {
            context.drawBorder(x, y, width, height, 0xFFFF5555);
        }
    }

    private void drawInteractiveKeystrokes(DrawContext context, TextRenderer textRenderer, int x, int y, int mouseX, int mouseY) {
        int width = 52;
        int height = 34;
        boolean hovered = isInside(mouseX, mouseY, x, y, width, height);

        int bgNormal = CrucifiedsConfigs.hudBackgroundColor;
        int bgActive = CrucifiedTheme.getPrimaryColor();

        drawKeyBox(context, textRenderer, "W", x + 18, y, false, bgNormal, bgActive);
        drawKeyBox(context, textRenderer, "A", x, y + 18, false, bgNormal, bgActive);
        drawKeyBox(context, textRenderer, "S", x + 18, y + 18, false, bgNormal, bgActive);
        drawKeyBox(context, textRenderer, "D", x + 36, y + 18, false, bgNormal, bgActive);

        if (hovered || "Keystrokes".equals(selectedHud)) {
            context.drawBorder(x, y, width, height, 0xFFFF5555);
        }
    }

    private void drawKeyBox(DrawContext context, TextRenderer textRenderer, String label, int x, int y, boolean pressed, int normalBg, int activeBg) {
        int size = 16;
        if (CrucifiedsConfigs.hudBackground) {
            context.fill(x, y, x + size, y + size, pressed ? activeBg : normalBg);
        }
        context.drawBorder(x, y, size, size, CrucifiedTheme.getSecondaryColor());
        int textWidth = textRenderer.getWidth(label);
        context.drawText(textRenderer, label, x + (size - textWidth) / 2, y + 4, CrucifiedsConfigs.hudTextColor, true);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (CrucifiedsConfigs.fpsCounter && isInside(mouseX, mouseY, CrucifiedsConfigs.fpsX, CrucifiedsConfigs.fpsY, 60, 14)) {
                selectedHud = "FPS";
                dragOffsetX = (int) mouseX - CrucifiedsConfigs.fpsX;
                dragOffsetY = (int) mouseY - CrucifiedsConfigs.fpsY;
            } else if (CrucifiedsConfigs.keystrokes && isInside(mouseX, mouseY, CrucifiedsConfigs.keystrokesX, CrucifiedsConfigs.keystrokesY, 52, 34)) {
                selectedHud = "Keystrokes";
                dragOffsetX = (int) mouseX - CrucifiedsConfigs.keystrokesX;
                dragOffsetY = (int) mouseY - CrucifiedsConfigs.keystrokesY;
            } else if (CrucifiedsConfigs.armorStatus && isInside(mouseX, mouseY, CrucifiedsConfigs.armorX, CrucifiedsConfigs.armorY, 72, 16)) {
                selectedHud = "Armor";
                dragOffsetX = (int) mouseX - CrucifiedsConfigs.armorX;
                dragOffsetY = (int) mouseY - CrucifiedsConfigs.armorY;
            } else if (CrucifiedsConfigs.cpsDisplay && isInside(mouseX, mouseY, CrucifiedsConfigs.cpsX, CrucifiedsConfigs.cpsY, 50, 14)) {
                selectedHud = "CPS";
                dragOffsetX = (int) mouseX - CrucifiedsConfigs.cpsX;
                dragOffsetY = (int) mouseY - CrucifiedsConfigs.cpsY;
            } else if (CrucifiedsConfigs.totemCounter && isInside(mouseX, mouseY, CrucifiedsConfigs.totemX, CrucifiedsConfigs.totemY, 65, 14)) {
                selectedHud = "Totems";
                dragOffsetX = (int) mouseX - CrucifiedsConfigs.totemX;
                dragOffsetY = (int) mouseY - CrucifiedsConfigs.totemY;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (selectedHud != null) {
            // Absolute offset tracking prevents any jank or stuttery movement drift
            int newX = (int) mouseX - dragOffsetX;
            int newY = (int) mouseY - dragOffsetY;
            switch (selectedHud) {
                case "FPS" -> { CrucifiedsConfigs.fpsX = newX; CrucifiedsConfigs.fpsY = newY; }
                case "Keystrokes" -> { CrucifiedsConfigs.keystrokesX = newX; CrucifiedsConfigs.keystrokesY = newY; }
                case "Armor" -> { CrucifiedsConfigs.armorX = newX; CrucifiedsConfigs.armorY = newY; }
                case "CPS" -> { CrucifiedsConfigs.cpsX = newX; CrucifiedsConfigs.cpsY = newY; }
                case "Totems" -> { CrucifiedsConfigs.totemX = newX; CrucifiedsConfigs.totemY = newY; }
            }
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        selectedHud = null;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private boolean isInside(double mx, double my, int x, int y, int w, int h) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
