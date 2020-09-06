package com.bedrocklegends.blt.proxy;


import com.bedrocklegends.blt.DRP;
import com.bedrocklegends.blt.UpdateDRP;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy implements IProxy {
    @Override
    public void init() {
        MinecraftForge.EVENT_BUS.register(new UpdateDRP());
      
        DRP.start();
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
    @Override
    public Minecraft getMinecraft() {
    	return Minecraft.getInstance();
    }
}