package com.example.survivalfly.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CrucifiedHudEditScreen extends Screen {
    private final Screen parent;
    private final String currentDescription = "Click buttons to toggle settings ON or OFF.";

    // Interactive toggle states
    private boolean toggleSprint = true;
    private boolean totemCounter = true;
    private boolean armorStatus = true;
    private boolean cpsDisplay = true;
    private boolean keystrokes = true;
    private boolean fpsCounter = true;
    private boolean fullbright = true;

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
        int startY = 75; // Properly spaced down to prevent overlap
        int buttonWidth = 220;
        int buttonHeight = 20;
        int spacing = 4;

        // Toggle Sprint Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Toggle Sprint: " + (toggleSprint ? "ON" : "OFF")), button -> {
                toggleSprint = !toggleSprint;
                button.setMessage(Text.literal("Toggle Sprint: " + (toggleSprint ? "ON" : "OFF")));
            }).dimensions(centerX - (buttonWidth / 2), startY, buttonWidth, buttonHeight).build());

        // Totem Counter Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Totem Counter: " + (totemCounter ? "ON" : "OFF")), button -> {
                totemCounter = !totemCounter;
                button.setMessage(Text.literal("Totem Counter: " + (totemCounter ? "ON" : "OFF")));
            }).dimensions(centerX - (buttonWidth / 2), startY + (buttonHeight + spacing), buttonWidth, buttonHeight).build());

        // Armor Status Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Armor Status: " + (armorStatus ? "ON" : "OFF")), button -> {
                armorStatus = !armorStatus;
                button.setMessage(Text.literal("Armor Status: " + (armorStatus ? "ON" : "OFF")));
            }).dimensions(centerX - (buttonWidth / 2), startY + (buttonHeight + spacing) * 2, buttonWidth, buttonHeight).build());

        // CPS Display Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("CPS Display: " + (cpsDisplay ? "ON" : "OFF")), button -> {
                cpsDisplay = !cpsDisplay;
                button.setMessage(Text.literal("CPS Display: " + (cpsDisplay ? "ON" : "OFF")));
            }).dimensions(centerX - (buttonWidth / 2), startY + (buttonHeight + spacing) * 3, buttonWidth, buttonHeight).build());

        // Keystrokes Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Keystrokes: " + (keystrokes ? "ON" : "OFF")), button -> {
                keystrokes = !keystrokes;
                button.setMessage(Text.literal("Keystrokes: " + (keystrokes ? "ON" : "OFF")));
            }).dimensions(centerX - (buttonWidth / 2), startY + (buttonHeight + spacing) * 4, buttonWidth, buttonHeight).build());

        // FPS Counter Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("FPS Counter: " + (fpsCounter ? "ON" : "OFF")), button -> {
                fpsCounter = !fpsCounter;
                button.setMessage(Text.literal("FPS Counter: " + (fpsCounter ? "ON" : "OFF")));
            }).dimensions(centerX - (buttonWidth / 2), startY + (buttonHeight + spacing) * 5, buttonWidth, buttonHeight).build());

        // Fullbright Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Fullbright: " + (fullbright ? "ON" : "OFF")), button -> {
                fullbright = !fullbright;
                button.setMessage(Text.literal("Fullbright: " + (fullbright ? "ON" : "OFF")));
            }).dimensions(centerX - (buttonWidth / 2), startY + (buttonHeight + spacing) * 6, buttonWidth, buttonHeight).build());

        // Back Button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back to HUD Editor"), button -> {
            if (this.client != null) {
                this.client.setScreen(this.parent);
            }
        }).dimensions(centerX - (buttonWidth / 2), startY + (buttonHeight + spacing) * 7 + 8, buttonWidth, buttonHeight).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);

        // Clean, vibrant header replacing the broken texture square
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("§lCRUCIFIED'S MODS"), this.width / 2, 20, 0xFF55FF);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 34, 0xAAAAAA);

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
