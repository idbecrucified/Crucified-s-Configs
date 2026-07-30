package com.example.survivalfly;

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
        int startY = this.height / 4 - 10;

        String[] themes = {"Gamer", "Sea", "Sun", "OLED", "Flash", "Natural", "Rock"};

        for (int i = 0; i < themes.length; i++) {
            String themeName = themes[i];
            boolean isActive = CrucifiedsConfigs.currentTheme.equals(themeName);
            
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal((isActive ? ">> " : "") + themeName + (isActive ? " <<" : "")),
                button -> {
                    CrucifiedsConfigs.currentTheme = themeName;
                    CrucifiedTheme.currentTheme = themeName;
                    this.clearAndInit(); // Refresh buttons to show selection marker
                }
            ).dimensions(centerX - 100, startY + (i * 24), 200, 20).build());
        }

        // Done / Back Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Done"),
            button -> this.close()
        ).dimensions(centerX - 100, startY + (themes.length * 24) + 10, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fillGradient(0, 0, this.width, this.height, CrucifiedTheme.getGradientStart(), CrucifiedTheme.getGradientEnd());
        context.drawCenteredTextWithShadow(this.textRenderer, "Select Client Theme", this.width / 2, 20, CrucifiedTheme.getPrimaryColor());
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
