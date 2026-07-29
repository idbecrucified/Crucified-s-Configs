package com.example.survivalfly;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class LunarModMenuScreen extends Screen {
    private final Screen parent;
    private String currentTab = "PvP";

    public LunarModMenuScreen(Screen parent) {
        super(Text.literal("Lunar Style Menu"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int panelWidth = 320;
        int panelHeight = 220;
        int panelX = (this.width - panelWidth) / 2;
        int panelY = (this.height - panelHeight) / 2;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("PvP"), b -> { currentTab = "PvP"; this.clearAndInit(); })
            .dimensions(panelX + 10, panelY + 40, 70, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Performance"), b -> { currentTab = "Performance"; this.clearAndInit(); })
            .dimensions(panelX + 10, panelY + 65, 70, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Graphics"), b -> { currentTab = "Graphics"; this.clearAndInit(); })
            .dimensions(panelX + 10, panelY + 90, 70, 20).build());

        int contentX = panelX + 95;
        int contentY = panelY + 40;

        if (currentTab.equals("PvP")) {
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Toggle Sprint: " + (CrucifiedsConfigs.toggleSprint ? "§dON" : "§7OFF")),
                b -> { CrucifiedsConfigs.toggleSprint = !CrucifiedsConfigs.toggleSprint; b.setMessage(Text.literal("Toggle Sprint: " + (CrucifiedsConfigs.toggleSprint ? "§dON" : "§7OFF"))); })
                .dimensions(contentX, contentY, 210, 20).build());

            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Totem Counter: " + (CrucifiedsConfigs.totemCounter ? "§dON" : "§7OFF")),
                b -> { CrucifiedsConfigs.totemCounter = !CrucifiedsConfigs.totemCounter; b.setMessage(Text.literal("Totem Counter: " + (CrucifiedsConfigs.totemCounter ? "§dON" : "§7OFF"))); })
                .dimensions(contentX, contentY + 25, 210, 20).build());
        } else if (currentTab.equals("Performance")) {
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Entity Culling: " + (CrucifiedsConfigs.entityCulling ? "§dON" : "§7OFF")),
                b -> { CrucifiedsConfigs.entityCulling = !CrucifiedsConfigs.entityCulling; b.setMessage(Text.literal("Entity Culling: " + (CrucifiedsConfigs.entityCulling ? "§dON" : "§7OFF"))); })
                .dimensions(contentX, contentY, 210, 20).build());
        } else if (currentTab.equals("Graphics")) {
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Fullbright: " + (CrucifiedsConfigs.fullbright ? "§dON" : "§7OFF")),
                b -> { CrucifiedsConfigs.fullbright = !CrucifiedsConfigs.fullbright; b.setMessage(Text.literal("Fullbright: " + (CrucifiedsConfigs.fullbright ? "§dON" : "§7OFF"))); })
                .dimensions(contentX, contentY, 210, 20).build());
        }

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Done"), b -> this.client.setScreen(this.parent))
            .dimensions(panelX + panelWidth - 80, panelY + panelHeight - 30, 70, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        int panelWidth = 320;
        int panelHeight = 220;
        int panelX = (this.width - panelWidth) / 2;
        int panelY = (this.height - panelHeight) / 2;

        context.fill(panelX, panelY, panelX + panelWidth, panelY + panelHeight, 0xEE0F0F17);
        context.fill(panelX, panelY, panelX + panelWidth, panelY + 25, 0xFF7C3AED);
        context.fill(panelX, panelY + 25, panelX + panelWidth, panelY + 27, 0xFFEC4899);

        context.drawTextWithShadow(this.textRenderer, "§lCrucified's Mod Hub", panelX + 12, panelY + 8, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, "Category: §d" + currentTab, panelX + 95, panelY + 18, 0xDDDDDD);

        super.render(context, mouseX, mouseY, delta);
    }
}
