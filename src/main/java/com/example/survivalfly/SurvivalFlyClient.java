package com.example.survivalfly;

import com.example.survivalfly.renderer.HudRenderer;
import com.example.survivalfly.screen.CrucifiedRootMenuScreen;
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

        modHubKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.survivalfly.modhub",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.survivalfly.general"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (modHubKey.wasPressed()) {
                client.setScreen(new CrucifiedRootMenuScreen(null));
            }
        });
    }
}
