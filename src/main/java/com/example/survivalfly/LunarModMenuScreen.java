package com.example.survivalfly;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class LunarModMenuScreen extends Screen {
    private final Screen parent;
    private String currentCategory = "PvP";

    public LunarModMenuScreen(Screen parent) {
        super(Text.literal("Crucified's Mod Hub"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Category Selection Buttons (Left side)
        this.addDrawableChild(ButtonWidget.builder(Text.literal("PvP"), button -> {
            this.currentCategory = "PvP";
            this.clearAndInit();
        }).dimensions(centerX - 140, centerY - 60, 80, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Performance"), button -> {
            this.currentCategory = "Performance";
            this.clearAndInit();
        }).dimensions(centerX - 140, centerY - 35, 80, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Graphics"), button -> {
            this.currentCategory = "Graphics";
            this.clearAndInit();
        }).dimensions(centerX - 140, centerY - 10, 80, 20).build());

        // Dynamic Config Buttons based on selected category (Right side)
        int rightX = centerX - 50;
        int startY = centerY - 60;

        if (currentCategory.equals("PvP")) {
            addToggleButton(rightX, startY, "Toggle Sprint", CrucifiedsConfigs.toggleSprint, val -> CrucifiedsConfigs.toggleSprint = val);
            addToggleButton(rightX, startY + 25, "Totem Counter", CrucifiedsConfigs.totemCounter, val -> CrucifiedsConfigs.totemCounter = val);
            addToggleButton(rightX, startY + 50, "Armor Status", CrucifiedsConfigs.armorStatus, val -> CrucifiedsConfigs.armorStatus = val);
            addToggleButton(rightX, startY + 75, "CPS Display", CrucifiedsConfigs.cpsDisplay, val -> CrucifiedsConfigs.cpsDisplay = val);
            addToggleButton(rightX, startY + 100, "Keystrokes", CrucifiedsConfigs.keystrokes, val -> CrucifiedsConfigs.keystrokes = val);
        } else if (currentCategory.equals("Performance")) {
            addToggleButton(rightX, startY, "Entity Culling", CrucifiedsConfigs.entityCulling, val -> CrucifiedsConfigs.entityCulling = val);
            addToggleButton(rightX, startY + 25, "FPS Booster", CrucifiedsConfigs.fpsBooster, val -> CrucifiedsConfigs.fpsBooster = val);
            addToggleButton(rightX, startY + 50, "Chunk Animator", CrucifiedsConfigs.chunkAnimator, val -> CrucifiedsConfigs.chunkAnimator = val);
            addToggleButton(rightX, startY + 75, "Particle Multiplier", CrucifiedsConfigs.particleMultiplier, val -> CrucifiedsConfigs.particleMultiplier = val);
        } else if (currentCategory.equals("Graphics")) {
            addToggleButton(rightX, startY, "Fullbright", CrucifiedsConfigs.fullbright, val -> CrucifiedsConfigs.fullbright = val);
            addToggleButton(rightX, startY + 25, "Dynamic Lighting", CrucifiedsConfigs.dynamicLighting, val -> CrucifiedsConfigs.dynamicLighting = val);
            addToggleButton(rightX, startY + 50, "Minimal HUD", CrucifiedsConfigs.minimalHud, val -> CrucifiedsConfigs.minimalHud = val);
            addToggleButton(rightX, startY + 75, "Weather Changer", CrucifiedsConfigs.weatherChanger, val -> CrucifiedsConfigs.weatherChanger = val);
        }

        // Done Button at the bottom
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Done"), button -> {
            this.client.setScreen(parent);
        }).dimensions(centerX + 30, centerY + 80, 100, 20).build());
    }

    private void addToggleButton(int x, int y, String label, boolean currentState, java.util.function.Consumer<Boolean> toggler) {
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal(label + ": " + (currentState ? "§aON" : "§cOFF")),
                button -> {
                    toggler.accept(!currentState);
                    this.clearAndInit(); // Refresh UI to update text color/state
                }
        ).dimensions(x, y, 180, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        
        // Header title
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 95, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Category: §d" + currentCategory), this.width / 2, this.height / 2 - 80, 0xFFFFFF);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
