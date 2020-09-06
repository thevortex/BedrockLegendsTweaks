package com.bedrocklegends.blt.mixins;

import net.minecraft.client.gui.LoadingGui;
import net.minecraft.client.gui.ResourceLoadProgressGui;
import net.minecraft.util.ColorHelper;

import java.util.concurrent.ThreadLocalRandom;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;


@Mixin(value = ResourceLoadProgressGui.class, priority = 1001)
public abstract class ProgressLoadGuiMixin {

	
	@ModifyArg(method = {
	"func_238629_a_" }, remap = false, at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/client/gui/ResourceLoadProgressGui;func_238467_a_(Lcom/mojang/blaze3d/matrix/MatrixStack;IIIII)V"), index = 5)
private int barBorderColor(int color) {
return ColorHelper.PackedColor.func_233006_a_(255, 185, 55, 35);
}

@ModifyArg(method = {
	"func_238629_a_" }, remap = false, at = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/client/gui/ResourceLoadProgressGui;func_238467_a_(Lcom/mojang/blaze3d/matrix/MatrixStack;IIIII)V"), index = 5)
private int barBorderColor1(int color) {
return ColorHelper.PackedColor.func_233006_a_(255, 185, 55, 35);
}

@ModifyArg(method = {
	"func_238629_a_" }, remap = false, at = @At(value = "INVOKE", ordinal = 2, target = "Lnet/minecraft/client/gui/ResourceLoadProgressGui;func_238467_a_(Lcom/mojang/blaze3d/matrix/MatrixStack;IIIII)V"), index = 5)
private int barBorderColor2(int color) {
return ColorHelper.PackedColor.func_233006_a_(255, 185, 55, 35);
}

@ModifyArg(method = {
	"func_238629_a_" }, remap = false, at = @At(value = "INVOKE", ordinal = 3, target = "Lnet/minecraft/client/gui/ResourceLoadProgressGui;func_238467_a_(Lcom/mojang/blaze3d/matrix/MatrixStack;IIIII)V"), index = 5)
private int barBorderColor3(int color) {
return ColorHelper.PackedColor.func_233006_a_(255, 185, 55, 35);
}

@ModifyArg(method = {
	"func_238629_a_" }, remap = false, at = @At(value = "INVOKE", ordinal = 4, target = "Lnet/minecraft/client/gui/ResourceLoadProgressGui;func_238467_a_(Lcom/mojang/blaze3d/matrix/MatrixStack;IIIII)V"), index = 5)
private int barColor(int color) {
return ColorHelper.PackedColor.func_233006_a_(255, 218, 165, 32);
}

    
	
}
