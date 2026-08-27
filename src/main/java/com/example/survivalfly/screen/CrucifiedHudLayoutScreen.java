package com.example.survivalfly.screen;

import com.example.survivalfly.CrucifiedTheme;
import com.example.survivalfly.HudRenderer;
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

    public CrucifiedHudLayoutScreen(Screen parent) {
        super(Text.literal("Edit HUD Layout"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        this.addDrawableChild(new ThemedButtonWidget(
            centerX - 60, this.height - 30, 120, 20,
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
            draggedElement.x = Math.max(0, Math.min(this.width - draggedElement.width, (int) mouseX - dragOffsetX));
            draggedElement.y = Math.max(0, Math.min(this.height - draggedElement.height, (int) mouseY - dragOffsetY));
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            draggedElement = null;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        // Highlight Draggable HUD Boxes
        for (HudRenderer.HudElement element : HudRenderer.ELEMENTS) {
            if (element.isEnabled()) {
                boolean hovered = element.isHovered(mouseX, mouseY);
                int borderColor = hovered ? 0xFF00FF00 : 0x88FFFFFF;
                context.fill(element.x - 2, element.y - 2, element.x + element.width + 2, element.y + element.height + 2, borderColor);
                context.fill(element.x - 1, element.y - 1, element.x + element.width + 1, element.y + element.height + 1, 0xCC000000);
            }
        }

        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Click and Drag elements to customize HUD layout"), this.width / 2, 15, 0xFFFF00);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() { return false; }
}
