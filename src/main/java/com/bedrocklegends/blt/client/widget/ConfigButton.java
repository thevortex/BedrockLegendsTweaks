package com.bedrocklegends.blt.client.widget;

import com.bedrocklegends.blt.BLTweaks;
import com.bedrocklegends.blt.client.screen.ConfigsScreen;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

/* @author lazyMods */
public class ConfigButton extends Widget {

    private static final ResourceLocation CONFIG_BUTTON = new ResourceLocation(BLTweaks.MOD_ID, "textures/gui/button_config_screen.png");

    public ConfigButton(int xIn, int yIn) {
        super(xIn, yIn, 20, 20, new StringTextComponent(""));
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        super.onClick(mouseX, mouseY);
        Minecraft.getInstance().displayGuiScreen(new ConfigsScreen());
    }

    @Override
    public void renderButton(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();

        mc.getTextureManager().bindTexture(CONFIG_BUTTON);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (!isHovered)
            this.blit(stack, this.x, this.y, 0, 0, 20, 20);
        else this.blit(stack, this.x, this.y, 20, 0, 20, 20);
    }
}
