package com.example.survivalfly;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class LunarModMenuScreen extends Screen {
    public final Screen parent;
    private String currentCategory = "PvP";

    public LunarModMenuScreen(Screen parent) {
        super(Text.literal("Crucified's Mod Hub"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int panelWidth = 440;
        int panelHeight = 240;
        int left = (this.width - panelWidth) / 2;
        int top = (this.height - panelHeight) / 2;

        // Category Sidebar Buttons (Left)
        int sidebarX = left + 12;
        int sidebarY = top + 55;
        
        addCategoryButton(sidebarX, sidebarY, "PvP");
        addCategoryButton(sidebarX, sidebarY + 25, "Performance");
        addCategoryButton(sidebarX, sidebarY + 50, "Graphics");

        // Back / Done Button at bottom left of sidebar
        this.addDrawableChild(ButtonWidget.builder(Text.literal("§dBack / Done"), button -> {
            this.client.setScreen(parent);
        }).dimensions(sidebarX, top + panelHeight - 30, 100, 20).build());

        // Mod Cards Grid (Right side)
        int gridStartX = left + 125;
        int gridStartY = top + 55;
        int cardWidth = 95;
        int cardHeight = 75;
        int spacingX = 8;
        int spacingY = 8;

        List<ModItem> mods = getModsForCategory(currentCategory);
        int index = 0;
        for (ModItem mod : mods) {
            int col = index % 3;
            int row = index / 3;
            int cardX = gridStartX + col * (cardWidth + spacingX);
            int cardY = gridStartY + row * (cardHeight + spacingY);

            // Toggle button for each mod card (refreshes screen safely on click)
            this.addDrawableChild(ButtonWidget.builder(
                    Text.literal(mod.getState() ? "§dENABLED" : "§7DISABLED"),
                    button -> {
                        mod.toggle();
                        this.client.setScreen(new LunarModMenuScreen(this.parent));
                    }
            ).dimensions(cardX, cardY + cardHeight - 22, cardWidth, 20).build());

            index++;
        }
    }

    private void addCategoryButton(int x, int y, String category) {
        boolean selected = this.currentCategory.equals(category);
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal((selected ? "§d> " : "§7") + category),
                button -> {
                    this.currentCategory = category;
                    this.client.setScreen(new LunarModMenuScreen(this.parent));
                }
        ).dimensions(x, y, 100, 20).build());
    }

    private List<ModItem> getModsForCategory(String category) {
        List<ModItem> list = new ArrayList<>();
        if (category.equals("PvP")) {
            list.add(new ModItem("Toggle Sprint", () -> CrucifiedsConfigs.toggleSprint, val -> CrucifiedsConfigs.toggleSprint = val));
            list.add(new ModItem("Totem Counter", () -> CrucifiedsConfigs.totemCounter, val -> CrucifiedsConfigs.totemCounter = val));
            list.add(new ModItem("Armor Status", () -> CrucifiedsConfigs.armorStatus, val -> CrucifiedsConfigs.armorStatus = val));
            list.add(new ModItem("CPS Display", () -> CrucifiedsConfigs.cpsDisplay, val -> CrucifiedsConfigs.cpsDisplay = val));
            list.add(new ModItem("Keystrokes", () -> CrucifiedsConfigs.keystrokes, val -> CrucifiedsConfigs.keystrokes = val));
            list.add(new ModItem("FPS Counter", () -> CrucifiedsConfigs.fpsCounter, val -> CrucifiedsConfigs.fpsCounter = val));
        } else if (category.equals("Performance")) {
            list.add(new ModItem("Entity Culling", () -> CrucifiedsConfigs.entityCulling, val -> CrucifiedsConfigs.entityCulling = val));
            list.add(new ModItem("FPS Booster", () -> CrucifiedsConfigs.fpsBooster, val -> CrucifiedsConfigs.fpsBooster = val));
            list.add(new ModItem("Chunk Animator", () -> CrucifiedsConfigs.chunkAnimator, val -> CrucifiedsConfigs.chunkAnimator = val));
            list.add(new ModItem("Particle Mult.", () -> CrucifiedsConfigs.particleMultiplier, val -> CrucifiedsConfigs.particleMultiplier = val));
        } else if (category.equals("Graphics")) {
            list.add(new ModItem("Fullbright", () -> CrucifiedsConfigs.fullbright, val -> CrucifiedsConfigs.fullbright = val));
            list.add(new ModItem("Dynamic Light", () -> CrucifiedsConfigs.dynamicLighting, val -> CrucifiedsConfigs.dynamicLighting = val));
            list.add(new ModItem("Minimal HUD", () -> CrucifiedsConfigs.minimalHud, val -> CrucifiedsConfigs.minimalHud = val));
            list.add(new ModItem("Weather Change", () -> CrucifiedsConfigs.weatherChanger, val -> CrucifiedsConfigs.weatherChanger = val));
        }
        return list;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        int panelWidth = 440;
        int panelHeight = 240;
        int left = (this.width - panelWidth) / 2;
        int top = (this.height - panelHeight) / 2;

        // Dark Window Background & Border
        context.fill(left, top, left + panelWidth, top + panelHeight, 0xEE1A111E);
        context.drawBorder(left, top, panelWidth, panelHeight, 0xFF9370DB);

        // Top Header Bar
        context.fill(left, top, left + panelWidth, top + 35, 0xFF4B0082);
        context.drawTextWithShadow(this.textRenderer, Text.literal("§d§lCRUCIFIED'S MOD HUB"), left + 15, top + 12, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, Text.literal("Category: §f" + currentCategory), left + 280, top + 12, 0xFFC0CB);

        // Render Mod Cards backgrounds & titles
        int gridStartX = left + 125;
        int gridStartY = top + 55;
        int cardWidth = 95;
        int cardHeight = 75;
        int spacingX = 8;
        int spacingY = 8;

        List<ModItem> mods = getModsForCategory(currentCategory);
        int index = 0;
        for (ModItem mod : mods) {
            int col = index % 3;
            int row = index / 3;
            int cardX = gridStartX + col * (cardWidth + spacingX);
            int cardY = gridStartY + row * (cardHeight + spacingY);

            context.fill(cardX, cardY, cardX + cardWidth, cardY + cardHeight, 0xFF2D1B36);
            context.drawBorder(cardX, cardY, cardWidth, cardHeight, mod.getState() ? 0xFFDA70D6 : 0xFF4A3b5C);
            context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(mod.name), cardX + (cardWidth / 2), cardY + 18, 0xFFFFFF);

            index++;
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    private static class ModItem {
        private final String name;
        private final java.util.function.Supplier<Boolean> getter;
        private final java.util.function.Consumer<Boolean> setter;

        public ModItem(String name, java.util.function.Supplier<Boolean> getter, java.util.function.Consumer<Boolean> setter) {
            this.name = name;
            this.getter = getter;
            this.setter = setter;
        }

        public boolean getState() {
            return getter.get();
        }

        public void toggle() {
            setter.accept(!getter.get());
        }
    }
}
