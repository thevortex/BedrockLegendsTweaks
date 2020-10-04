package com.bedrocklegends.blt.proxy;

import com.bedrocklegends.blt.client.DarkUIResourcePack;
import com.bedrocklegends.blt.data.BLTRecipesDatapack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CommonProxy {

    public void init() {
        this.addToBus(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public void addToBus(IEventBus modEventBus) {
        modEventBus.addListener(this::setupCommon);
    }

    public void setupCommon(FMLCommonSetupEvent e){
        DarkUIResourcePack.createResourcePack();
    }
}