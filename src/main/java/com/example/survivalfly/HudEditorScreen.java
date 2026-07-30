package com.example.survivalfly;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class HudEditorScreen extends Screen {
    private String selectedHud = null;

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
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, "HUD Editor - Click and drag elements to move them", this.width / 2, 10, 0xFFFFFFFF);
        
        drawHudOutline(context, "FPS", CrucifiedsConfigs.fpsX, CrucifiedsConfigs.fpsY, 60, 16, mouseX, mouseY);
        drawHudOutline(context, "Keystrokes", CrucifiedsConfigs.keystrokesX, CrucifiedsConfigs.keystrokesY, 52, 34, mouseX, mouseY);
        drawHudOutline(context, "Armor", CrucifiedsConfigs.armorX, CrucifiedsConfigs.armorY, 72, 16, mouseX, mouseY);
        drawHudOutline(context, "CPS", CrucifiedsConfigs.cpsX, CrucifiedsConfigs.cpsY, 50, 16, mouseX, mouseY);
        drawHudOutline(context, "Totems", CrucifiedsConfigs.totemX, CrucifiedsConfigs.totemY, 65, 16, mouseX, mouseY);

        super.render(context, mouseX, mouseY, delta);
    }

    private void drawHudOutline(DrawContext context, String name, int x, int y, int width, int height, int mouseX, int mouseY) {
        boolean hovered = isInside(mouseX, mouseY, x, y, width, height);
        int color = hovered ? 0xFFFF5555 : 0xFF55FF55;
        context.drawBorder(x, y, width, height, color);
        context.drawText(this.textRenderer, name, x + 2, y + 4, 0xFFFFFFFF, true);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (isInside(mouseX, mouseY, CrucifiedsConfigs.fpsX, CrucifiedsConfigs.fpsY, 60, 16)) {
                selectedHud = "FPS";
            } else if (isInside(mouseX, mouseY, CrucifiedsConfigs.keystrokesX, CrucifiedsConfigs.keystrokesY, 52, 34)) {
                selectedHud = "Keystrokes";
            } else if (isInside(mouseX, mouseY, CrucifiedsConfigs.armorX, CrucifiedsConfigs.armorY, 72, 16)) {
                selectedHud = "Armor";
            } else if (isInside(mouseX, mouseY, CrucifiedsConfigs.cpsX, CrucifiedsConfigs.cpsY, 50, 16)) {
                selectedHud = "CPS";
            } else if (isInside(mouseX, mouseY, CrucifiedsConfigs.totemX, CrucifiedsConfigs.totemY, 65, 16)) {
                selectedHud = "Totems";
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (selectedHud != null) {
            int dx = (int) deltaX;
            int dy = (int) deltaY;
            switch (selectedHud) {
                case "FPS" -> { CrucifiedsConfigs.fpsX += dx; CrucifiedsConfigs.fpsY += dy; }
                case "Keystrokes" -> { CrucifiedsConfigs.keystrokesX += dx; CrucifiedsConfigs.keystrokesY += dy; }
                case "Armor" -> { CrucifiedsConfigs.armorX += dx; CrucifiedsConfigs.armorY += dy; }
                case "CPS" -> { CrucifiedsConfigs.cpsX += dx; CrucifiedsConfigs.cpsY += dy; }
                case "Totems" -> { CrucifiedsConfigs.totemX += dx; CrucifiedsConfigs.totemY += dy; }
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
