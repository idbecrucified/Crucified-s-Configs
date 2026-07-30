package com.example.survivalfly.mixin;

import com.example.survivalfly.CrucifiedsThemeScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Inject(method = "init", at = @At("TAIL"))
    private void addCustomModsButton(CallbackInfo ci) {
        TitleScreen screen = (TitleScreen) (Object) this;
        
        int x = screen.width / 2 - 100;
        int y = screen.height / 4 + 48 + 72; 

        // Cast to Screen to access the protected addDrawableChild method safely
        ((Screen) (Object) screen).addDrawableChild(ButtonWidget.builder(
            Text.literal("Crucifieds Configs"),
            button -> MinecraftClient.getInstance().setScreen(new CrucifiedsThemeScreen(screen))
        ).dimensions(x, y, 200, 20).build());
    }
}
