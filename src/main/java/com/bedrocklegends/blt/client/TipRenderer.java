package com.bedrocklegends.blt.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.loading.FMLPaths;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/* @author lazyMods */
public class TipRenderer {

    public String tipTile;
    public String tip;
    private static int tipTitleColor = Color.RED.getRGB();
    private static int tipColor = Color.WHITE.getRGB();

    private static MatrixStack stack = new MatrixStack();

    private static final TipRenderer BLANK = new TipRenderer("", "");

    public TipRenderer(String tipTile, String tip) {
        this.tipTile = tipTile;
        this.tip = tip;
    }

    public static TipRenderer getTip() {
        Map<String, String> tips = getTips();
        if(tips.size() > 0){
            int randomIndex = new Random().nextInt(tips.size());
            String key = new ArrayList<>(tips.keySet()).get(randomIndex);
            return new TipRenderer(key, tips.get(key));
        }
        return BLANK;
    }

    public static void renderTipOnScreen(Screen screen, String tipTitle, String tip) {
        screen.getMinecraft().fontRenderer.func_243246_a(stack, new StringTextComponent(tipTitle), 10, screen.height - 35, tipTitleColor);
        screen.getMinecraft().fontRenderer.func_243246_a(stack, new StringTextComponent(tip), 10, screen.height - 20, tipColor);
    }

    private static Map<String, String> getTips() {
        File tipFile = new File(FMLPaths.GAMEDIR.get() + "/tips.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (!Files.exists(tipFile.toPath())) return new HashMap<>();
        try {
            return gson.fromJson(new FileReader(tipFile), new TypeToken<HashMap<String, String>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}
