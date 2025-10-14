package com.example.mixin.client;

import com.example.ExampleMod;
import com.example.util.TreeWorldCreatorExtension;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanRule;

@Mixin(WorldCreator.class)
public class WorldCreatorMixin implements TreeWorldCreatorExtension {
        @Shadow
        private GameRules gameRules;

        @Unique
        private boolean modid$craftingTableTrees;

        @Override
        public boolean modid$isCraftingTableTreesEnabled() {
                        return this.modid$craftingTableTrees;
        }

        @Override
        public void modid$setCraftingTableTreesEnabled(boolean enabled) {
                this.modid$craftingTableTrees = enabled;
                if (this.gameRules != null) {
                        BooleanRule rule = this.gameRules.get(ExampleMod.CRAFTING_TABLE_TREE_RULE);
                        if (rule != null) {
                                rule.set(enabled, null);
                        }
                }
        }

        @Inject(method = "setGameRules", at = @At("HEAD"))
        private void modid$syncFromGameRules(GameRules gameRules, CallbackInfo ci) {
                if (gameRules == null) {
                        this.modid$craftingTableTrees = false;
                        return;
                }
                this.modid$craftingTableTrees = gameRules.getBoolean(ExampleMod.CRAFTING_TABLE_TREE_RULE);
        }

        @Inject(method = "getGameRules", at = @At("RETURN"))
        private void modid$applyGameRule(CallbackInfoReturnable<GameRules> cir) {
                GameRules gameRules = cir.getReturnValue();
                if (gameRules == null) {
                        return;
                }
                BooleanRule rule = gameRules.get(ExampleMod.CRAFTING_TABLE_TREE_RULE);
                if (rule != null) {
                        rule.set(this.modid$craftingTableTrees, null);
                }
        }
}
