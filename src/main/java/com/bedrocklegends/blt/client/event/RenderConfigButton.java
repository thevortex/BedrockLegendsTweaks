package com.bedrocklegends.blt.client.event;

import com.bedrocklegends.blt.client.widget.ConfigButton;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/* @author lazyMods */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class RenderConfigButton {

    @SubscribeEvent
    public static void onGuiPostInit(GuiScreenEvent.InitGuiEvent.Post event) {
        Screen screen = event.getGui();
        if (screen instanceof MainMenuScreen) {
            if (event.getWidgetList() != null) {
                event.addWidget(new ConfigButton(screen.width - 22, 2));
            }
        }
    }
}