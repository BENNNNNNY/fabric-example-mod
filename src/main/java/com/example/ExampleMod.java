package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
        public static final String MOD_ID = "modid";
        public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
        public static final GameRules.Key<GameRules.BooleanRule> CRAFTING_TABLE_TREE_RULE =
                        GameRuleRegistry.register("craftingTableTrees",
                                        GameRules.Category.UPDATES, GameRules.BooleanRule.create(false));

        @Override
        public void onInitialize() {
                LOGGER.info("Crafting table tree generation initialized");
        }

        public static boolean craftingTableTreesEnabled(WorldAccess world) {
                if (world instanceof ServerWorld serverWorld) {
                        return serverWorld.getGameRules().getBoolean(CRAFTING_TABLE_TREE_RULE);
                }
                if (world.getServer() != null) {
                        return world.getServer().getGameRules().getBoolean(CRAFTING_TABLE_TREE_RULE);
                }
                return false;
        }

        public static boolean craftingTableTreesEnabled(StructureWorldAccess world) {
                return craftingTableTreesEnabled((WorldAccess) world);
        }

        public static boolean craftingTableTreesEnabled(ServerWorld world) {
                return world.getGameRules().getBoolean(CRAFTING_TABLE_TREE_RULE);
        }
}
