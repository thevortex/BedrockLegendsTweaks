package com.bedrocklegends.blt.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ServerProxy implements IProxy {
    @Override
    public void init() {

    }

    @Override
    public World getClientWorld() {
        throw new IllegalStateException("Cannot be run on the server!");
    }

    @Override
    public PlayerEntity getClientPlayer() {
        throw new IllegalStateException("Cannot be run on the server!");
    }

	@Override
	public Minecraft getMinecraft() {
		
		throw new IllegalStateException("Cannot be run on the server!");
	}
}