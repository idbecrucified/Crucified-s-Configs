package com.example.survivalfly.mixin;

import com.example.survivalfly.screen.CrucifiedRootMenuScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends net.minecraft.client.gui.screen.Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void addCustomMenuButton(CallbackInfo ci) {
        int buttonWidth = 100;
        int buttonHeight = 20;
        int x = 10;
        int y = 10;

        // Adds only the Crucified Hub button (Crucifieds Configs button removed)
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Crucified Hub"),
            button -> MinecraftClient.getInstance().setScreen(new CrucifiedRootMenuScreen(this))
        ).dimensions(x, y, buttonWidth, buttonHeight).build());
    }
}
