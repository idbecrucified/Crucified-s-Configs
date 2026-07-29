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
