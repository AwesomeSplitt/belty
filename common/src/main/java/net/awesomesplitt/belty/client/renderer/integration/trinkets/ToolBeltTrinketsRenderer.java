package net.awesomesplitt.belty.client.renderer.integration.trinkets;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.emi.trinkets.api.*;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.awesomesplitt.belty.Belty;
import net.awesomesplitt.belty.client.renderer.ToolBeltModel;
import net.awesomesplitt.belty.registry.ItemRegistries;
import net.awesomesplitt.belty.registry.ModelLayerRegistries;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ToolBeltTrinketsRenderer implements TrinketRenderer {
    private static final ResourceLocation BELT_LOCATION = ResourceLocation.fromNamespaceAndPath(Belty.MOD_ID, "textures/entity/tool_belt.png");
    private final ToolBeltModel<LivingEntity> beltModel;

    public ToolBeltTrinketsRenderer(EntityModelSet entityModelSet) {
        this.beltModel = new ToolBeltModel<>(entityModelSet.bakeLayer(ModelLayerRegistries.TOOL_BELT));
    }

    public void register() {
        TrinketRendererRegistry.registerRenderer(ItemRegistries.TOOL_BELT.get(), this);
    }

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, PoseStack matrices, MultiBufferSource vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        renderTrinket(stack, slotReference, contextModel, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
    }

    private <M extends LivingEntity> void renderTrinket(ItemStack stack, SlotReference slotReference, EntityModel<M> contextModel, PoseStack matrices, MultiBufferSource vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        matrices.pushPose();
        matrices.translate(0.0F, -0.75, 0.0F);
        contextModel.copyPropertiesTo((EntityModel<M>) this.beltModel);
        this.beltModel.setupAnim(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.armorCutoutNoCull(BELT_LOCATION), stack.hasFoil());
        this.beltModel.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        matrices.popPose();
    }
}