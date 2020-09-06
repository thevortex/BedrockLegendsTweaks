package com.bedrocklegends.blt;

import java.time.OffsetDateTime;
import java.util.*;

import com.repack.com.jagrosh.discordipc.IPCClient;
import com.repack.com.jagrosh.discordipc.entities.RichPresence.Builder;
import com.repack.com.jagrosh.discordipc.exceptions.NoDiscordClientException;

import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.ModList;

@OnlyIn(Dist.CLIENT)
public class DRP {
	
	private static final IPCClient CLIENT = new IPCClient(750913315450126438L);
	
	private static boolean isEnabled = false;
	
	private static final OffsetDateTime TIME = OffsetDateTime.now();
	public static State currentState = new State(EnumState.STARTUP);
	
	private static int errorCount = 0;
	
	private static final Timer TIMER = new Timer("Discord Rich Presence Timer Thread");
	private static TimerTask timerTask;
	
	static {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> stop(), "Discord Rich Presence Stop Thread"));
	}
	
	public static void start() {
		try {
			CLIENT.connect();
			TIMER.schedule(timerTask = new TimerTask() {
				
				@Override
				public void run() {
					setState(currentState);
					
				}
			}, 1000, 1000 * 120);
			isEnabled = true;
			
		} catch (final NoDiscordClientException ex) {
			
		}
	}
	
	public static void stop() {
		if (timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}
		try {
			CLIENT.close();
		} catch (final Exception ex) {
		}
		errorCount = 0;
		isEnabled = false;
		
	}
	
	public static void setIdling() {
		setState(new State(EnumState.MENU));
	}
	
	public static void setDimension(Dimension dimension) {
		setState(getStateFromDimension(dimension));
	}
	
	
	public static State getStateFromDimension(Dimension dimension) {
		//func_236063_b_ = .getType()
		switch (dimension.func_236063_b_().func_241513_m_()) {
		case -1:
			return new State(EnumState.NETHER);
		case 0:
			return new State(EnumState.OVERWORLD);
		case 1:
			return new State(EnumState.END);
		default:
			return new State(EnumState.DIM, dimension.func_236063_b_().getSuffix());
		}
	}
	
	public static void setState(State state) {
		currentState = state;
		final Builder builder = new Builder();
		builder.setDetails(ModList.get().size() + " Mods");
		builder.setState(state.getState().getMessage(state.getReplace()));
		builder.setStartTimestamp(TIME);
		builder.setLargeImage("me", "Monumental Experience");
		
		if (state.getState() == EnumState.STARTUP) {
			builder.setLargeImage("me", "Monumental Experience");
		}
		if (state.getState() == EnumState.MENU) {
			builder.setLargeImage("me", "Monumental Experience");
		}
		if (state.getState() == EnumState.OVERWORLD) {
			builder.setLargeImage("me", "Monumental Experience");
			builder.setSmallImage("", "In the Overworld");
		}
		if (state.getState() == EnumState.NETHER) {
			builder.setLargeImage("me", "Monumental Experience");
			builder.setSmallImage("nether", "In the Nether");
		}
		if (state.getState() == EnumState.END) {
			builder.setLargeImage("atm6", "Monumental Experience");
			builder.setSmallImage("end", "In the End");
		}
		
		try {
			CLIENT.sendRichPresence(builder.build());
		} catch (final Exception ex) {
			try {
				CLIENT.connect();
				errorCount = 0;
				CLIENT.sendRichPresence(builder.build());
			} catch (final Exception ex2) {
				try {
					CLIENT.close();
				} catch (final Exception ex3) {
				}
				errorCount++;
				if (errorCount > 10) {
					stop();
				}
			}
		}
	}
	
	public static boolean isEnabled() {
		return isEnabled;
	}
	
	public static State getCurrent() {
		return currentState;
	}
	
	public static class State {
		
		private final EnumState state;
		private final String replace;
		
		public State(EnumState state) {
			this(state, "");
		}
		
		public State(EnumState state, String replace) {
			this.state = state;
			this.replace = replace;
		}
		
		public EnumState getState() {
			return state;
		}
		
		public String getReplace() {
			return replace;
		}
	}
	
	public static enum EnumState {
		
		STARTUP("Starting Minecraft", "me", "Monumental Experience"),
		MENU("Main Menu", "me", "Monumental Experience"),
		OVERWORLD("Dimension: Overworld", "me", "Monumental Experience"),
		NETHER("Dimension: Nether", "me", "Monumental Experience"),
		END("Dimension: The End", "me", "Monumental Experience"),
		DIM("Dimension: %s", "me", "Monumental Experience");
		
		private final String message, imagename, imagekey;
		
		private EnumState(String message, String imagename, String imagekey) {
			this.message = message;
			this.imagename = imagename;
			this.imagekey = imagekey;
		}
		
		public String getMessage(String replace) {
			return message.replace("%s", replace);
		}
		
		public String getImageName(String replace) {
			return imagename.replace("%s", replace);
		}
		
		public String getImageKey() {
			return imagekey;
		}
	}

	public static void setDimension(DimensionType dimension) {
		
	}

	public static void setDimension(String path) {

		setState(getStateFromDimension(path));
	}

	private static State getStateFromDimension(String path) {
		
			//func_236063_b_ = .getType()
			switch (path) {
			case "infiniburn_nether":
				return new State(EnumState.NETHER);
			case "infiniburn_overworld":
				return new State(EnumState.OVERWORLD);
			case "infiniburn_end":
				return new State(EnumState.END);
			default:
				return new State(EnumState.DIM, path);
			}
	}
	


}