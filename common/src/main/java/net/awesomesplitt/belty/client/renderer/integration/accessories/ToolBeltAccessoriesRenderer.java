package net.awesomesplitt.belty.client.renderer.integration.accessories;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
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

public class ToolBeltAccessoriesRenderer implements AccessoryRenderer {
    private static final ResourceLocation BELT_LOCATION = ResourceLocation.fromNamespaceAndPath(Belty.MOD_ID, "textures/entity/tool_belt.png");
    private final ToolBeltModel<LivingEntity> beltModel;

    public ToolBeltAccessoriesRenderer(EntityModelSet entityModelSet) {
        this.beltModel = new ToolBeltModel<>(entityModelSet.bakeLayer(ModelLayerRegistries.TOOL_BELT));
    }

    public void register() {
        AccessoriesRendererRegistry.registerRenderer(ItemRegistries.TOOL_BELT.get(), () -> this);
    }

    @Override
    public <M extends LivingEntity> void render(ItemStack stack, SlotReference reference, PoseStack matrices, EntityModel<M> model, MultiBufferSource multiBufferSource, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matrices.pushPose();
        matrices.translate(0.0F, -0.75, 0.0F);
        model.copyPropertiesTo((EntityModel<M>) this.beltModel);
        this.beltModel.setupAnim(reference.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(BELT_LOCATION), stack.hasFoil());
        this.beltModel.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        matrices.popPose();
    }
}
