package com.example.survivalfly;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SurvivalFlyClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("survivalfly");
    private static KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        // Register Right Shift keybinding
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.survivalfly.open_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.survivalfly.general"
        ));

        // Listen for key presses and log them
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                LOGGER.info("Right Shift was pressed! Attempting to open menu...");
                client.setScreen(new LunarModMenuScreen(client.currentScreen));
            }
        });
    }
}
