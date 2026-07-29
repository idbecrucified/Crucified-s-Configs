package com.example.survivalfly;

import com.example.survivalfly.util.UIUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CrucifiedsThemeScreen extends Screen {
    private final Screen parent;

    public CrucifiedsThemeScreen(Screen parent) {
        super(Text.literal("Theme Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelLeft = centerX - 150;
        int panelTop = centerY - 145;

        String[] themes = {"Gamer", "Sea", "Sun", "OLED", "Flash", "Natural", "Rock"};
        int yOffset = panelTop + 45;

        for (String themeName : themes) {
            boolean isSelected = CrucifiedTheme.currentTheme.equals(themeName);
            this.addDrawableChild(ButtonWidget.builder(Text.literal(themeName + (isSelected ? " (Active)" : "")), button -> {
                CrucifiedTheme.currentTheme = themeName;
                this.client.setScreen(new CrucifiedsThemeScreen(parent));
            }).dimensions(panelLeft + 50, yOffset, 200, 20).build());
            yOffset += 24;
        }

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), button -> {
            this.client.setScreen(parent);
        }).dimensions(panelLeft + 50, panelTop + 270 - 35, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xC0101010);

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelLeft = centerX - 150;
        int panelTop = centerY - 145;

        UIUtils.drawRoundedRect(context, panelLeft, panelTop, 300, 270, 10, CrucifiedTheme.getBackgroundColor());
        UIUtils.drawRoundedRect(context, panelLeft, panelTop, 300, 35, 10, CrucifiedTheme.getHeaderColor());

        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Theme Settings"), panelLeft + 150, panelTop + 12, 0xFFFFFFFF);
        context.fill(panelLeft, panelTop + 35, panelLeft + 300, panelTop + 38, CrucifiedTheme.getAccentColor());

        super.render(context, mouseX, mouseY, delta);
    }
}
