package net.awesomesplitt.belty.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.awesomesplitt.belty.Belty;
import net.awesomesplitt.belty.registry.ModelLayerRegistries;
import net.awesomesplitt.belty.item.ToolBeltItem;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ToolBeltLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation BELT_LOCATION = ResourceLocation.fromNamespaceAndPath(Belty.MOD_ID, "textures/entity/tool_belt.png");
    private final ToolBeltModel<T> beltModel;

    public ToolBeltLayer(RenderLayerParent<T, M> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.beltModel = new ToolBeltModel<>(entityModelSet.bakeLayer(ModelLayerRegistries.TOOL_BELT));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T entity, float f, float g, float h, float j, float k, float l) {
        ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.LEGS);
        if (itemStack.getItem() instanceof ToolBeltItem) {
            poseStack.pushPose();
            poseStack.translate(0.0F, -0.75, 0.0F);
            this.getParentModel().copyPropertiesTo(this.beltModel);
            this.beltModel.setupAnim(entity, f, g, j, k, l);
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(BELT_LOCATION), itemStack.hasFoil());
            this.beltModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
    }
}
