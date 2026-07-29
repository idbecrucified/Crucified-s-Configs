package com.example.survivalfly.screen;

import com.example.survivalfly.CrucifiedTheme;
import com.example.survivalfly.CrucifiedsConfigs;
import com.example.survivalfly.util.UIUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CrucifiedModsScreen extends Screen {
    private final Screen parent;

    public CrucifiedModsScreen(Screen parent) {
        super(Text.literal("Crucified's Mod Hub"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelWidth = 280;
        int panelHeight = 200;
        int panelX = centerX - panelWidth / 2;
        int panelY = centerY - panelHeight / 2;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("PvP"), button -> {}).dimensions(panelX + 10, panelY + 45, 80, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Performance"), button -> {}).dimensions(panelX + 10, panelY + 70, 80, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Graphics"), button -> {}).dimensions(panelX + 10, panelY + 95, 80, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Toggle Sprint: " + (CrucifiedsConfigs.toggleSprint ? "ON" : "OFF")), button -> {
            CrucifiedsConfigs.toggleSprint = !CrucifiedsConfigs.toggleSprint;
            button.setMessage(Text.literal("Toggle Sprint: " + (CrucifiedsConfigs.toggleSprint ? "ON" : "OFF")));
        }).dimensions(panelX + 100, panelY + 45, 170, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("CPS Display: " + (CrucifiedsConfigs.cpsDisplay ? "ON" : "OFF")), button -> {
            CrucifiedsConfigs.cpsDisplay = !CrucifiedsConfigs.cpsDisplay;
            button.setMessage(Text.literal("CPS Display: " + (CrucifiedsConfigs.cpsDisplay ? "ON" : "OFF")));
        }).dimensions(panelX + 100, panelY + 70, 170, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Keystrokes: " + (CrucifiedsConfigs.keystrokes ? "ON" : "OFF")), button -> {
            CrucifiedsConfigs.keystrokes = !CrucifiedsConfigs.keystrokes;
            button.setMessage(Text.literal("Keystrokes: " + (CrucifiedsConfigs.keystrokes ? "ON" : "OFF")));
        }).dimensions(panelX + 100, panelY + 95, 170, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Armor Status: " + (CrucifiedsConfigs.armorStatus ? "ON" : "OFF")), button -> {
            CrucifiedsConfigs.armorStatus = !CrucifiedsConfigs.armorStatus;
            button.setMessage(Text.literal("Armor Status: " + (CrucifiedsConfigs.armorStatus ? "ON" : "OFF")));
        }).dimensions(panelX + 100, panelY + 120, 170, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Totem Counter: " + (CrucifiedsConfigs.totemCounter ? "ON" : "OFF")), button -> {
            CrucifiedsConfigs.totemCounter = !CrucifiedsConfigs.totemCounter;
            button.setMessage(Text.literal("Totem Counter: " + (CrucifiedsConfigs.totemCounter ? "ON" : "OFF")));
        }).dimensions(panelX + 100, panelY + 145, 170, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), button -> {
            this.client.setScreen(parent);
        }).dimensions(panelX + 170, panelY + panelHeight - 30, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelWidth = 280;
        int panelHeight = 200;
        int panelX = centerX - panelWidth / 2;
        int panelY = centerY - panelHeight / 2;

        UIUtils.drawRoundedRect(context, panelX, panelY, panelWidth, panelHeight, 8, CrucifiedTheme.getBackgroundColor());

        int headerHeight = 35;
        int themeColor1 = CrucifiedTheme.getPrimaryColor();
        int themeColor2 = CrucifiedTheme.getSecondaryColor();

        context.fillGradient(panelX + 2, panelY + 2, panelX + panelWidth - 2, panelY + headerHeight, themeColor1, themeColor2);
        context.fill(panelX, panelY + headerHeight, panelX + panelWidth, panelY + headerHeight + 2, themeColor1);

        context.getMatrices().push();
        context.getMatrices().scale(1.25F, 1.25F, 1.0F);
        String titleText = "Crucified's Mod Hub";
        int scaledWidth = this.textRenderer.getWidth(titleText);
        context.drawText(this.textRenderer, titleText, (int)((panelX + (panelWidth / 2) - (scaledWidth * 1.25F / 2)) / 1.25F), (int)((panelY + 5) / 1.25F), 0xFFFFFFFF, true);
        context.getMatrices().pop();

        String subtitleText = "Category: PvP";
        int subWidth = this.textRenderer.getWidth(subtitleText);
        context.drawText(this.textRenderer, subtitleText, panelX + (panelWidth / 2) - (subWidth / 2), panelY + 22, 0xFFFFB6C1, true);

        super.render(context, mouseX, mouseY, delta);
    }
}
