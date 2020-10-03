package com.bedrocklegends.blt.proxy;


import com.bedrocklegends.blt.DRP;
import com.bedrocklegends.blt.UpdateDRP;

import com.bedrocklegends.blt.client.DarkUIResourcePackCreation;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientProxy implements IProxy {

    @Override
    public void init() {
        MinecraftForge.EVENT_BUS.register(new UpdateDRP());
      
        DRP.start();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }

    public void setupClient(FMLClientSetupEvent e){
        DarkUIResourcePackCreation.createResourcePacks();
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