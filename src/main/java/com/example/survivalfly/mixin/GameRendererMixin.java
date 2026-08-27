package com.example.survivalfly.mixin;

import com.example.survivalfly.screen.CrucifiedModsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void applyCustomZoom(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
        if (CrucifiedModsScreen.isZoomEnabled()) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.getWindow() != null) {
                int keyCode = getGlfwKeyCode(CrucifiedModsScreen.zoomKey);
                // Zooms in dynamically when configured key is actively held down
                if (InputUtil.isKeyPressed(client.getWindow().getHandle(), keyCode)) {
                    double baseFov = cir.getReturnValue();
                    cir.setReturnValue(baseFov / CrucifiedModsScreen.zoomIntensity);
                }
            }
        }
    }

    private int getGlfwKeyCode(String keyName) {
        switch (keyName.toUpperCase()) {
            case "Z": return GLFW.GLFW_KEY_Z;
            case "V": return GLFW.GLFW_KEY_V;
            case "LEFT_ALT": return GLFW.GLFW_KEY_LEFT_ALT;
            case "C":
            default:
                return GLFW.GLFW_KEY_C;
        }
    }
}
