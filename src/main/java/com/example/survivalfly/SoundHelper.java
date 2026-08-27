package com.example.survivalfly;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;

public class SoundHelper {
    public static void playClick() {
        MinecraftClient.getInstance().getSoundManager().play(
            PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F)
        );
    }

    public static void playThemeChange() {
        MinecraftClient.getInstance().getSoundManager().play(
            PositionedSoundInstance.master(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, 1.2F)
        );
    }

    public static void playToggle(boolean enabled) {
        float pitch = enabled ? 1.4F : 0.8F;
        MinecraftClient.getInstance().getSoundManager().play(
            PositionedSoundInstance.master(SoundEvents.BLOCK_NOTE_BLOCK_BIT.value(), pitch)
        );
    }

    public static void playReset() {
        MinecraftClient.getInstance().getSoundManager().play(
            PositionedSoundInstance.master(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F)
        );
    }
}
