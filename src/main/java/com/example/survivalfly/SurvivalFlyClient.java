package com.example.survivalfly;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SurvivalFlyClient implements ClientModInitializer {
    public static KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        // Register Right Shift keybinding to open the menu
        openMenuKey = net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.survivalfly.open_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.survivalfly.hud"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Open menu on Right Shift press
            while (openMenuKey.wasPressed()) {
                client.setScreen(new LunarModMenuScreen(client.currentScreen));
            }

            if (client.player != null) {
                // Fullbright Logic
                if (CrucifiedsConfigs.fullbright) {
                    client.options.getGamma().setValue(12.0D);
                } else {
                    if (client.options.getGamma().getValue() > 1.0D) {
                        client.options.getGamma().setValue(0.5D);
                    }
                }

                // Toggle Sprint Logic
                if (CrucifiedsConfigs.toggleSprint && client.player.input.pressingForward) {
                    client.player.setSprinting(true);
                }
            }
        });
    }
}
