package com.bedrocklegends.blt.client.event;

import com.bedrocklegends.blt.client.TipRenderer;
import com.bedrocklegends.blt.client.screen.ConfigsScreen;
import com.bedrocklegends.blt.client.widget.ConfigButton;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WorldLoadProgressScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

/* @author lazyMods */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class GuiScreenListener {

    private static TipRenderer chosenTip;

    @SubscribeEvent
    public static void onGuiOpen(GuiOpenEvent event) {
        Screen screen = event.getGui();

        if (screen instanceof WorldLoadProgressScreen) {
            Map<String, Object> configs = ConfigsScreen.populateConfigs();
            if ((boolean) configs.get(ConfigsScreen.RENDER_TIPS)) {
                chosenTip = TipRenderer.getTip();
            }
        }
    }

    @SubscribeEvent
    public static void onGuiPostInit(GuiScreenEvent.InitGuiEvent.Post event) {
        Screen screen = event.getGui();
        if (screen instanceof MainMenuScreen) {
            if (event.getWidgetList() != null) {
                event.addWidget(new ConfigButton(screen.width - 22, 2));
            }
        }
    }

    @SubscribeEvent
    public static void onGuiPostDraw(GuiScreenEvent.DrawScreenEvent.Post event) {
        Screen screen = event.getGui();

        if (screen instanceof WorldLoadProgressScreen) {
            Map<String, Object> configs = ConfigsScreen.populateConfigs();
            if ((boolean) configs.get(ConfigsScreen.RENDER_TIPS)) {
                TipRenderer.renderTipOnScreen(screen, chosenTip.tipTile, chosenTip.tip);
            }
        }
    }
}