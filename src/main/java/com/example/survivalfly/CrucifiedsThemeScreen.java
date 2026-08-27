package com.example.survivalfly;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CrucifiedsThemeScreen extends Screen {
    private final Screen parent;

    public CrucifiedsThemeScreen(Screen parent) {
        super(Text.literal("Select Client Theme"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelHeight = 250;
        int panelY = centerY - panelHeight / 2;

        String[] themes = {"Gamer", "Sea", "Sun", "OLED", "Flash", "Natural", "Rock"};
        int startY = panelY + 44;

        for (int i = 0; i < themes.length; i++) {
            String themeName = themes[i];
            boolean isActive = CrucifiedTheme.getCurrentTheme().equalsIgnoreCase(themeName);
            String label = themeName + (isActive ? " [Active]" : "");

            this.addDrawableChild(ButtonWidget.builder(
                Text.literal(label),
                button -> {
                    CrucifiedTheme.setTheme(themeName);
                    MinecraftClient.getInstance().setScreen(new CrucifiedsThemeScreen(parent));
                }
            ).dimensions(centerX - 100, startY + (i * 22), 200, 18).build());
        }

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Back"),
            button -> MinecraftClient.getInstance().setScreen(parent)
        ).dimensions(centerX - 100, startY + (themes.length * 22) + 6, 200, 18).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelWidth = 240;
        int panelHeight = 250;
        int panelX = centerX - panelWidth / 2;
        int panelY = centerY - panelHeight / 2;

        // Black 1px border outline
        context.fill(panelX - 1, panelY - 1, panelX + panelWidth + 1, panelY + panelHeight + 1, 0xFF000000);

        // Entire panel gradient from Primary (top) to Secondary (bottom)
        context.fillGradient(panelX, panelY, panelX + panelWidth, panelY + panelHeight, CrucifiedTheme.getPrimaryColor(), CrucifiedTheme.getSecondaryColor());

        context.drawCenteredTextWithShadow(this.textRenderer, "Select Client Theme", centerX, panelY + 14, 0xFFFFFF);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
