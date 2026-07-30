package com.example.survivalfly;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.lwjgl.glfw.GLFW;

public class SurvivalFlyClient implements ClientModInitializer {
    private static boolean rightShiftWasPressed = false;

    @Override
    public void onInitializeClient() {
        // Register the HUD renderer so elements actually appear on screen
        HudRenderCallback.EVENT.register(HudRenderer::renderHud);

        // Register Right Shift listener to open the HUD Editor
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client == null || client.getWindow() == null) {
                return;
            }

            long windowHandle = client.getWindow().getHandle();
            boolean isPressed = GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;

            if (isPressed && !rightShiftWasPressed) {
                if (client.currentScreen == null && client.player != null) {
                    client.setScreen(new HudEditorScreen());
                }
            }

            rightShiftWasPressed = isPressed;
        });
    }
}
