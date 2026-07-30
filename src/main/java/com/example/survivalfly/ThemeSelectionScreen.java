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
        int centerY = this.height / 2;

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Red Theme"),
            button -> CrucifiedTheme.setTheme("Red")
        ).dimensions(centerX - 100, centerY - 40, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Blue Theme"),
            button -> CrucifiedTheme.setTheme("Blue")
        ).dimensions(centerX - 100, centerY - 10, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Back"),
            button -> MinecraftClient.getInstance().setScreen(parent)
        ).dimensions(centerX - 100, centerY + 30, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, "Select Theme", this.width / 2, this.height / 2 - 70, CrucifiedTheme.getPrimaryColor());
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
