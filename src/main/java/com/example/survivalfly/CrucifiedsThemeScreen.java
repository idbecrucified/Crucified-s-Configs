package com.example.survivalfly.screen;

import com.example.survivalfly.SurvivalFlyClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CrucifiedsThemeScreen extends Screen {
    private final Screen parent;

    public CrucifiedsThemeScreen(Screen parent) {
        super(Text.literal("Theme Selector"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        int panelLeft = (this.width - 300) / 2;
        int panelTop = (this.height - 240) / 2;
        int buttonWidth = 200;
        int buttonHeight = 20;
        int startY = panelTop + 45;
        int spacing = 23;

        String[] themes = {"Gamer", "Sea", "Sun", "OLED", "Flash", "Natural", "Rock"};

        for (int i = 0; i < themes.length; i++) {
            String themeName = themes[i];
            boolean isSelected = SurvivalFlyClient.currentTheme.equals(themeName);
            String label = themeName + (isSelected ? " (Active)" : "");

            this.addDrawableChild(ButtonWidget.builder(Text.literal(label), b -> {
                SurvivalFlyClient.currentTheme = themeName;
                this.clearAndInit();
            }).dimensions(panelLeft + 50, startY + (i * spacing), buttonWidth, buttonHeight).build());
        }

        // Back Button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), b -> {
            if (this.client != null) {
                this.client.setScreen(this.parent);
            }
        }).dimensions(panelLeft + 100, panelTop + 210, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        int panelLeft = (this.width - 300) / 2;
        int panelTop = (this.height - 240) / 2;

        // Container background matching selected theme
        context.fill(panelLeft, panelTop, panelLeft + 300, panelTop + 240, SurvivalFlyClient.getBackgroundColor());

        // Header banner
        context.fill(panelLeft, panelTop, panelLeft + 300, panelTop + 32, SurvivalFlyClient.getHeaderColor());
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Select HUD Theme"), panelLeft + 150, panelTop + 10, 0xFFFFFF);

        // Accent divider
        context.fill(panelLeft, panelTop + 32, panelLeft + 300, panelTop + 35, SurvivalFlyClient.getAccentColor());

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }
}
