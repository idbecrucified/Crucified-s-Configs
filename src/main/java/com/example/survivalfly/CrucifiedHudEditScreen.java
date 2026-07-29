package com.example.survivalfly;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CrucifiedHudEditScreen extends Screen {
    private static final Identifier LOGO_TEXTURE = new Identifier("survivalfly", "textures/gui/logo.png");
    
    private String draggingHud = null;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;

    public CrucifiedHudEditScreen() {
        super(Text.literal("Crucified's HUD Overlay"));
    }

    @Override
    protected void init() {
        int buttonWidth = 220;
        int buttonHeight = 30;
        int buttonX = (this.width - buttonWidth) / 2; // Perfectly centers the button horizontally
        int buttonY = this.height - 70;

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("MODS"),
                button -> this.client.setScreen(new LunarModMenuScreen(this))
        ).dimensions(buttonX, buttonY, buttonWidth, buttonHeight).build());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int mx = (int) mouseX;
            int my = (int) mouseY;

            if (CrucifiedsConfigs.fpsCounter && mx >= CrucifiedsConfigs.fpsCounterX && mx <= CrucifiedsConfigs.fpsCounterX + 60 && my >= CrucifiedsConfigs.fpsCounterY && my <= CrucifiedsConfigs.fpsCounterY + 12) {
                draggingHud = "fps";
                dragOffsetX = mx - CrucifiedsConfigs.fpsCounterX;
                dragOffsetY = my - CrucifiedsConfigs.fpsCounterY;
                return true;
            }
            if (CrucifiedsConfigs.totemCounter && mx >= CrucifiedsConfigs.totemCounterX && mx <= CrucifiedsConfigs.totemCounterX + 70 && my >= CrucifiedsConfigs.totemCounterY && my <= CrucifiedsConfigs.totemCounterY + 12) {
                draggingHud = "totem";
                dragOffsetX = mx - CrucifiedsConfigs.totemCounterX;
                dragOffsetY = my - CrucifiedsConfigs.totemCounterY;
                return true;
            }
            if (CrucifiedsConfigs.keystrokes && mx >= CrucifiedsConfigs.keystrokesX && mx <= CrucifiedsConfigs.keystrokesX + 60 && my >= CrucifiedsConfigs.keystrokesY && my <= CrucifiedsConfigs.keystrokesY + 40) {
                draggingHud = "keystrokes";
                dragOffsetX = mx - CrucifiedsConfigs.keystrokesX;
                dragOffsetY = my - CrucifiedsConfigs.keystrokesY;
                return true;
            }
            if (CrucifiedsConfigs.cpsDisplay && mx >= CrucifiedsConfigs.cpsDisplayX && mx <= CrucifiedsConfigs.cpsDisplayX + 50 && my >= CrucifiedsConfigs.cpsDisplayY && my <= CrucifiedsConfigs.cpsDisplayY + 12) {
                draggingHud = "cps";
                dragOffsetX = mx - CrucifiedsConfigs.cpsDisplayX;
                dragOffsetY = my - CrucifiedsConfigs.cpsDisplayY;
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (draggingHud != null) {
            int newX = (int) mouseX - dragOffsetX;
            int newY = (int) mouseY - dragOffsetY;

            switch (draggingHud) {
                case "fps":
                    CrucifiedsConfigs.fpsCounterX = newX;
                    CrucifiedsConfigs.fpsCounterY = newY;
                    break;
                case "totem":
                    CrucifiedsConfigs.totemCounterX = newX;
                    CrucifiedsConfigs.totemCounterY = newY;
                    break;
                case "keystrokes":
                    CrucifiedsConfigs.keystrokesX = newX;
                    CrucifiedsConfigs.keystrokesY = newY;
                    break;
                case "cps":
                    CrucifiedsConfigs.cpsDisplayX = newX;
                    CrucifiedsConfigs.cpsDisplayY = newY;
                    break;
            }
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        draggingHud = null;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        if (this.client != null) {
            HudRenderer.renderAllHuds(context, this.client, true);
        }

        // Render Custom Logo at top center with correct proportion
        int logoWidth = 180;
        int logoHeight = 70;
        int logoX = (this.width - logoWidth) / 2;
        int logoY = 20;
        
        try {
            context.drawTexture(LOGO_TEXTURE, logoX, logoY, 0, 0, logoWidth, logoHeight, logoWidth, logoHeight);
        } catch (Exception e) {
            context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("§d§lCRUCIFIED'S MODS"), this.width / 2, logoY + 25, 0xFFFFFF);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
