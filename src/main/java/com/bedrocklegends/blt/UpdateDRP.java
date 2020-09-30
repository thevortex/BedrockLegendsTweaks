package com.bedrocklegends.blt;


import com.bedrocklegends.blt.DRP.EnumState;
import com.bedrocklegends.blt.DRP.State;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.WorldSelectionScreen;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import net.minecraftforge.api.distmarker.Dist;

@EventBusSubscriber(modid = BLTweaks.MOD_ID, value = Dist.CLIENT)
public class UpdateDRP {
		
		@SubscribeEvent
		public void on(InitGuiEvent.Pre event) {
			if (!DRP.isEnabled()) {
				return;
			}
			if (event.getGui() instanceof MainMenuScreen || event.getGui() instanceof WorldSelectionScreen || event.getGui() instanceof MultiplayerScreen) {
				final State state = DRP.getCurrent();
				if (state == null || state.getState() != EnumState.MENU) {
					DRP.setIdling();
				}
			}
		}
		
		@SubscribeEvent
		public void on(EntityJoinWorldEvent event) {
			if (!DRP.isEnabled()) {
				return;
			}
			if (event.getEntity() instanceof ClientPlayerEntity) {
				final ClientPlayerEntity player = (ClientPlayerEntity) event.getEntity();
				if (player.getUniqueID().equals(Minecraft.getInstance().player.getUniqueID())) {
					
					DRP.setDimension(player.getEntityWorld().dimensionType.infiniburn.getPath());
				}
			}
		}
	
}
