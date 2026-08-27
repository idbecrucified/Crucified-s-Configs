package com.example.survivalfly.screen;

import com.example.survivalfly.CrucifiedTheme;
import com.example.survivalfly.CrucifiedsThemeScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CrucifiedRootMenuScreen extends Screen {
    private final Screen parent;

    public CrucifiedRootMenuScreen(Screen parent) {
        super(Text.literal("Crucified Client Hub"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Mods"),
            button -> MinecraftClient.getInstance().setScreen(new CrucifiedModsScreen(this))
        ).dimensions(centerX - 100, centerY - 15, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Themes"),
            button -> MinecraftClient.getInstance().setScreen(new CrucifiedsThemeScreen(this))
        ).dimensions(centerX - 100, centerY + 10, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Back"),
            button -> MinecraftClient.getInstance().setScreen(parent)
        ).dimensions(centerX - 100, centerY + 35, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelWidth = 260;
        int panelHeight = 160;
        int panelX = centerX - panelWidth / 2;
        int panelY = centerY - panelHeight / 2;

        // Black 1px border outline
        context.fill(panelX - 1, panelY - 1, panelX + panelWidth + 1, panelY + panelHeight + 1, 0xFF000000);

        // Entire panel gradient from Primary (top) to Secondary (bottom)
        context.fillGradient(panelX, panelY, panelX + panelWidth, panelY + panelHeight, CrucifiedTheme.getPrimaryColor(), CrucifiedTheme.getSecondaryColor());

        context.drawCenteredTextWithShadow(this.textRenderer, "Crucified Client Hub", centerX, panelY + 14, 0xFFFFFF);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
