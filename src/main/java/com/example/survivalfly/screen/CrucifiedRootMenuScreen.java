package com.example.survivalfly.screen;

import com.example.survivalfly.CrucifiedTheme;
import com.example.survivalfly.CrucifiedsThemeScreen;
import com.example.survivalfly.util.UIUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CrucifiedRootMenuScreen extends Screen {
    private final Screen parent;

    public CrucifiedRootMenuScreen(Screen parent) {
        super(Text.literal("Crucified's Mod Hub"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelWidth = 320;
        int panelHeight = 220;
        int panelLeft = centerX - panelWidth / 2;
        int panelTop = centerY - panelHeight / 2;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Mod Hub"), button -> {
            this.client.setScreen(new CrucifiedModsScreen(this));
        }).dimensions(panelLeft + 60, panelTop + 70, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Theme Settings"), button -> {
            this.client.setScreen(new CrucifiedsThemeScreen(this));
        }).dimensions(panelLeft + 60, panelTop + 100, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Close"), button -> {
            this.client.setScreen(null);
        }).dimensions(panelLeft + 60, panelTop + panelHeight - 40, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelWidth = 320;
        int panelHeight = 220;
        int panelLeft = centerX - panelWidth / 2;
        int panelTop = centerY - panelHeight / 2;

        UIUtils.drawRoundedRect(context, panelLeft, panelTop, panelWidth, panelHeight, 10, CrucifiedTheme.getBackgroundColor());
        UIUtils.drawRoundedRect(context, panelLeft, panelTop, panelWidth, 35, 10, CrucifiedTheme.getHeaderColor());

        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Active HUD Overlays Manager"), panelLeft + 160, panelTop + 20, CrucifiedTheme.getAccentColor());
        context.fill(panelLeft, panelTop + 35, panelLeft + panelWidth, panelTop + 38, CrucifiedTheme.getAccentColor());

        super.render(context, mouseX, mouseY, delta);
    }
}
