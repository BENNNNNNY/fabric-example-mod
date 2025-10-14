package com.example.mixin.client;

import com.example.ExampleMod;
import com.example.util.TreeWorldCreatorExtension;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.world.GameRules.BooleanRule;
import net.minecraft.world.level.LevelInfo;

@Mixin(WorldCreator.class)
public class WorldCreatorMixin implements TreeWorldCreatorExtension {
        @Unique
        private boolean modid$craftingTableTrees;

        @Override
        public boolean modid$isCraftingTableTreesEnabled() {
                        return this.modid$craftingTableTrees;
        }

        @Override
        public void modid$setCraftingTableTreesEnabled(boolean enabled) {
                this.modid$craftingTableTrees = enabled;
        }

        @Inject(method = "createLevelInfo", at = @At("RETURN"))
        private void modid$applyGameRule(CallbackInfoReturnable<LevelInfo> cir) {
                LevelInfo levelInfo = cir.getReturnValue();
                if (levelInfo == null) {
                        return;
                }
                BooleanRule rule = levelInfo.getGameRules().get(ExampleMod.CRAFTING_TABLE_TREE_RULE);
                if (rule != null) {
                        rule.set(this.modid$craftingTableTrees, null);
                }
        }
}
