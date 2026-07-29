package com.example.survivalfly.screen;

import com.example.survivalfly.SurvivalFlyClient;
import com.example.survivalfly.util.UIUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CrucifiedModsScreen extends Screen {
    private final Screen parent;
    private static String selectedCategory = "PvP";

    public CrucifiedModsScreen(Screen parent) {
        super(Text.literal("Crucified's Mod Hub"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        int panelLeft = (this.width - 340) / 2;
        int panelTop = (this.height - 220) / 2;

        // Category Buttons (Left Sidebar)
        int catX = panelLeft + 15;
        int catY = panelTop + 50;
        int catWidth = 95;
        int catHeight = 22;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("PvP"), b -> {
            selectedCategory = "PvP";
            this.clearAndInit();
        }).dimensions(catX, catY, catWidth, catHeight).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Performance"), b -> {
            selectedCategory = "Performance";
            this.clearAndInit();
        }).dimensions(catX, catY + 28, catWidth, catHeight).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Graphics"), b -> {
            selectedCategory = "Graphics";
            this.clearAndInit();
        }).dimensions(catX, catY + 56, catWidth, catHeight).build());

        // Right Content Toggles
        int contentX = panelLeft + 125;
        int contentY = panelTop + 50;
        int contentWidth = 200;
        int contentHeight = 20;
        int spacing = 24;

        if (selectedCategory.equals("PvP")) {
            addToggle(contentX, contentY, contentWidth, contentHeight, "Toggle Sprint", SurvivalFlyClient.toggleSprint, val -> SurvivalFlyClient.toggleSprint = val);
            addToggle(contentX, contentY + spacing, contentWidth, contentHeight, "CPS Display", SurvivalFlyClient.cpsDisplay, val -> SurvivalFlyClient.cpsDisplay = val);
            addToggle(contentX, contentY + spacing * 2, contentWidth, contentHeight, "Keystrokes", SurvivalFlyClient.keystrokes, val -> SurvivalFlyClient.keystrokes = val);
            addToggle(contentX, contentY + spacing * 3, contentWidth, contentHeight, "Armor Status", SurvivalFlyClient.armorStatus, val -> SurvivalFlyClient.armorStatus = val);
            addToggle(contentX, contentY + spacing * 4, contentWidth, contentHeight, "Hit Color", SurvivalFlyClient.hitColor, val -> SurvivalFlyClient.hitColor = val);

        } else if (selectedCategory.equals("Performance")) {
            addToggle(contentX, contentY, contentWidth, contentHeight, "FPS Counter", SurvivalFlyClient.fpsCounter, val -> SurvivalFlyClient.fpsCounter = val);
            addToggle(contentX, contentY + spacing, contentWidth, contentHeight, "Fast Render", SurvivalFlyClient.fastRender, val -> SurvivalFlyClient.fastRender = val);
            addToggle(contentX, contentY + spacing * 2, contentWidth, contentHeight, "Zoom Toggle", SurvivalFlyClient.zoomToggle, val -> SurvivalFlyClient.zoomToggle = val);
            addToggle(contentX, contentY + spacing * 3, contentWidth, contentHeight, "Chunk Animator", SurvivalFlyClient.chunkAnimator, val -> SurvivalFlyClient.chunkAnimator = val);

        } else if (selectedCategory.equals("Graphics")) {
            addToggle(contentX, contentY, contentWidth, contentHeight, "Fullbright", SurvivalFlyClient.fullbright, val -> SurvivalFlyClient.fullbright = val);
            addToggle(contentX, contentY + spacing, contentWidth, contentHeight, "Totem Counter", SurvivalFlyClient.totemCounter, val -> SurvivalFlyClient.totemCounter = val);
            addToggle(contentX, contentY + spacing * 2, contentWidth, contentHeight, "Custom Sky", SurvivalFlyClient.customSky, val -> SurvivalFlyClient.customSky = val);
        }

        // Back Button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), b -> {
            if (this.client != null) {
                this.client.setScreen(this.parent);
            }
        }).dimensions(panelLeft + 225, panelTop + 185, 100, 20).build());
    }

    private void addToggle(int x, int y, int width, int height, String label, boolean currentState, java.util.function.Consumer<Boolean> action) {
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal(label + ": " + (currentState ? "§aON" : "§cOFF")), b -> {
                boolean newState = !currentState;
                action.accept(newState);
                b.setMessage(Text.literal(label + ": " + (newState ? "§aON" : "§cOFF")));
            }).dimensions(x, y, width, height).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        int panelLeft = (this.width - 340) / 2;
        int panelTop = (this.height - 220) / 2;

        // Rounded container box
        UIUtils.drawRoundedRect(context, panelLeft, panelTop, 340, 220, 10, SurvivalFlyClient.getBackgroundColor());

        // Rounded Header banner
        UIUtils.drawRoundedRect(context, panelLeft, panelTop, 340, 35, 10, SurvivalFlyClient.getHeaderColor());
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Crucified's Mod Hub"), panelLeft + 170, panelTop + 8, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Category: " + selectedCategory), panelLeft + 170, panelTop + 20, SurvivalFlyClient.getAccentColor());

        context.fill(panelLeft, panelTop + 35, panelLeft + 340, panelTop + 38, SurvivalFlyClient.getAccentColor());

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }
}
