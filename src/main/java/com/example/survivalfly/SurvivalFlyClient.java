package com.example.survivalfly;

import com.example.survivalfly.renderer.HudRenderer;
import com.example.survivalfly.screen.CrucifiedModsScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SurvivalFlyClient implements ClientModInitializer {
    public static KeyBinding modHubKey;

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(HudRenderer::render);

        // Register Right Shift key binding to open the Mod Hub
        modHubKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.survivalfly.modhub",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.survivalfly.general"
        ));

        // Check if the key is pressed every tick
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (modHubKey.wasPressed()) {
                client.setScreen(new CrucifiedModsScreen(null));
            }
        });
    }
}
