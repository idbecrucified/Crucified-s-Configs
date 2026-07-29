package com.example.survivalfly;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ThemeSelectionScreen extends Screen {
    private final Screen parent;

    public ThemeSelectionScreen(Screen parent) {
        super(Text.literal("Select HUD Theme"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        int centerX = this.width / 2;
        int startY = this.height / 2 - 95;
        int buttonWidth = 140;
        int buttonHeight = 22;
        int spacing = 26;

        String[] themes = {"Gamer", "Sea", "Sun", "OLED", "Flash", "Natural", "Rock"};

        for (int i = 0; i < themes.length; i++) {
            String theme = themes[i];
            boolean isActive = CrucifiedsConfigs.currentTheme.equals(theme);
            this.addDrawableChild(ButtonWidget.builder(
                    Text.literal(theme + (isActive ? " (Active)" : "")),
                    button -> {
                        CrucifiedsConfigs.currentTheme = theme;
                        this.client.setScreen(new ThemeSelectionScreen(parent));
                    }
            ).dimensions(centerX - buttonWidth / 2, startY + (i * spacing), buttonWidth, buttonHeight).build());
        }

        // Back Button
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Back"),
                button -> this.client.setScreen(parent)
        ).dimensions(centerX - buttonWidth / 2, startY + (themes.length * spacing) + 8, buttonWidth, buttonHeight).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 25, CrucifiedTheme.getPrimaryColor());
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}
