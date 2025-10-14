package com.example.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;

@Mixin(CreateWorldScreen.class)
public interface WorldCreatorAccessor {
        @Accessor("worldCreator")
        WorldCreator modid$getWorldCreator();
}
