package com.example.mixin;

import com.example.ExampleMod;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

@Mixin(TreeFeature.class)
public abstract class TreeFeatureMixin {
        @Unique
        private boolean modid$shouldElevate;
        @Unique
        private BlockPos modid$basePos;

        @ModifyVariable(method = "generate", at = @At(value = "STORE"), ordinal = 0)
        private BlockPos modid$raiseOrigin(BlockPos origin, FeatureContext<TreeFeatureConfig> context) {
                StructureWorldAccess world = context.getWorld();
                if (ExampleMod.craftingTableTreesEnabled(world)) {
                        this.modid$shouldElevate = true;
                        this.modid$basePos = origin;
                        return origin.up();
                }
                this.modid$shouldElevate = false;
                this.modid$basePos = null;
                return origin;
        }

        @Inject(method = "generate", at = @At("RETURN"))
        private void modid$placeCraftingTable(FeatureContext<TreeFeatureConfig> context,
                        CallbackInfoReturnable<Boolean> cir) {
                if (!this.modid$shouldElevate || !cir.getReturnValue()) {
                        return;
                }

                StructureWorldAccess world = context.getWorld();
                BlockPos base = this.modid$basePos;
                if (base == null) {
                        return;
                }

                BlockState state = world.getBlockState(base);
                if (!state.isOf(Blocks.CRAFTING_TABLE)) {
                        world.setBlockState(base, Blocks.CRAFTING_TABLE.getDefaultState(), 19);
                }
        }
}
