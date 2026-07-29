package com.example.survivalfly;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.lwjgl.glfw.GLFW;

public class SurvivalFlyClient implements ClientModInitializer {
    public static KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        openMenuKey = net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.survivalfly.open_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.survivalfly.hud"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                client.setScreen(new LunarModMenuScreen(client.currentScreen));
            }

            if (client.player != null) {
                // Fullbright Logic (Using silent Night Vision effect to bypass 1.20.1 gamma limits)
                if (CrucifiedsConfigs.fullbright) {
                    client.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 0, false, false));
                }

                // Toggle Sprint Logic
                if (CrucifiedsConfigs.toggleSprint && client.player.input.pressingForward) {
                    client.player.setSprinting(true);
                }
            }
        });
    }
}
