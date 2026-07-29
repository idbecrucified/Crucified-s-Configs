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

    @Override
    public void onInitializeClient() {
        // Register keybinding to open the HUD edit screen using Right Shift
        editScreenKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.survivalfly.editscreen",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.survivalfly"
        ));

        // Listen for client ticks to open the edit screen when Right Shift is pressed
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (editScreenKey.wasPressed()) {
                client.setScreen(new CrucifiedHudEditScreen());
            }
        });

        // Register the HUD renderer so elements actually render on the screen
        HudRenderCallback.EVENT.register(new HudRenderer());
    }

    public static int getCps() {
        return cps;
    }
}
