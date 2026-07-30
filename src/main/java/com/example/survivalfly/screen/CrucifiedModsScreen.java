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
    
    private static boolean zoom = true;
    private static boolean fpsDisplay = true;
    private static boolean fullbright = true;

    // Zoom settings
    public static float zoomIntensity = 4.0f;
    public static String zoomKey = "C";

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
        String[] categories = {"PvP", "Graphics"};
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
        } else if (currentCategory.equals("Graphics")) {
            // Zoom toggle button with gear configuration icon
            String zoomText = "Zoom: " + (zoom ? "§aON" : "§cOFF");
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal(zoomText),
                button -> {
                    zoom = !zoom;
                    MinecraftClient.getInstance().setScreen(new CrucifiedModsScreen(parent));
                }
            ).dimensions(modX, modStartY, 160, 20).build());

            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("⚙"),
                button -> MinecraftClient.getInstance().setScreen(new ZoomSettingsScreen(this))
            ).dimensions(modX + 164, modStartY, 26, 20).build());

            addModToggle(modX, modStartY, 1, "FPS Display", fpsDisplay, val -> fpsDisplay = val);
            
            // Fullbright toggle
            String fbText = "Fullbright: " + (fullbright ? "§aON" : "§cOFF");
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal(fbText),
                button -> {
                    fullbright = !fullbright;
                    MinecraftClient.getInstance().setScreen(new CrucifiedModsScreen(parent));
                }
            ).dimensions(modX, modStartY + (2 * 26), 190, 20).build());
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
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelWidth = 320;
        int panelHeight = 200;
        int panelX = centerX - panelWidth / 2;
        int panelY = centerY - panelHeight / 2 + 10;

        // Rectangle background around menu container
        context.fill(panelX, panelY, panelX + panelWidth, panelY + panelHeight, 0xEE1A1A22);

        // Header Banner background as a gradient between the 2 colors in the theme
        int headerHeight = 35;
        context.fillGradient(panelX + 10, panelY + 8, panelX + panelWidth - 10, panelY + headerHeight, CrucifiedTheme.getGradientStart(), CrucifiedTheme.getGradientEnd());
        context.fill(panelX + 10, panelY + headerHeight, panelX + panelWidth - 10, panelY + headerHeight + 3, CrucifiedTheme.getPrimaryColor());

        // Header text elements using theme primary color
        context.drawCenteredTextWithShadow(this.textRenderer, "Crucified's Mod Hub", centerX, panelY + 12, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, "Category: " + currentCategory, centerX, panelY + 24, CrucifiedTheme.getPrimaryColor());

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    public static boolean isZoomEnabled() { return zoom; }
    public static boolean isFpsEnabled() { return fpsDisplay; }
    public static boolean isFullbrightEnabled() { return fullbright; }
    public static boolean isKeystrokesEnabled() { return keystrokes; }
}

// Sub-screen for configuring Zoom Intensity and Keybind with gradient panel background
class ZoomSettingsScreen extends Screen {
    private final Screen parent;

    protected ZoomSettingsScreen(Screen parent) {
        super(Text.literal("Zoom Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Zoom Intensity: " + CrucifiedModsScreen.zoomIntensity + "x"),
            button -> {
                if (CrucifiedModsScreen.zoomIntensity == 2.0f) CrucifiedModsScreen.zoomIntensity = 3.0f;
                else if (CrucifiedModsScreen.zoomIntensity == 3.0f) CrucifiedModsScreen.zoomIntensity = 4.0f;
                else if (CrucifiedModsScreen.zoomIntensity == 4.0f) CrucifiedModsScreen.zoomIntensity = 6.0f;
                else if (CrucifiedModsScreen.zoomIntensity == 6.0f) CrucifiedModsScreen.zoomIntensity = 8.0f;
                else CrucifiedModsScreen.zoomIntensity = 2.0f;
                MinecraftClient.getInstance().setScreen(new ZoomSettingsScreen(parent));
            }
        ).dimensions(centerX - 100, centerY - 30, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Zoom Key: " + CrucifiedModsScreen.zoomKey),
            button -> {
                if (CrucifiedModsScreen.zoomKey.equals("C")) CrucifiedModsScreen.zoomKey = "Z";
                else if (CrucifiedModsScreen.zoomKey.equals("Z")) CrucifiedModsScreen.zoomKey = "V";
                else if (CrucifiedModsScreen.zoomKey.equals("V")) CrucifiedModsScreen.zoomKey = "LEFT_ALT";
                else CrucifiedModsScreen.zoomKey = "C";
                MinecraftClient.getInstance().setScreen(new ZoomSettingsScreen(parent));
            }
        ).dimensions(centerX - 100, centerY, 200, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Done"),
            button -> MinecraftClient.getInstance().setScreen(parent)
        ).dimensions(centerX - 100, centerY + 40, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelWidth = 260;
        int panelHeight = 160;
        int panelX = centerX - panelWidth / 2;
        int panelY = centerY - panelHeight / 2;

        // Square panel with gradient of the 2 theme colors around the config
        context.fill(panelX, panelY, panelX + panelWidth, panelY + panelHeight, 0xEE1A1A22);
        context.fillGradient(panelX, panelY, panelX + panelWidth, panelY + 30, CrucifiedTheme.getGradientStart(), CrucifiedTheme.getGradientEnd());
        
        context.drawCenteredTextWithShadow(this.textRenderer, "Zoom Configuration", centerX, panelY + 10, CrucifiedTheme.getPrimaryColor());
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
