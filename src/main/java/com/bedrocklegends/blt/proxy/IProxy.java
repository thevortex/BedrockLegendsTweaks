package com.bedrocklegends.blt.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface IProxy {
   void init();
   World getClientWorld();
   PlayerEntity getClientPlayer();
   Minecraft getMinecraft();
}
