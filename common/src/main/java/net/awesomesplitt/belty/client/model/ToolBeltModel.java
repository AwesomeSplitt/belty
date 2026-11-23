package net.awesomesplitt.belty.client.model;

// Made with Blockbench 5.2.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class ToolBeltModel<T extends LivingEntity> extends AgeableListModel<T> {
	private final ModelPart tool_belt;

	public ToolBeltModel(ModelPart root) {
		this.tool_belt = root.getChild("tool_belt");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tool_belt = partdefinition.addOrReplaceChild("tool_belt", CubeListBuilder.create().texOffs(14, 0).addBox(-4.0F, -2.0F, -2.998F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 3).addBox(-4.0F, -2.0F, 1.998F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(4.0F, -2.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 8).addBox(-5.0F, -2.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(18, 7).addBox(2.0F, -2.0F, -2.999F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(22, 4).addBox(-5.001F, -2.0F, -3.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(14, 5).addBox(5.001F, -2.0F, -2.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(28, 7).addBox(-4.0F, -2.0F, -2.999F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.tool_belt);
	}

	@Override
	public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.tool_belt.z = entity.isCrouching() ? 4F : 0F;
	}
}