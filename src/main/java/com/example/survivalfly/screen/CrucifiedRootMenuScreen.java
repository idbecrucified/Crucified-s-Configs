package com.example.survivalfly.screen;

import com.example.survivalfly.SurvivalFlyClient;
import com.example.survivalfly.util.UIUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CrucifiedRootMenuScreen extends Screen {

    public CrucifiedRootMenuScreen() {
        super(Text.literal("Crucified Client Hub"));
    }

    @Override
    protected void init() {
        super.init();

        int panelWidth = 320;
        int panelHeight = 220;
        int panelLeft = (this.width - panelWidth) / 2;
        int panelTop = (this.height - panelHeight) / 2;

        // Mods Button (Opens Mod Configuration Menu)
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Mods"), b -> {
            if (this.client != null) {
                this.client.setScreen(new CrucifiedModsScreen(this));
            }
        }).dimensions(panelLeft + 85, panelTop + 90, 150, 24).build());

        // Themes Button on Bottom Left
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Themes"), b -> {
            if (this.client != null) {
                this.client.setScreen(new CrucifiedsThemeScreen(this));
            }
        }).dimensions(panelLeft + 15, panelTop + 185, 95, 20).build());

        // Done Button on Bottom Right
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> {
            if (this.client != null) {
                this.client.setScreen(null);
            }
        }).dimensions(panelLeft + 210, panelTop + 185, 95, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        int panelWidth = 320;
        int panelHeight = 220;
        int panelLeft = (this.width - panelWidth) / 2;
        int panelTop = (this.height - 220) / 2;

        // Rounded Main Container
        UIUtils.drawRoundedRect(context, panelLeft, panelTop, panelWidth, panelHeight, 10, SurvivalFlyClient.getBackgroundColor());

        // Rounded Header Banner
        UIUtils.drawRoundedRect(context, panelLeft, panelTop, panelWidth, 35, 10, SurvivalFlyClient.getHeaderColor());
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Crucified Client Hub"), panelLeft + 160, panelTop + 8, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Active HUD Overlays Manager"), panelLeft + 160, panelTop + 20, SurvivalFlyClient.getAccentColor());

        // Accent Divider
        context.fill(panelLeft, panelTop + 35, panelLeft + panelWidth, panelTop + 38, SurvivalFlyClient.getAccentColor());

        // HUD Status Description Box
        UIUtils.drawRoundedRect(context, panelLeft + 20, panelTop + 50, panelWidth - 40, 30, 5, 0x44000000);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("HUD Layout Preview Active"), panelLeft + 160, panelTop + 60, 0xAAAAAA);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
