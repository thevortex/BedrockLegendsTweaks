package com.bedrocklegends.blt;

import com.bedrocklegends.blt.proxy.ClientProxy;
import com.bedrocklegends.blt.proxy.IProxy;
import com.bedrocklegends.blt.proxy.ServerProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BLTweaks.MOD_ID)
public class BLTweaks {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "bltweaks";
    public static IProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public BLTweaks() {

        proxy.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

}
