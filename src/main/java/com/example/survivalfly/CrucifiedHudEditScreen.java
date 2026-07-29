package com.example.survivalfly.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CrucifiedHudEditScreen extends Screen {
    private final Screen parent;
    private String currentDescription = "Hover over or toggle settings to see descriptions here.";

    public CrucifiedHudEditScreen(Screen parent) {
        super(Text.literal("Crucified's Mods Manager"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int startY = 65; // Pushed down to leave space for the logo at the top
        int buttonWidth = 200;
        int buttonHeight = 20;
        int spacing = 4;

        // Configuration Toggles
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Toggle Sprint: ON"), button -> {
            // Your toggle logic here
        }).dimensions(centerX - (buttonWidth / 2), startY, buttonWidth, buttonHeight).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Totem Counter: ON"), button -> {
        }).dimensions(centerX - (buttonWidth / 2), startY + (buttonHeight + spacing), buttonWidth, buttonHeight).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Armor Status: ON"), button -> {
        }).dimensions(centerX - (buttonWidth / 2), startY + (buttonHeight + spacing) * 2, buttonWidth, buttonHeight).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("CPS Display: ON"), button -> {
        }).dimensions(centerX - (buttonWidth / 2), startY + (buttonHeight + spacing) * 3, buttonWidth, buttonHeight).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Keystrokes: ON"), button -> {
        }).dimensions(centerX - (buttonWidth / 2), startY + (buttonHeight + spacing) * 4, buttonWidth, buttonHeight).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("FPS Counter: ON"), button -> {
        }).dimensions(centerX - (buttonWidth / 2), startY + (buttonHeight + spacing) * 5, buttonWidth, buttonHeight).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Fullbright: ON"), button -> {
        }).dimensions(centerX - (buttonWidth / 2), startY + (buttonHeight + spacing) * 6, buttonWidth, buttonHeight).build());

        // Back Button safely pushed down below the grid
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back to HUD Editor"), button -> {
            this.client.setScreen(this.parent);
        }).dimensions(centerX - (buttonWidth / 2), startY + (buttonHeight + spacing) * 7 + 10, buttonWidth, buttonHeight).build());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);

        // Draw Logo at the top
        int logoWidth = 48;
        int logoHeight = 48;
        int logoX = (this.width - logoWidth) / 2;
        int logoY = 10;
        
        RenderSystem.setShaderTexture(0, new Identifier("survivalfly", "textures/gui/logo.png"));
        DrawableHelper.drawTexture(matrices, logoX, logoY, 0, 0, logoWidth, logoHeight, logoWidth, logoHeight);

        // Draw Title below the logo
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, logoY + logoHeight + 4, 0xFF55FF);

        // Fixed description section positioned safely at the bottom of the screen
        int descY = this.height - 35;
        drawCenteredText(matrices, this.textRenderer, Text.literal("[Descriptions]"), this.width / 2, descY, 0xAAAAAA);
        drawCenteredText(matrices, this.textRenderer, Text.literal(this.currentDescription), this.width / 2, descY + 12, 0xFFFFFF);
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }
}
