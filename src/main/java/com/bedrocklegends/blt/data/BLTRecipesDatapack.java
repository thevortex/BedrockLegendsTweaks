package com.bedrocklegends.blt.data;

import com.bedrocklegends.blt.BLTweaks;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.command.impl.ReloadCommand;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.StartupMessageManager;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/* @author lazyMods */
public class BLTRecipesDatapack {

    public static void init(MinecraftServer server) {
        getWorldsLocations().forEach(BLTRecipesDatapack::createDatapack);

        ResourcePackList resourcepacklist = server.getResourcePacks();
        resourcepacklist.reloadPacksFromFinders();
        Collection<? extends ResourcePackInfo> enabledPacks = resourcepacklist.getEnabledPacks();
        Collection<? extends ResourcePackInfo> allPacks = resourcepacklist.getAllPacks();
        List<ResourcePackInfo> disabledPacks = allPacks.stream().filter((resourcePackInfo) -> !enabledPacks.contains(resourcePackInfo)).collect(Collectors.toList());
        if (disabledPacks.size() > 0 && disabledPacks.contains(resourcepacklist.getPackInfo("file/BLTRecipes"))) {
            ResourcePackInfo bltPack = disabledPacks.stream().filter(r -> r.getName().equals("file/BLTRecipes")).collect(Collectors.toList()).get(0);
            List<ResourcePackInfo> currentEnabledPacks = Lists.newArrayList(resourcepacklist.getEnabledPacks());
            currentEnabledPacks.add(bltPack);
            ReloadCommand.func_241062_a_(currentEnabledPacks.stream().map(ResourcePackInfo::getName).collect(Collectors.toList()), server.getCommandSource());
        }
    }

    private static void createDatapack(String worldName) {
        StartupMessageManager.addModMessage("[BLTRecipes]: Start creating recipes");
        Collection<ResourceLocation> locations = Minecraft.getInstance().getResourceManager().getAllResourceLocations("modrecipes", (s) -> s.endsWith(".json"));

        locations.forEach((res) -> {
            String recipePath = FMLPaths.GAMEDIR.get() + "/saves/" + worldName + "/datapacks/BLTRecipes/data/" + res.getPath().replace("modrecipes/", "");
            String[] pathElements = res.getPath().replace("modrecipes/", "").split("/");
            StringBuilder folderPath = new StringBuilder();
            Arrays.stream(recipePath.split("/")).filter((s) -> !s.endsWith(".json")).forEach((s) -> folderPath.append(s).append("/"));

            try {
                if (!Files.exists(Paths.get(folderPath.toString())))
                    Files.createDirectories(Paths.get(folderPath.toString()));
                if (!Files.exists(Paths.get(recipePath))) {
                    StartupMessageManager.addModMessage("Adding " + pathElements[0] + " recipe with name: " + pathElements[pathElements.length - 1] + "...");
                    File output = new File(recipePath);
                    OutputStream outputStream = new FileOutputStream(output);
                    InputStream inputStream = Minecraft.class.getResourceAsStream("/assets/" + res.getNamespace() + "/" + res.getPath());
                    if (inputStream != null)
                        IOUtils.copy(inputStream, outputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        createResourcePackMetaFile(worldName);
        StartupMessageManager.addModMessage("[BLTRecipes]: Done.");
    }

    private static void createResourcePackMetaFile(String worldName) {
        try {
            FileWriter writer = new FileWriter(new File(FMLPaths.GAMEDIR.get() + "/saves/" + worldName + "/datapacks/BLTRecipes/pack.mcmeta"));
            writer.append("{\n" +
                    "   \"pack\":{\n" +
                    "      \"pack_format\": " + BLTweaks.PACK_FORMAT_VERSION + ",\n" +
                    "      \"description\": \"BedrockLegends Tweaks Recipes\"\n" +
                    "   }\n" +
                    "}");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getWorldsLocations() {
        List<String> folderNames = new ArrayList<>();
        File[] directories = new File(FMLPaths.GAMEDIR.get() + "/saves").listFiles(File::isDirectory);
        if (directories != null) {
            for (File directory : directories) {
                folderNames.add(directory.getName());
            }
        }
        return folderNames;
    }
}
