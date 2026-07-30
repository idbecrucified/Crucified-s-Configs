package com.example.survivalfly.mixin;

import com.example.survivalfly.screen.CrucifiedModsScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void applyCustomZoom(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
        if (CrucifiedModsScreen.isZoomEnabled()) {
            double originalFov = cir.getReturnValue();
            // Dynamically divides FOV by the selected zoom intensity multiplier (2.0x, 3.0x, 4.0x, 6.0x, 8.0x)
            cir.setReturnValue(originalFov / CrucifiedModsScreen.zoomIntensity);
        }
    }
}
