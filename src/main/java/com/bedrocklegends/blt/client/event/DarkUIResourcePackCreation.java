package com.bedrocklegends.blt.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.ClientModLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.progress.StartupMessageManager;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

/* @author lazyMods */
public class DarkUIResourcePackCreation {

    public static void createResourcePacks() {
        StartupMessageManager.addModMessage("[DarkUI]: Checking resource packs...");

        Collection<ResourceLocation> locations = Minecraft.getInstance().getResourceManager().getAllResourceLocations("mods", (s) -> s.endsWith(".png"));

        locations.forEach((res) -> {
            String texturePath = FMLPaths.GAMEDIR.get().toString() + "/resourcepacks/DarkUI/assets/" + res.getPath().replace("mods/", "");
            String[] pathElements = res.getPath().replace("mods/", "").split("/");
            StringBuilder folderPath = new StringBuilder();
            Arrays.stream(texturePath.split("/")).filter((s) -> !s.endsWith(".png")).forEach((s) -> folderPath.append(s).append("/"));

            try {
                if (!Files.exists(Paths.get(folderPath.toString())))
                    Files.createDirectories(Paths.get(folderPath.toString()));
                if (!Files.exists(Paths.get(texturePath))) {
                    StartupMessageManager.addModMessage("Adding " + pathElements[0] + " texture with name: " + pathElements[pathElements.length - 1] + "...");
                    File output = new File(texturePath);
                    OutputStream outputStream = new FileOutputStream(output);
                    InputStream inputStream = Minecraft.class.getResourceAsStream("/assets/" + res.getNamespace() + "/" + res.getPath());
                    IOUtils.copy(inputStream, outputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        if (!Files.exists(Paths.get(FMLPaths.GAMEDIR.get().toString() + "/resourcepacks/DarkUI/" + "pack.mcmeta")))
            createResourcePackMetaFile();
        StartupMessageManager.addModMessage("[DarkUI]: Done.");
    }

    public static void reload(boolean addOrRemove) {
        Minecraft.getInstance().gameSettings.resourcePacks.clear();
        Minecraft.getInstance().gameSettings.resourcePacks.add("vanilla");
        Minecraft.getInstance().gameSettings.resourcePacks.add("mod_resources");
        if (addOrRemove) {
            Minecraft.getInstance().gameSettings.resourcePacks.add("file/DarkUI");
        } else if (!addOrRemove && Minecraft.getInstance().gameSettings.resourcePacks.contains("file/DarkUI")) {
            Minecraft.getInstance().gameSettings.resourcePacks.remove("file/DarkUI");
        }
        Minecraft.getInstance().gameSettings.fillResourcePackList(Minecraft.getInstance().getResourcePackList());
        while (ClientModLoader.isLoading()) {
            System.out.println("Reloading...");
        }
        Minecraft.getInstance().gameSettings.saveOptions();
        Minecraft.getInstance().reloadResources();
    }

    private static void createResourcePackMetaFile() {
        try {
            FileWriter writer = new FileWriter(new File(FMLPaths.GAMEDIR.get().toString() + "/resourcepacks/DarkUI/" + "pack.mcmeta"));
            writer.append("{\n" +
                    "    \"pack\": {\n" +
                    "        \"description\": \"DarkUI\",\n" +
                    "        \"pack_format\": 6,\n" +
                    "        \"_comment\": \"A pack_format of 6 requires json lang files. Note: we require v5 pack meta for all mods.\"\n" +
                    "    }\n" +
                    "}");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
