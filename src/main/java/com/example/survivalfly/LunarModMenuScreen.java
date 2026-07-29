package com.example.survivalfly;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class LunarModMenuScreen extends Screen {
    private final Screen parent;

    public LunarModMenuScreen(Screen parent) {
        super(Text.literal("Crucified's Mods Manager"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        int centerX = this.width / 2;
        int startY = 50;
        int buttonWidth = 220;
        int buttonHeight = 22;
        int spacing = 26;

        // 1. Toggle Sprint
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Toggle Sprint: " + (CrucifiedsConfigs.toggleSprint ? "ON" : "OFF")),
                button -> {
                    CrucifiedsConfigs.toggleSprint = !CrucifiedsConfigs.toggleSprint;
                    button.setMessage(Text.literal("Toggle Sprint: " + (CrucifiedsConfigs.toggleSprint ? "ON" : "OFF")));
                }
        ).dimensions(centerX - buttonWidth / 2, startY, buttonWidth, buttonHeight).build());

        // 2. Totem Counter
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Totem Counter: " + (CrucifiedsConfigs.totemCounter ? "ON" : "OFF")),
                button -> {
                    CrucifiedsConfigs.totemCounter = !CrucifiedsConfigs.totemCounter;
                    button.setMessage(Text.literal("Totem Counter: " + (CrucifiedsConfigs.totemCounter ? "ON" : "OFF")));
                }
        ).dimensions(centerX - buttonWidth / 2, startY + (spacing * 1), buttonWidth, buttonHeight).build());

        // 3. Armor Status
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Armor Status: " + (CrucifiedsConfigs.armorStatus ? "ON" : "OFF")),
                button -> {
                    CrucifiedsConfigs.armorStatus = !CrucifiedsConfigs.armorStatus;
                    button.setMessage(Text.literal("Armor Status: " + (CrucifiedsConfigs.armorStatus ? "ON" : "OFF")));
                }
        ).dimensions(centerX - buttonWidth / 2, startY + (spacing * 2), buttonWidth, buttonHeight).build());

        // 4. CPS Display
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("CPS Display: " + (CrucifiedsConfigs.cpsDisplay ? "ON" : "OFF")),
                button -> {
                    CrucifiedsConfigs.cpsDisplay = !CrucifiedsConfigs.cpsDisplay;
                    button.setMessage(Text.literal("CPS Display: " + (CrucifiedsConfigs.cpsDisplay ? "ON" : "OFF")));
                }
        ).dimensions(centerX - buttonWidth / 2, startY + (spacing * 3), buttonWidth, buttonHeight).build());

        // 5. Keystrokes
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Keystrokes: " + (CrucifiedsConfigs.keystrokes ? "ON" : "OFF")),
                button -> {
                    CrucifiedsConfigs.keystrokes = !CrucifiedsConfigs.keystrokes;
                    button.setMessage(Text.literal("Keystrokes: " + (CrucifiedsConfigs.keystrokes ? "ON" : "OFF")));
                }
        ).dimensions(centerX - buttonWidth / 2, startY + (spacing * 4), buttonWidth, buttonHeight).build());

        // 6. FPS Counter
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("FPS Counter: " + (CrucifiedsConfigs.fpsCounter ? "ON" : "OFF")),
                button -> {
                    CrucifiedsConfigs.fpsCounter = !CrucifiedsConfigs.fpsCounter;
                    button.setMessage(Text.literal("FPS Counter: " + (CrucifiedsConfigs.fpsCounter ? "ON" : "OFF")));
                }
        ).dimensions(centerX - buttonWidth / 2, startY + (spacing * 5), buttonWidth, buttonHeight).build());

        // 7. Fullbright
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Fullbright: " + (CrucifiedsConfigs.fullbright ? "ON" : "OFF")),
                button -> {
                    CrucifiedsConfigs.fullbright = !CrucifiedsConfigs.fullbright;
                    button.setMessage(Text.literal("Fullbright: " + (CrucifiedsConfigs.fullbright ? "ON" : "OFF")));
                }
        ).dimensions(centerX - buttonWidth / 2, startY + (spacing * 6), buttonWidth, buttonHeight).build());

        // Back Button
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Back to HUD Editor"),
                button -> this.client.setScreen(parent)
        ).dimensions(centerX - buttonWidth / 2, startY + (spacing * 7) + 10, buttonWidth, buttonHeight).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        
        // Title with dynamic theme color
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, CrucifiedTheme.getPrimaryColor());

        // Mod Descriptions displayed below title / around screen
        int descY = 240;
        context.drawCenteredTextWithShadow(this.textRenderer, "§7[Descriptions]", this.width / 2, descY, 0xAAAAAA);
        context.drawCenteredTextWithShadow(this.textRenderer, "§8• ToggleSprint: Automatically keeps you sprinting without holding W.", this.width / 2, descY + 12, 0x888888);
        context.drawCenteredTextWithShadow(this.textRenderer, "§8• TotemCounter / Armor / CPS / Keystrokes: Live HUD overlays for PvP stats.", this.width / 2, descY + 24, 0x888888);
        context.drawCenteredTextWithShadow(this.textRenderer, "§8• Fullbright: Brightens up dark caves and night environments instantly.", this.width / 2, descY + 36, 0x888888);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}
