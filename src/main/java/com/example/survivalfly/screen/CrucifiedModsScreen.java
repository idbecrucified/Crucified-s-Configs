package com.example.survivalfly.screen;

import com.example.survivalfly.CrucifiedTheme;
import com.example.survivalfly.ThemedButtonWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class CrucifiedModsScreen extends Screen {
    private final Screen parent;
    private static String currentCategory = "PvP";

    private static boolean toggleSprint = true;
    private static boolean cpsDisplay = true;
    private static boolean keystrokes = true;
    private static boolean armorStatus = true;
    
    private static boolean zoom = true;
    private static boolean fpsDisplay = true;
    private static boolean fullbright = true;

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
        int panelY = centerY - panelHeight / 2;

        String[] categories = {"PvP", "Graphics"};
        int catX = panelX + 15;
        int catStartY = panelY + 50;
        for (int i = 0; i < categories.length; i++) {
            String cat = categories[i];
            this.addDrawableChild(new ThemedButtonWidget(
                catX, catStartY + (i * 28), 90, 20,
                Text.literal(cat),
                button -> {
                    currentCategory = cat;
                    MinecraftClient.getInstance().setScreen(new CrucifiedModsScreen(parent));
                }
            ));
        }

        int modX = panelX + 115;
        int modStartY = panelY + 50;

        if (currentCategory.equals("PvP")) {
            addModToggle(modX, modStartY, 0, "Toggle Sprint", toggleSprint, val -> toggleSprint = val);
            addModToggle(modX, modStartY, 1, "CPS Display", cpsDisplay, val -> cpsDisplay = val);
            addModToggle(modX, modStartY, 2, "Keystrokes", keystrokes, val -> keystrokes = val);
            addModToggle(modX, modStartY, 3, "Armor Status", armorStatus, val -> armorStatus = val);
        } else if (currentCategory.equals("Graphics")) {
            String zoomText = "Zoom: " + (zoom ? "§aON" : "§cOFF");
            this.addDrawableChild(new ThemedButtonWidget(
                modX, modStartY, 160, 20,
                Text.literal(zoomText),
                button -> {
                    zoom = !zoom;
                    MinecraftClient.getInstance().setScreen(new CrucifiedModsScreen(parent));
                }
            ));

            this.addDrawableChild(new ThemedButtonWidget(
                modX + 164, modStartY, 26, 20,
                Text.literal("⚙"),
                button -> MinecraftClient.getInstance().setScreen(new ZoomSettingsScreen(this))
            ));

            addModToggle(modX, modStartY, 1, "FPS Display", fpsDisplay, val -> fpsDisplay = val);
            
            String fbText = "Fullbright: " + (fullbright ? "§aON" : "§cOFF");
            this.addDrawableChild(new ThemedButtonWidget(
                modX, modStartY + (2 * 26), 190, 20,
                Text.literal(fbText),
                button -> {
                    fullbright = !fullbright;
                    MinecraftClient.getInstance().setScreen(new CrucifiedModsScreen(parent));
                }
            ));
        }

        this.addDrawableChild(new ThemedButtonWidget(
            panelX + panelWidth - 110, panelY + panelHeight - 30, 95, 20,
            Text.literal("Back"),
            button -> MinecraftClient.getInstance().setScreen(parent)
        ));
    }

    private void addModToggle(int x, int startY, int index, String modName, boolean currentState, java.util.function.Consumer<Boolean> onToggle) {
        String statusText = modName + ": " + (currentState ? "§aON" : "§cOFF");
        this.addDrawableChild(new ThemedButtonWidget(
            x, startY + (index * 26), 190, 20,
            Text.literal(statusText),
            button -> {
                onToggle.accept(!currentState);
                MinecraftClient.getInstance().setScreen(new CrucifiedModsScreen(parent));
            }
        ));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelWidth = 320;
        int panelHeight = 200;
        int panelX = centerX - panelWidth / 2;
        int panelY = centerY - panelHeight / 2;

        context.fill(panelX - 1, panelY - 1, panelX + panelWidth + 1, panelY + panelHeight + 1, 0xFF000000);
        context.fillGradient(panelX, panelY, panelX + panelWidth, panelY + panelHeight, CrucifiedTheme.getPrimaryColor(), CrucifiedTheme.getSecondaryColor());
        context.fill(panelX + 1, panelY + 1, panelX + panelWidth - 1, panelY + 2, 0x55FFFFFF);
        context.fill(panelX + 110, panelY + 45, panelX + 111, panelY + panelHeight - 15, 0x44FFFFFF);
        
        CrucifiedTheme.renderThemeDecorations(context, panelX, panelY, panelWidth, panelHeight);

        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Crucified's Mod Hub"), centerX, panelY + 12, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Category: " + currentCategory), centerX, panelY + 26, 0xDDDDDD);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() { return false; }

    public static boolean isZoomEnabled() { return zoom; }
    public static boolean isFpsEnabled() { return fpsDisplay; }
    public static boolean isFullbrightEnabled() { return fullbright; }
    public static boolean isKeystrokesEnabled() { return keystrokes; }

    public static class ZoomSettingsScreen extends Screen {
        private final Screen parent;

        public ZoomSettingsScreen(Screen parent) {
            super(Text.literal("Zoom Settings"));
            this.parent = parent;
        }

        @Override
        protected void init() {
            int centerX = this.width / 2;
            int centerY = this.height / 2;

            this.addDrawableChild(new ThemedButtonWidget(
                centerX - 100, centerY - 30, 200, 20,
                Text.literal("Zoom Intensity: " + CrucifiedModsScreen.zoomIntensity + "x"),
                button -> {
                    if (CrucifiedModsScreen.zoomIntensity == 2.0f) CrucifiedModsScreen.zoomIntensity = 3.0f;
                    else if (CrucifiedModsScreen.zoomIntensity == 3.0f) CrucifiedModsScreen.zoomIntensity = 4.0f;
                    else if (CrucifiedModsScreen.zoomIntensity == 4.0f) CrucifiedModsScreen.zoomIntensity = 6.0f;
                    else if (CrucifiedModsScreen.zoomIntensity == 6.0f) CrucifiedModsScreen.zoomIntensity = 8.0f;
                    else CrucifiedModsScreen.zoomIntensity = 2.0f;
                    MinecraftClient.getInstance().setScreen(new ZoomSettingsScreen(parent));
                }
            ));

            this.addDrawableChild(new ThemedButtonWidget(
                centerX - 100, centerY, 200, 20,
                Text.literal("Zoom Key: " + CrucifiedModsScreen.zoomKey),
                button -> {
                    if (CrucifiedModsScreen.zoomKey.equals("C")) CrucifiedModsScreen.zoomKey = "Z";
                    else if (CrucifiedModsScreen.zoomKey.equals("Z")) CrucifiedModsScreen.zoomKey = "V";
                    else if (CrucifiedModsScreen.zoomKey.equals("V")) CrucifiedModsScreen.zoomKey = "LEFT_ALT";
                    else CrucifiedModsScreen.zoomKey = "C";
                    MinecraftClient.getInstance().setScreen(new ZoomSettingsScreen(parent));
                }
            ));

            this.addDrawableChild(new ThemedButtonWidget(
                centerX - 100, centerY + 40, 200, 20,
                Text.literal("Done"),
                button -> MinecraftClient.getInstance().setScreen(parent)
            ));
        }

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, float delta) {
            this.renderBackground(context);

            int centerX = this.width / 2;
            int centerY = this.height / 2;
            int panelWidth = 260;
            int panelHeight = 160;
            int panelX = centerX - panelWidth / 2;
            int panelY = centerY - panelHeight / 2;

            context.fill(panelX - 1, panelY - 1, panelX + panelWidth + 1, panelY + panelHeight + 1, 0xFF000000);
            context.fillGradient(panelX, panelY, panelX + panelWidth, panelY + panelHeight, CrucifiedTheme.getPrimaryColor(), CrucifiedTheme.getSecondaryColor());
            context.fill(panelX + 1, panelY + 1, panelX + panelWidth - 1, panelY + 2, 0x55FFFFFF);
            
            CrucifiedTheme.renderThemeDecorations(context, panelX, panelY, panelWidth, panelHeight);
            
            context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Zoom Configuration"), centerX, panelY + 14, 0xFFFFFF);
            super.render(context, mouseX, mouseY, delta);
        }

        @Override
        public boolean shouldPause() { return false; }
    }
}
