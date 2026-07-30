package com.example.survivalfly;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.lwjgl.glfw.GLFW;

public class SurvivalFlyClient implements ClientModInitializer {
    private static boolean rightShiftWasPressed = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client == null || client.getWindow() == null) {
                return;
            }

            long windowHandle = client.getWindow().getHandle();
            boolean isPressed = GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;

            // Trigger only on the initial press down to prevent screen-opening loops
            if (isPressed && !rightShiftWasPressed) {
                if (client.currentScreen == null && client.player != null) {
                    client.setScreen(new HudEditorScreen());
                }
            }

            rightShiftWasPressed = isPressed;
        });
    }
}
