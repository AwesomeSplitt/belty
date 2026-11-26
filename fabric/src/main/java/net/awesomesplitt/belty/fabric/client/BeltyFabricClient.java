package net.awesomesplitt.belty.fabric.client;

import net.awesomesplitt.belty.Belty;
import net.awesomesplitt.belty.client.renderer.ToolBeltLayer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;

public final class BeltyFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof PlayerRenderer playerRenderer) {
                registrationHelper.register(new ToolBeltLayer<>(playerRenderer, context.getModelSet()));
            }

            Belty.registerAccessories(context.getModelSet());
            Belty.registerTrinkets(context.getModelSet());
        }));
    }
}
