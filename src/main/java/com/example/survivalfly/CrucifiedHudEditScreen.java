package com.example.survivalfly.screen;

import com.example.survivalfly.SurvivalFlyClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CrucifiedHudEditScreen extends Screen {
    private final Screen parent;
    private static String selectedCategory = "PvP";

    public CrucifiedHudEditScreen() {
        this(null);
    }

    public CrucifiedHudEditScreen(Screen parent) {
        super(Text.literal("Crucified's Mod Hub"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        int panelLeft = (this.width - 320) / 2;
        int panelTop = (this.height - 200) / 2;

        // Category Buttons (Left Sidebar)
        int catX = panelLeft + 15;
        int catY = panelTop + 50;
        int catWidth = 90;
        int catHeight = 20;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("PvP"), b -> {
            selectedCategory = "PvP";
            this.clearAndInit();
        }).dimensions(catX, catY, catWidth, catHeight).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Performance"), b -> {
            selectedCategory = "Performance";
            this.clearAndInit();
        }).dimensions(catX, catY + 25, catWidth, catHeight).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Graphics"), b -> {
            selectedCategory = "Graphics";
            this.clearAndInit();
        }).dimensions(catX, catY + 50, catWidth, catHeight).build());

        // Right Content Area Toggles based on selected category
        int contentX = panelLeft + 120;
        int contentY = panelTop + 50;
        int contentWidth = 185;
        int contentHeight = 20;

        if (selectedCategory.equals("PvP")) {
            this.addDrawableChild(ButtonWidget.builder(Text.literal("Toggle Sprint: " + (SurvivalFlyClient.toggleSprint ? "ON" : "OFF")), b -> {
                SurvivalFlyClient.toggleSprint = !SurvivalFlyClient.toggleSprint;
                b.setMessage(Text.literal("Toggle Sprint: " + (SurvivalFlyClient.toggleSprint ? "ON" : "OFF")));
            }).dimensions(contentX, contentY, contentWidth, contentHeight).build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("CPS Display: " + (SurvivalFlyClient.cpsDisplay ? "ON" : "OFF")), b -> {
                SurvivalFlyClient.cpsDisplay = !SurvivalFlyClient.cpsDisplay;
                b.setMessage(Text.literal("CPS Display: " + (SurvivalFlyClient.cpsDisplay ? "ON" : "OFF")));
            }).dimensions(contentX, contentY + 25, contentWidth, contentHeight).build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("Keystrokes: " + (SurvivalFlyClient.keystrokes ? "ON" : "OFF")), b -> {
                SurvivalFlyClient.keystrokes = !SurvivalFlyClient.keystrokes;
                b.setMessage(Text.literal("Keystrokes: " + (SurvivalFlyClient.keystrokes ? "ON" : "OFF")));
            }).dimensions(contentX, contentY + 50, contentWidth, contentHeight).build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("Armor Status: " + (SurvivalFlyClient.armorStatus ? "ON" : "OFF")), b -> {
                SurvivalFlyClient.armorStatus = !SurvivalFlyClient.armorStatus;
                b.setMessage(Text.literal("Armor Status: " + (SurvivalFlyClient.armorStatus ? "ON" : "OFF")));
            }).dimensions(contentX, contentY + 75, contentWidth, contentHeight).build());

        } else if (selectedCategory.equals("Performance")) {
            this.addDrawableChild(ButtonWidget.builder(Text.literal("FPS Counter: " + (SurvivalFlyClient.fpsCounter ? "ON" : "OFF")), b -> {
                SurvivalFlyClient.fpsCounter = !SurvivalFlyClient.fpsCounter;
                b.setMessage(Text.literal("FPS Counter: " + (SurvivalFlyClient.fpsCounter ? "ON" : "OFF")));
            }).dimensions(contentX, contentY, contentWidth, contentHeight).build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("Fast Render: " + (SurvivalFlyClient.fastRender ? "ON" : "OFF")), b -> {
                SurvivalFlyClient.fastRender = !SurvivalFlyClient.fastRender;
                b.setMessage(Text.literal("Fast Render: " + (SurvivalFlyClient.fastRender ? "ON" : "OFF")));
            }).dimensions(contentX, contentY + 25, contentWidth, contentHeight).build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("Zoom Toggle: " + (SurvivalFlyClient.zoomToggle ? "ON" : "OFF")), b -> {
                SurvivalFlyClient.zoomToggle = !SurvivalFlyClient.zoomToggle;
                b.setMessage(Text.literal("Zoom Toggle: " + (SurvivalFlyClient.zoomToggle ? "ON" : "OFF")));
            }).dimensions(contentX, contentY + 50, contentWidth, contentHeight).build());

        } else if (selectedCategory.equals("Graphics")) {
            this.addDrawableChild(ButtonWidget.builder(Text.literal("Fullbright: " + (SurvivalFlyClient.fullbright ? "ON" : "OFF")), b -> {
                SurvivalFlyClient.fullbright = !SurvivalFlyClient.fullbright;
                b.setMessage(Text.literal("Fullbright: " + (SurvivalFlyClient.fullbright ? "ON" : "OFF")));
            }).dimensions(contentX, contentY, contentWidth, contentHeight).build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("Totem Counter: " + (SurvivalFlyClient.totemCounter ? "ON" : "OFF")), b -> {
                SurvivalFlyClient.totemCounter = !SurvivalFlyClient.totemCounter;
                b.setMessage(Text.literal("Totem Counter: " + (SurvivalFlyClient.totemCounter ? "ON" : "OFF")));
            }).dimensions(contentX, contentY + 25, contentWidth, contentHeight).build());
        }

        // Done Button at bottom right
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> {
            if (this.client != null) {
                this.client.setScreen(this.parent);
            }
        }).dimensions(panelLeft + 205, panelTop + 165, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        int panelLeft = (this.width - 320) / 2;
        int panelTop = (this.height - 200) / 2;

        // Dark background container box matching your menu style
        context.fill(panelLeft, panelTop, panelLeft + 320, panelTop + 200, 0xEE1A1A24);

        // Top Header banner styling
        context.fill(panelLeft, panelTop, panelLeft + 320, panelTop + 35, 0xFF8A49F5);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Crucified's Mod Hub"), panelLeft + 160, panelTop + 8, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Category: " + selectedCategory), panelLeft + 160, panelTop + 20, 0xFF55FF);

        // Pink accent divider line
        context.fill(panelLeft, panelTop + 35, panelLeft + 320, panelTop + 38, 0xFFFF55FF);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }
}
