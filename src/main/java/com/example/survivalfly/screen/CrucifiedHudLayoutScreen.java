package com.example.survivalfly.screen;

import com.example.survivalfly.HudRenderer;
import com.example.survivalfly.SoundHelper;
import com.example.survivalfly.ThemedButtonWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class CrucifiedHudLayoutScreen extends Screen {
    private final Screen parent;
    private HudRenderer.HudElement draggedElement = null;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;
    private static final int SNAP_DIST = 8;

    public CrucifiedHudLayoutScreen(Screen parent) {
        super(Text.literal("Edit HUD Layout"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;

        this.addDrawableChild(new ThemedButtonWidget(
            centerX - 110, this.height - 30, 105, 20,
            Text.literal("Reset Layout"),
            button -> {
                HudRenderer.resetToDefaults();
                SoundHelper.playReset();
            }
        ));

        this.addDrawableChild(new ThemedButtonWidget(
            centerX + 5, this.height - 30, 105, 20,
            Text.literal("Save & Exit"),
            button -> MinecraftClient.getInstance().setScreen(parent)
        ));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            for (HudRenderer.HudElement element : HudRenderer.ELEMENTS) {
                if (element.isEnabled() && element.isHovered((int) mouseX, (int) mouseY)) {
                    draggedElement = element;
                    dragOffsetX = (int) mouseX - element.x;
                    dragOffsetY = (int) mouseY - element.y;
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (draggedElement != null) {
            int targetX = (int) mouseX - dragOffsetX;
            int targetY = (int) mouseY - dragOffsetY;

            // Screen Corner / Edge Snapping
            if (Math.abs(targetX) < SNAP_DIST) targetX = 0;
            if (Math.abs(targetX + draggedElement.width - this.width) < SNAP_DIST) targetX = this.width - draggedElement.width;
            if (Math.abs(targetY) < SNAP_DIST) targetY = 0;
            if (Math.abs(targetY + draggedElement.height - this.height) < SNAP_DIST) targetY = this.height - draggedElement.height;

            // Element-to-Element Snapping
            for (HudRenderer.HudElement other : HudRenderer.ELEMENTS) {
                if (other == draggedElement || !other.isEnabled()) continue;

                if (Math.abs(targetX - (other.x + other.width + 2)) < SNAP_DIST) targetX = other.x + other.width + 2;
                if (Math.abs((targetX + draggedElement.width) - (other.x - 2)) < SNAP_DIST) targetX = other.x - 2 - draggedElement.width;
                if (Math.abs(targetX - other.x) < SNAP_DIST) targetX = other.x;
                if (Math.abs((targetX + draggedElement.width) - (other.x + other.width)) < SNAP_DIST) targetX = other.x + other.width - draggedElement.width;

                if (Math.abs(targetY - (other.y + other.height + 2)) < SNAP_DIST) targetY = other.y + other.height + 2;
                if (Math.abs((targetY + draggedElement.height) - (other.y - 2)) < SNAP_DIST) targetY = other.y - 2 - draggedElement.height;
                if (Math.abs(targetY - other.y) < SNAP_DIST) targetY = other.y;
                if (Math.abs((targetY + draggedElement.height) - (other.y + other.height)) < SNAP_DIST) targetY = other.y + other.height - draggedElement.height;
            }

            draggedElement.x = Math.max(0, Math.min(this.width - draggedElement.width, targetX));
            draggedElement.y = Math.max(0, Math.min(this.height - draggedElement.height, targetY));
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) draggedElement = null;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        // Render actual HUD content first
        HudRenderer.renderHud(context);

        // Render HOLLOW outlines around HUD elements (No dark filled gray boxes)
        for (HudRenderer.HudElement element : HudRenderer.ELEMENTS) {
            if (element.isEnabled()) {
                boolean isHoveredOrDragged = element.isHovered(mouseX, mouseY) || element == draggedElement;
                int outlineColor = isHoveredOrDragged ? 0xFF00FF00 : 0xFFFFFFFF;
                drawHollowRect(context, element.x - 2, element.y - 2, element.width + 4, element.height + 4, outlineColor);
            }
        }

        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Drag elements to move. They snap to edges and each other."), this.width / 2, 12, 0xFFFF00);
        super.render(context, mouseX, mouseY, delta);
    }

    private void drawHollowRect(DrawContext context, int x, int y, int w, int h, int color) {
        context.fill(x, y, x + w, y + 1, color);
        context.fill(x, y + h - 1, x + w, y + h, color);
        context.fill(x, y, x + 1, y + h, color);
        context.fill(x + w - 1, y, x + w, y + h, color);
    }

    @Override
    public boolean shouldPause() { return false; }
}
