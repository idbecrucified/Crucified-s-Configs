package com.example.survivalfly.screen;

import com.example.survivalfly.CrucifiedTheme;
import com.example.survivalfly.CrucifiedsThemeScreen;
import com.example.survivalfly.ThemedButtonWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class CrucifiedMainMenuScreen extends Screen {
    private final Screen parent;

    public CrucifiedMainMenuScreen(Screen parent) {
        super(Text.literal("Crucified's Client Menu"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int startY = centerY - 45;

        // 1. Mods Button
        this.addDrawableChild(new ThemedButtonWidget(
            centerX - 100, startY, 200, 24,
            Text.literal("Mods"),
            button -> MinecraftClient.getInstance().setScreen(new CrucifiedModsScreen(this))
        ));

        // 2. Themes Button
        this.addDrawableChild(new ThemedButtonWidget(
            centerX - 100, startY + 30, 200, 24,
            Text.literal("Themes"),
            button -> MinecraftClient.getInstance().setScreen(new CrucifiedsThemeScreen(this))
        ));

        // 3. HUD Layout Button
        this.addDrawableChild(new ThemedButtonWidget(
            centerX - 100, startY + 60, 200, 24,
            Text.literal("HUD Layout (Edit Positions)"),
            button -> MinecraftClient.getInstance().setScreen(new CrucifiedHudLayoutScreen(this))
        ));

        // Back / Close Button
        this.addDrawableChild(new ThemedButtonWidget(
            centerX - 100, startY + 100, 200, 20,
            Text.literal("Close Menu"),
            button -> MinecraftClient.getInstance().setScreen(parent)
        ));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelWidth = 240;
        int panelHeight = 175;
        int panelX = centerX - panelWidth / 2;
        int panelY = centerY - panelHeight / 2 - 10;

        context.fill(panelX - 1, panelY - 1, panelX + panelWidth + 1, panelY + panelHeight + 1, 0xFF000000);
        context.fillGradient(panelX, panelY, panelX + panelWidth, panelY + panelHeight, CrucifiedTheme.getPrimaryColor(), CrucifiedTheme.getSecondaryColor());
        context.fill(panelX + 1, panelY + 1, panelX + panelWidth - 1, panelY + 2, 0x55FFFFFF);

        CrucifiedTheme.renderThemeDecorations(context, panelX, panelY, panelWidth, panelHeight);

        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Crucified's Client"), centerX, panelY + 12, 0xFFFFFF);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() { return false; }
}
