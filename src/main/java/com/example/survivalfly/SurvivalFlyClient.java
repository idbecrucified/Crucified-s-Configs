package com.example.survivalfly;

import com.example.survivalfly.screen.CrucifiedHudEditScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SurvivalFlyClient implements ClientModInitializer {
    private static KeyBinding editScreenKey;
    private static int cps = 0;

    // Expanded Mod Toggle States
    public static boolean toggleSprint = true;
    public static boolean cpsDisplay = true;
    public static boolean keystrokes = true;
    public static boolean armorStatus = true;
    public static boolean hitColor = true;

    public static boolean fpsCounter = true;
    public static boolean fastRender = false;
    public static boolean zoomToggle = true;
    public static boolean chunkAnimator = true;

    public static boolean fullbright = true;
    public static boolean totemCounter = true;
    public static boolean customSky = false;

    // Theme Configuration ("Gamer", "Sea", "Sun", "OLED", "Flash", "Natural", "Rock")
    public static String currentTheme = "Gamer";

    public static int getHeaderColor() {
        return switch (currentTheme) {
            case "Sea" -> 0xFF0077BE;
            case "Sun" -> 0xFFFF8C00;
            case "OLED" -> 0xFF111111;
            case "Flash" -> 0xFFFFFFFF;
            case "Natural" -> 0xFF2E8B57;
            case "Rock" -> 0xFF708090;
            default -> 0xFF8A49F5; // Gamer (Default)
        };
    }

    public static int getAccentColor() {
        return switch (currentTheme) {
            case "Sea" -> 0xFF00FFFF;
            case "Sun" -> 0xFFFFD700;
            case "OLED" -> 0xFF888888;
            case "Flash" -> 0xFFCCCCCC;
            case "Natural" -> 0xFF98FB98;
            case "Rock" -> 0xFFC0C0C0;
            default -> 0xFFFF55FF; // Gamer
        };
    }

    public static int getBackgroundColor() {
        return switch (currentTheme) {
            case "Sea" -> 0xEE0A1118;
            case "Sun" -> 0xEE1A1408;
            case "OLED" -> 0xFF000000;
            case "Flash" -> 0xFF000000;
            case "Natural" -> 0xEE0F1C12;
            case "Rock" -> 0xEE1C1E21;
            default -> 0xEE1A1A24; // Gamer
        };
    }

    @Override
    public void onInitializeClient() {
        editScreenKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.survivalfly.editscreen",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.survivalfly"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (editScreenKey.wasPressed()) {
                client.setScreen(new CrucifiedHudEditScreen());
            }
        });

        HudRenderCallback.EVENT.register(new HudRenderer());
    }

    public static int getCps() {
        return cps;
    }
}
