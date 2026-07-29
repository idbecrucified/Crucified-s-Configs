package com.example.survivalfly.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CrucifiedHudEditScreen extends Screen {
    private final Screen parent;

    public CrucifiedHudEditScreen(Screen parent) {
        super(Text.literal("Crucified HUD Editor"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int panelWidth = 280;
        int panelHeight = 220;
        int panelLeft = (this.width - panelWidth) / 2;
        int panelTop = (this.height - panelHeight) / 2;

        // Done / Close Button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> {
            if (this.client != null) {
                this.client.setScreen(this.parent);
            }
        }).dimensions(panelLeft + panelWidth - 105, panelTop + panelHeight - 30, 90, 20).build());

        // Themes Button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Themes"), b -> {
            if (this.client != null) {
                this.client.setScreen(new CrucifiedsThemeScreen(this));
            }
        }).dimensions(panelLeft + 15, panelTop + panelHeight - 30, 90, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        int panelWidth = 280;
        int panelHeight = 220;
        int panelLeft = (this.width - panelWidth) / 2;
        int panelTop = (this.height - panelHeight) / 2;

        context.fill(panelLeft, panelTop, panelLeft + panelWidth, panelTop + panelHeight, 0xC0101010);
        context.drawBorder(panelLeft, panelTop, panelWidth, panelHeight, 0xFF555555);

        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, panelTop + 12, 0xFFFFFF);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
