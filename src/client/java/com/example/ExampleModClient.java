package com.example;

import com.example.client.screen.TreeGenerationConfigScreen;
import com.example.mixin.client.ScreenInvoker;
import com.example.mixin.client.WorldCreatorAccessor;
import com.example.util.TreeWorldCreatorExtension;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget.Builder;
import net.minecraft.text.Text;

import java.util.Optional;

public class ExampleModClient implements ClientModInitializer {
        private static final Text CUSTOMIZE_TEXT = Text.translatable("createWorld.customizeType");

        @Override
        public void onInitializeClient() {
                ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
                        if (screen instanceof CreateWorldScreen createWorldScreen) {
                                enableCustomizeButton(createWorldScreen);
                        }
                });
        }

        private static void enableCustomizeButton(CreateWorldScreen screen) {
                Optional<ButtonWidget> original = screen.children().stream()
                                .filter(element -> element instanceof ButtonWidget button
                                                && CUSTOMIZE_TEXT.equals(button.getMessage()))
                                .map(ButtonWidget.class::cast)
                                .findFirst();

                int x;
                int y;
                int width;
                int height;

                if (original.isPresent()) {
                        ButtonWidget button = original.get();
                        button.visible = false;
                        button.active = false;
                        x = button.getX();
                        y = button.getY();
                        width = button.getWidth();
                        height = button.getHeight();
                } else {
                        x = screen.width / 2 - 75;
                        y = screen.height / 2;
                        width = 150;
                        height = 20;
                }

                Builder builder = ButtonWidget.builder(CUSTOMIZE_TEXT, btn -> openCustomizeScreen(screen));
                ButtonWidget newButton = builder.dimensions(x, y, width, height).build();
                ((ScreenInvoker) screen).modid$addDrawableChild(newButton);
        }

        private static void openCustomizeScreen(CreateWorldScreen parent) {
                MinecraftClient client = MinecraftClient.getInstance();
                Object worldCreator = ((WorldCreatorAccessor) parent).modid$getWorldCreator();
                if (!(worldCreator instanceof TreeWorldCreatorExtension extension)) {
                        ExampleMod.LOGGER.warn("WorldCreator missing Crafting Table Trees extension");
                        return;
                }
                client.setScreen(new TreeGenerationConfigScreen(parent, extension));
        }
}
