package net.awesomesplitt.belty.neoforge.client.renderer.integration.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.awesomesplitt.belty.Belty;
import net.awesomesplitt.belty.client.renderer.ToolBeltModel;
import net.awesomesplitt.belty.registry.ItemRegistries;
import net.awesomesplitt.belty.registry.ModelLayerRegistries;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class ToolBeltCuriosRenderer implements ICurioRenderer {
    private static final ResourceLocation BELT_LOCATION = ResourceLocation.fromNamespaceAndPath(Belty.MOD_ID, "textures/entity/tool_belt.png");
    public static ToolBeltModel<LivingEntity> BELT_MODEL = null;

    public static void createModel(EntityModelSet modelSet) {
        BELT_MODEL = new ToolBeltModel<>(modelSet.bakeLayer(ModelLayerRegistries.TOOL_BELT));
    }

    public static void register() {
        CuriosRendererRegistry.register(ItemRegistries.TOOL_BELT.get(), ToolBeltCuriosRenderer::new);
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matrixStack.pushPose();
        matrixStack.translate(0.0F, -0.75, 0.0F);
        renderLayerParent.getModel().copyPropertiesTo((EntityModel<T>) BELT_MODEL);
        BELT_MODEL.setupAnim(slotContext.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(BELT_LOCATION), stack.hasFoil());
        BELT_MODEL.renderToBuffer(matrixStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        matrixStack.popPose();
    }
}
