package com.example.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.Drawable;

@Mixin(Screen.class)
public interface ScreenInvoker {
        @Invoker("addDrawableChild")
        <T extends Element & Drawable & Selectable> T modid$addDrawableChild(T drawable);
}
