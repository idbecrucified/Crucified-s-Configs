package com.example.survivalfly;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CrucifiedsThemeScreen extends Screen {
    private final Screen parent;

    public CrucifiedsThemeScreen(Screen parent) {
        super(Text.literal("Crucified Client Menu"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Fullbright Toggle Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Fullbright: " + (CrucifiedsConfigs.fullbright ? "ON" : "OFF")),
            button -> {
                CrucifiedsConfigs.fullbright = !CrucifiedsConfigs.fullbright;
                button.setMessage(Text.literal("Fullbright: " + (CrucifiedsConfigs.fullbright ? "ON" : "OFF")));
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.options != null) {
                    client.options.getGamma().setValue(CrucifiedsConfigs.fullbright ? 12.0D : 1.0D);
                }
            }
        ).dimensions(centerX - 100, centerY - 60, 200, 20).build());

        // Fast Render Toggle Button (Performance)
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Fast Render: " + (CrucifiedsConfigs.fastRender ? "ON" : "OFF")),
            button -> {
                CrucifiedsConfigs.fastRender = !CrucifiedsConfigs.fastRender;
                button.setMessage(Text.literal("Fast Render: " + (CrucifiedsConfigs.fastRender ? "ON" : "OFF")));
            }
        ).dimensions(centerX - 100, centerY - 30, 200, 20).build());

        // HUD Background Toggle Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("HUD Background: " + (CrucifiedsConfigs.hudBackground ? "ON" : "OFF")),
            button -> {
                CrucifiedsConfigs.hudBackground = !CrucifiedsConfigs.hudBackground;
                button.setMessage(Text.literal("HUD Background: " + (CrucifiedsConfigs.hudBackground ? "ON" : "OFF")));
            }
        ).dimensions(centerX - 100, centerY, 200, 20).build());

        // Reset HUD Placement Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Reset HUD Placement"),
            button -> {
                CrucifiedsConfigs.resetHudPositions();
            }
        ).dimensions(centerX - 100, centerY + 30, 200, 20).build());

        // Done / Back Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Done"),
            button -> this.close()
        ).dimensions(centerX - 100, centerY + 70, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Render Theme Gradient Background instead of a solid dark gray box
        context.fillGradient(0, 0, this.width, this.height, CrucifiedTheme.getGradientStart(), CrucifiedTheme.getGradientEnd());

        // Category / Title Text styled dynamically using the active theme's primary color
        context.drawCenteredTextWithShadow(this.textRenderer, "Crucified Client Settings", this.width / 2, 30, CrucifiedTheme.getPrimaryColor());
        context.drawText(this.textRenderer, "Category: General & HUDs", 20, 60, CrucifiedTheme.getPrimaryColor(), true);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
