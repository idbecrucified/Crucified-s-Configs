package com.example.survivalfly;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import com.example.survivalfly.screen.CrucifiedRootMenuScreen;
import org.lwjgl.glfw.GLFW;

public class SurvivalFlyClient implements ClientModInitializer {
    public static KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.survivalfly.open_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RSHIFT,
                "category.survivalfly.general"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                client.setScreen(new CrucifiedRootMenuScreen(null));
            }
        });

        HudRenderCallback.EVENT.register(HudRenderer::renderHud);
    }
}
