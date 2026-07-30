package com.example.survivalfly.screen;

import com.example.survivalfly.CrucifiedTheme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CrucifiedModsScreen extends Screen {
    private final Screen parent;
    private static String currentCategory = "PvP";

    // Mod states
    private static boolean toggleSprint = true;
    private static boolean cpsDisplay = true;
    private static boolean keystrokes = true;
    private static boolean armorStatus = true;
    private static boolean hitColor = true;
    
    private static boolean fpsDisplay = true;
    private static boolean fullbright = true;

    private static boolean zoom = true;
    private static boolean customSky = true;

    public CrucifiedModsScreen(Screen parent) {
        super(Text.literal("Crucified's Mod Hub"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        
        int panelWidth = 320;
        int panelHeight = 200;
        int panelX = centerX - panelWidth / 2;
        int panelY = centerY - panelHeight / 2 + 10;

        // Category selection buttons on the left
        String[] categories = {"PvP", "Performance", "Graphics"};
        int catX = panelX + 15;
        int catStartY = panelY + 48;
        for (int i = 0; i < categories.length; i++) {
            String cat = categories[i];
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal(cat),
                button -> {
                    currentCategory = cat;
                    MinecraftClient.getInstance().setScreen(new CrucifiedModsScreen(parent));
                }
            ).dimensions(catX, catStartY + (i * 28), 90, 20).build());
        }

        // Mod toggle buttons on the right depending on the active category
        int modX = panelX + 115;
        int modStartY = panelY + 48;

        if (currentCategory.equals("PvP")) {
            addModToggle(modX, modStartY, 0, "Toggle Sprint", toggleSprint, val -> toggleSprint = val);
            addModToggle(modX, modStartY, 1, "CPS Display", cpsDisplay, val -> cpsDisplay = val);
            addModToggle(modX, modStartY, 2, "Keystrokes", keystrokes, val -> keystrokes = val);
            addModToggle(modX, modStartY, 3, "Armor Status", armorStatus, val -> armorStatus = val);
            addModToggle(modX, modStartY, 4, "Hit Color", hitColor, val -> hitColor = val);
        } else if (currentCategory.equals("Performance")) {
            addModToggle(modX, modStartY, 0, "FPS Display", fpsDisplay, val -> fpsDisplay = val);
            addModToggle(modX, modStartY, 1, "Fullbright", fullbright, val -> fullbright = val);
        } else if (currentCategory.equals("Graphics")) {
            addModToggle(modX, modStartY, 0, "Zoom", zoom, val -> zoom = val);
            addModToggle(modX, modStartY, 1, "Custom Sky", customSky, val -> customSky = val);
        }

        // Back button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Back"),
            button -> MinecraftClient.getInstance().setScreen(parent)
        ).dimensions(panelX + panelWidth - 110, panelY + panelHeight - 32, 95, 20).build());
    }

    private void addModToggle(int x, int startY, int index, String modName, boolean currentState, java.util.function.Consumer<Boolean> onToggle) {
        String statusText = modName + ": " + (currentState ? "§aON" : "§cOFF");
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal(statusText),
            button -> {
                onToggle.accept(!currentState);
                MinecraftClient.getInstance().setScreen(new CrucifiedModsScreen(parent));
            }
        ).dimensions(x, startY + (index * 26), 190, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Gradient background
        context.fillGradient(0, 0, this.width, this.height, CrucifiedTheme.getGradientStart(), CrucifiedTheme.getGradientEnd());

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelWidth = 320;
        int panelHeight = 200;
        int panelX = centerX - panelWidth / 2;
        int panelY = centerY - panelHeight / 2 + 10;

        // Main dark container box
        context.fill(panelX, panelY, panelX + panelWidth, panelY + panelHeight, 0xEE1A1A22);

        // Header Banner background and bottom accent line
        int headerHeight = 35;
        context.fill(panelX + 10, panelY + 8, panelX + panelWidth - 10, panelY + headerHeight, CrucifiedTheme.getSecondaryColor());
        context.fill(panelX + 10, panelY + headerHeight, panelX + panelWidth - 10, panelY + headerHeight + 3, CrucifiedTheme.getPrimaryColor());

        // Header text elements
        context.drawCenteredTextWithShadow(this.textRenderer, "Crucified's Mod Hub", centerX, panelY + 12, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, "Category: " + currentCategory, centerX, panelY + 24, CrucifiedTheme.getPrimaryColor());

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
