package com.example.survivalfly;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CrucifiedHudEditScreen extends Screen {
    private static final Identifier LOGO_TEXTURE = new Identifier("survivalfly", "textures/gui/logo.png");

    public CrucifiedHudEditScreen() {
        super(Text.literal("Crucified's HUD Overlay"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int bottomY = this.height - 70;

        // Centered "MODS" button (no emote or shirt icons)
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("MODS"),
                button -> this.client.setScreen(new LunarModMenuScreen(this))
        ).dimensions(centerX - 110, bottomY, 220, 30).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);

        // Render all active HUDs live on the screen
        if (this.client != null) {
            HudRenderer.renderAllHuds(context, this.client);
        }

        // Render Custom Logo at top center
        int logoWidth = 160;
        int logoHeight = 60;
        int logoX = (this.width - logoWidth) / 2;
        int logoY = 25;
        
        try {
            context.drawTexture(LOGO_TEXTURE, logoX, logoY, 0, 0, logoWidth, logoHeight, logoWidth, logoHeight);
        } catch (Exception e) {
            context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("§d§lCRUCIFIED'S MODS"), this.width / 2, logoY + 20, 0xFFFFFF);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
