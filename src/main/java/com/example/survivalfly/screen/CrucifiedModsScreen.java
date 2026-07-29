package com.example.survivalfly.screen;

import com.example.survivalfly.CrucifiedTheme;
import com.example.survivalfly.util.UIUtils;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CrucifiedModsScreen extends Screen {
    private final Screen parent;

    public CrucifiedModsScreen(Screen parent) {
        super(Text.literal("Crucified's Mod Hub"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelWidth = 280;
        int panelHeight = 180;
        int panelX = centerX - panelWidth / 2;
        int panelY = centerY - panelHeight / 2;

        // Back button at the bottom of the panel
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), button -> {
            this.client.setScreen(parent);
        }).dimensions(panelX + 160, panelY + panelHeight - 35, 100, 20).build());

        // Add other category/mod toggle buttons here as needed
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelWidth = 280;
        int panelHeight = 180;
        int panelX = centerX - panelWidth / 2;
        int panelY = centerY - panelHeight / 2;

        // 1. Draw main background panel
        UIUtils.drawRoundedRect(context, panelX, panelY, panelWidth, panelHeight, 8, 0xCC1a1c23);

        // 2. Draw Header with Theme Gradient and top-only rounded corners (flat bottom)
        int headerHeight = 35;
        int themeColor1 = CrucifiedTheme.getPrimaryColor(); // Adjust based on your theme implementation
        int themeColor2 = CrucifiedTheme.getSecondaryColor();

        // Draw top rounded portion and fill the lower part of the header square to prevent corner artifacts
        context.fillGradient(panelX + 8, panelY, panelX + panelWidth - 8, panelY + headerHeight, themeColor1, themeColor2);
        context.fill(panelX, panelY + 8, panelX + panelWidth, panelY + headerHeight, themeColor2);
        // Redraw top rounded caps to preserve smooth top corners
        UIUtils.drawRoundedRect(context, panelX, panelY, panelWidth, headerHeight, 8, 0x00000000); // handled via gradient fill above

        // 3. Draw Separator Line
        context.fill(panelX, panelY + headerHeight, panelX + panelWidth, panelY + headerHeight + 2, 0xFFFF007F);

        // 4. Render Larger Header Text (Title)
        context.getMatrices().push();
        context.getMatrices().scale(1.25F, 1.25F, 1.0F);
        String titleText = "Crucified's Mod Hub";
        int scaledWidth = this.textRenderer.getWidth(titleText);
        context.drawText(this.textRenderer, titleText, (int)((panelX + (panelWidth / 2) - (scaledWidth * 1.25F / 2)) / 1.25F), (int)((panelY + 6) / 1.25F), 0xFFFFFFFF, true);
        context.getMatrices().pop();

        // Subtitle text
        String subtitleText = "Category: PvP";
        int subWidth = this.textRenderer.getWidth(subtitleText);
        context.drawText(this.textRenderer, subtitleText, panelX + (panelWidth / 2) - (subWidth / 2), panelY + 22, 0xFFFFB6C1, true);

        super.render(context, mouseX, mouseY, delta);
    }
}
