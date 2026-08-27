package com.example.survivalfly;

import com.example.survivalfly.screen.CrucifiedMainMenuScreen;
import com.example.survivalfly.screen.CrucifiedModsScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SurvivalFlyClient implements ClientModInitializer {
    public static KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.survivalfly.open_menu",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.survivalfly.title"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Keybind listener -> Open Main Menu
            while (openMenuKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new CrucifiedMainMenuScreen(null));
                }
            }

            if (client.player != null) {
                // Toggle Sprint Logic
                if (CrucifiedModsScreen.isToggleSprintEnabled()) {
                    if (client.player.input.pressingForward && !client.player.isSneaking() && !client.player.horizontalCollision) {
                        client.player.setSprinting(true);
                    }
                }

                // Fullbright Logic
                if (CrucifiedModsScreen.isFullbrightEnabled()) {
                    client.options.getGamma().setValue(16.0);
                } else {
                    if (client.options.getGamma().getValue() > 1.0) {
                        client.options.getGamma().setValue(1.0);
                    }
                }

                // CPS Tracking Update
                HudRenderer.updateCpsTrackers();
            }
        });

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            HudRenderer.renderHud(drawContext);
        });
    }
}
