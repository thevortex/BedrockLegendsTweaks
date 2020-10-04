package com.bedrocklegends.blt;

import com.bedrocklegends.blt.data.BLTRecipesDatapack;
import com.bedrocklegends.blt.proxy.ClientProxy;
import com.bedrocklegends.blt.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BLTweaks.MOD_ID)
public class BLTweaks {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "bltweaks";

    //Current version of pack.mcmeta (1.16.3 == 6)
    public static int PACK_FORMAT_VERSION = 6;

    public BLTweaks() {
        DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new).init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerPreStarting(FMLServerAboutToStartEvent event) {
        BLTRecipesDatapack.init(event.getServer());
    }
}
