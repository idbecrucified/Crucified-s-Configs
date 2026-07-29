package com.example.survivalfly.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CrucifiedHudEditScreen extends Screen {
    private final Screen parent;
    private final String currentDescription = "Hover over or toggle settings to see descriptions here.";

    // No-argument constructor to match SurvivalFlyClient.java call
    public CrucifiedHudEditScreen() {
        this(null);
    }

    public CrucifiedHudEditScreen(Screen parent) {
        super(Text.literal("Crucified's Mods Manager"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int startY = 65;
        int buttonWidth = 200;
        int buttonHeight = 20;
        int spacing = 4;

        // Configuration Toggles
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Toggle Sprint: ON"), button -> {
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

        // Back Button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back to HUD Editor"), button -> {
            if (this.client != null) {
                this.client.setScreen(this.parent);
            }
        }).dimensions(centerX - (buttonWidth / 2), startY + (buttonHeight + spacing) * 7 + 10, buttonWidth, buttonHeight).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);

        // Draw Logo at the top
        int logoWidth = 48;
        int logoHeight = 48;
        int logoX = (this.width - logoWidth) / 2;
        int logoY = 10;
        
        context.drawTexture(
            new Identifier("survivalfly", "textures/gui/logo.png"), 
            logoX, logoY, 
            0, 0, 
            logoWidth, logoHeight, 
            logoWidth, logoHeight
        );

        // Draw Title below the logo
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, logoY + logoHeight + 4, 0xFF55FF);

        // Description section at the bottom of the screen
        int descY = this.height - 35;
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("[Descriptions]"), this.width / 2, descY, 0xAAAAAA);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(this.currentDescription), this.width / 2, descY + 12, 0xFFFFFF);
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }
}
