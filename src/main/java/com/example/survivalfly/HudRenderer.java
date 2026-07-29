package com.example.survivalfly;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class HudEditorScreen extends Screen {
    private String selectedHud = null;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;

    public HudEditorScreen() {
        super(Text.literal("HUD Editor"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        if (selectedHud != null) {
            if (GLFW.glfwGetMouseButton(client.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_RELEASE) {
                selectedHud = null;
            } else {
                updateHudPosition(selectedHud, mouseX - dragOffsetX, mouseY - dragOffsetY);
            }
        }

        // Render draggable interactive previews using config colors
        drawHudBox(context, "FPS: 60", CrucifiedsConfigs.fpsX, CrucifiedsConfigs.fpsY, "fps");
        drawHudBox(context, "Keystrokes", CrucifiedsConfigs.keystrokesX, CrucifiedsConfigs.keystrokesY, "keystrokes");
        drawHudBox(context, "Armor Status", CrucifiedsConfigs.armorX, CrucifiedsConfigs.armorY, "armor");
        drawHudBox(context, "CPS: 0", CrucifiedsConfigs.cpsX, CrucifiedsConfigs.cpsY, "cps");
        drawHudBox(context, "Totem: 0", CrucifiedsConfigs.totemX, CrucifiedsConfigs.totemY, "totem");

        context.drawCenteredTextWithShadow(this.textRenderer, "Drag HUD elements to reposition. Press Right Shift or ESC to exit.", this.width / 2, 20, CrucifiedTheme.getPrimaryColor());
        super.render(context, mouseX, mouseY, delta);
    }

    private void drawHudBox(DrawContext context, String text, int x, int y, String id) {
        int w = textRenderer.getWidth(text) + 6;
        int h = 14;
        double scaledMouseX = client.mouse.getX() * (double)client.getWindow().getScaledWidth() / (double)client.getWindow().getWidth();
        double scaledMouseY = client.mouse.getY() * (double)client.getWindow().getScaledHeight() / (double)client.getWindow().getHeight();
        boolean hovered = scaledMouseX >= x && scaledMouseX <= x + w && scaledMouseY >= y && scaledMouseY <= y + h;

        if (CrucifiedsConfigs.hudBackground) {
            context.fill(x, y, x + w, y + h, CrucifiedsConfigs.hudBackgroundColor);
        }
        context.drawBorder(x, y, w, h, hovered ? CrucifiedTheme.getPrimaryColor() : 0xFFFFFFFF);
        context.drawText(this.textRenderer, text, x + 3, y + 3, CrucifiedsConfigs.hudTextColor, true);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (isHovered(CrucifiedsConfigs.fpsX, CrucifiedsConfigs.fpsY, "FPS: 60", mouseX, mouseY)) {
                selectedHud = "fps"; dragOffsetX = (int)(mouseX - CrucifiedsConfigs.fpsX); dragOffsetY = (int)(mouseY - CrucifiedsConfigs.fpsY);
            } else if (isHovered(CrucifiedsConfigs.keystrokesX, CrucifiedsConfigs.keystrokesY, "Keystrokes", mouseX, mouseY)) {
                selectedHud = "keystrokes"; dragOffsetX = (int)(mouseX - CrucifiedsConfigs.keystrokesX); dragOffsetY = (int)(mouseY - CrucifiedsConfigs.keystrokesY);
            } else if (isHovered(CrucifiedsConfigs.armorX, CrucifiedsConfigs.armorY, "Armor Status", mouseX, mouseY)) {
                selectedHud = "armor"; dragOffsetX = (int)(mouseX - CrucifiedsConfigs.armorX); dragOffsetY = (int)(mouseY - CrucifiedsConfigs.armorY);
            } else if (isHovered(CrucifiedsConfigs.cpsX, CrucifiedsConfigs.cpsY, "CPS: 0", mouseX, mouseY)) {
                selectedHud = "cps"; dragOffsetX = (int)(mouseX - CrucifiedsConfigs.cpsX); dragOffsetY = (int)(mouseY - CrucifiedsConfigs.cpsY);
            } else if (isHovered(CrucifiedsConfigs.totemX, CrucifiedsConfigs.totemY, "Totem: 0", mouseX, mouseY)) {
                selectedHud = "totem"; dragOffsetX = (int)(mouseX - CrucifiedsConfigs.totemX); dragOffsetY = (int)(mouseY - CrucifiedsConfigs.totemY);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean isHovered(int x, int y, String text, double mouseX, double mouseY) {
        int w = textRenderer.getWidth(text) + 6;
        int h = 14;
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }

    private void updateHudPosition(String id, int x, int y) {
        switch(id) {
            case "fps": CrucifiedsConfigs.fpsX = x; CrucifiedsConfigs.fpsY = y; break;
            case "keystrokes": CrucifiedsConfigs.keystrokesX = x; CrucifiedsConfigs.keystrokesY = y; break;
            case "armor": CrucifiedsConfigs.armorX = x; CrucifiedsConfigs.armorY = y; break;
            case "cps": CrucifiedsConfigs.cpsX = x; CrucifiedsConfigs.cpsY = y; break;
            case "totem": CrucifiedsConfigs.totemX = x; CrucifiedsConfigs.totemY = y; break;
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT || keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
