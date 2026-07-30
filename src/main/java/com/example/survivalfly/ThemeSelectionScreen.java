package com.example.survivalfly.screen;

import com.example.survivalfly.CrucifiedTheme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ThemeSelectionScreen extends Screen {
    private final Screen parent;

    public ThemeSelectionScreen(Screen parent) {
        super(Text.literal("Theme Selection"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = this.height / 2 - 90;
        int spacing = 22;

        String[] themes = {"Gamer", "Sea", "Sun", "OLED", "Flash", "Natural", "Rock"};
        for (int i = 0; i < themes.length; i++) {
            String theme = themes[i];
            boolean isActive = CrucifiedTheme.getCurrentTheme().equalsIgnoreCase(theme);
            String label = theme + (isActive ? " [Active]" : "");

            this.addDrawableChild(ButtonWidget.builder(
                Text.literal(label),
                button -> {
                    CrucifiedTheme.setTheme(theme);
                    // Refresh screen to update active indicators
                    MinecraftClient.getInstance().setScreen(new ThemeSelectionScreen(parent));
                }
            ).dimensions(centerX - 100, startY + (i * spacing), 200, 20).build());
        }

        // Back button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Back"),
            button -> MinecraftClient.getInstance().setScreen(parent)
        ).dimensions(centerX - 100, startY + (themes.length * spacing) + 8, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, "Select Theme", this.width / 2, this.height / 2 - 115, CrucifiedTheme.getPrimaryColor());
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
