package com.example.client.screen;

import com.example.util.TreeWorldCreatorExtension;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class TreeGenerationConfigScreen extends Screen {
        private static final Text TOGGLE_LABEL = Text.translatable("option.modid.crafting_table_trees");
        private static final Text DESCRIPTION = Text.translatable("text.modid.crafting_table_trees.description");

        private final CreateWorldScreen parent;
        private final TreeWorldCreatorExtension extension;
        private boolean craftingTableTrees;

        public TreeGenerationConfigScreen(CreateWorldScreen parent, TreeWorldCreatorExtension extension) {
                super(Text.translatable("screen.modid.crafting_table_trees"));
                this.parent = parent;
                this.extension = extension;
                this.craftingTableTrees = extension.modid$isCraftingTableTreesEnabled();
        }

        @Override
        protected void init() {
                super.init();
                int centerX = this.width / 2;
                int centerY = this.height / 2;

                this.addDrawableChild(CyclingButtonWidget.onOffBuilder(this.craftingTableTrees)
                                .build(centerX - 75, centerY - 10, 150, 20, TOGGLE_LABEL,
                                                (button, value) -> this.craftingTableTrees = value));

                this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> {
                        this.extension.modid$setCraftingTableTreesEnabled(this.craftingTableTrees);
                        this.close();
                }).dimensions(centerX - 75, centerY + 20, 150, 20).build());

                this.addDrawableChild(ButtonWidget.builder(ScreenTexts.CANCEL, button -> this.closeToParent())
                                .dimensions(centerX - 75, centerY + 45, 150, 20).build());
        }

        @Override
        public void close() {
                this.closeToParent();
        }

        private void closeToParent() {
                if (this.client != null) {
                        this.client.setScreen(this.parent);
                }
        }

        @Override
        public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
                this.renderBackground(drawContext, mouseX, mouseY, delta);
                drawContext.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 30, 0xFFFFFF);
                drawContext.drawCenteredTextWithShadow(this.textRenderer, DESCRIPTION, this.width / 2, this.height / 2 - 40,
                                0xA0A0A0);
                super.render(drawContext, mouseX, mouseY, delta);
        }
}
