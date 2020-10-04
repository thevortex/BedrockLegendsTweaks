package com.bedrocklegends.blt.proxy;


import com.bedrocklegends.blt.DRP;
import com.bedrocklegends.blt.UpdateDRP;

import com.bedrocklegends.blt.client.DarkUIResourcePack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        super.init();
        MinecraftForge.EVENT_BUS.register(new UpdateDRP());
        DRP.start();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }

    public void addToBus(IEventBus bus){
        super.addToBus(bus);
        bus.addListener(this::setupClient);
    }

    public void setupClient(FMLClientSetupEvent e){
        DarkUIResourcePack.createResourcePack();
    }
}