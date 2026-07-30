package com.example.survivalfly.screen;

import com.example.survivalfly.CrucifiedTheme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CrucifiedRootMenuScreen extends Screen {
    private final Screen parent;

    public CrucifiedRootMenuScreen(Screen parent) {
        super(Text.literal("Crucified Menu"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Mods"),
            button -> MinecraftClient.getInstance().setScreen(new CrucifiedModsScreen(this))
        ).dimensions(centerX - 100, centerY - 10, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Themes"),
            button -> MinecraftClient.getInstance().setScreen(new ThemeSelectionScreen(this))
        ).dimensions(10, this.height - 30, 80, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, "Crucified Configs", this.width / 2, this.height / 2 - 40, CrucifiedTheme.getPrimaryColor());
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
