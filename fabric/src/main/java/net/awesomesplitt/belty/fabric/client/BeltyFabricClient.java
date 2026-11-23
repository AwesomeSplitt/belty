package net.awesomesplitt.belty.fabric.client;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.awesomesplitt.belty.client.renderer.entity.layers.ToolBeltLayer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public final class BeltyFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof PlayerRenderer playerRenderer) {
                registrationHelper.register(new ToolBeltLayer<>(playerRenderer, context.getModelSet()));
            }
        }));
    }
}
