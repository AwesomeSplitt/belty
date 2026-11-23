package net.awesomesplitt.belty.registry;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.awesomesplitt.belty.Belty;
import net.awesomesplitt.belty.client.renderer.ToolBeltModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModelLayerRegistries {
    public static ModelLayerLocation TOOL_BELT = register("tool_belt");

    private static ModelLayerLocation register(String name) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Belty.MOD_ID, name), "main");
    }

    public static void register() {
        EntityModelLayerRegistry.register(TOOL_BELT, ToolBeltModel::createBodyLayer);
    }
}
