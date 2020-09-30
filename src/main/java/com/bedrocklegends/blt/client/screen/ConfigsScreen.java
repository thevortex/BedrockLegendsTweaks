package com.bedrocklegends.blt.client.screen;

import com.bedrocklegends.blt.client.event.DarkUIResourcePackCreation;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/* @author lazyMods */
public class ConfigsScreen extends Screen {

    private Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private Map<String, Object> configs;

    private final String DARK_UI = "dark_ui";

    public ConfigsScreen() {
        super(new StringTextComponent("BedrockLegends Tweaks"));
        this.configs = this.populateConfigs();
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(new CheckboxButton(20, 40, 150, 20, new StringTextComponent("Dark Mode UI"), (boolean) configs.get(DARK_UI)) {
            @Override
            public void onPress() {
                super.onPress();
                DarkUIResourcePackCreation.reload(this.isChecked());
                configs.put(DARK_UI, this.isChecked());
                saveConfigs();
            }
        });
        this.addButton(new Button(this.width / 2 - 100, this.height - 40, 200, 20, new StringTextComponent("Close"), (e) -> this.closeScreen()));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        Screen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 20, 16777215);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public void saveConfigs() {
        try {
            File map = new File(FMLPaths.GAMEDIR.get() + "/blt_configs.json");
            FileWriter fileWriter = new FileWriter(map);
            GSON.toJson(this.configs, fileWriter);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> populateConfigs() {
        Map<String, Object> configs = null;
        try {
            File bltconfigs = new File(FMLPaths.GAMEDIR.get() + "/blt_configs.json");
            if(!Files.exists(Paths.get(bltconfigs.toURI()))){
                configs = new HashMap<>();
                configs.put(DARK_UI, false);
            } else {
                configs = GSON.fromJson(new FileReader(bltconfigs), new TypeToken<Map<String, Object>>() {
                }.getType());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return configs;
    }
}
