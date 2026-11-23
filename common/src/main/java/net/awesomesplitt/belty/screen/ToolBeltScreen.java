package net.awesomesplitt.belty.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.awesomesplitt.belty.Belty;
import net.awesomesplitt.belty.registry.KeyMappingRegistries;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class ToolBeltScreen extends AbstractContainerScreen<ToolBeltMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Belty.MOD_ID, "textures/gui/container/tool_belt.png");
    private static final List<ResourceLocation> EMPTY_SLOT_TOOLS;
    private static final List<ResourceLocation> EMPTY_SLOT_BUNDLES;
    private final List<CyclingSlotBackground> toolSlotBgs;
    private final List<CyclingSlotBackground> bundleSlotsBg;

    public ToolBeltScreen(ToolBeltMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.toolSlotBgs = createToolSlotBackgrounds();
        this.bundleSlotsBg = createBundleSlotBackgrounds();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        super.renderTooltip(guiGraphics, i, j);
    }

    protected void init() {
        super.init();
        int slots = menu.tool_slots + menu.bundle_slots;
        this.titleLabelX = imageWidth/2 - slots*9;
        this.titleLabelY = 18;
    }

    @Override
    public boolean keyPressed(int i, int j, int k) {
        if (KeyMappingRegistries.OPEN_TOOL_BELT.matches(i, j)) {
            this.onClose();
            return true;
        }
        return super.keyPressed(i, j, k);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y + 66, 0, 66, imageWidth, imageHeight - 66);

        int slots = menu.tool_slots + menu.bundle_slots;
        x = width/2 - slots*9;

        guiGraphics.blit(GUI_TEXTURE, x - 7, y + 12, 54, 12, 25, 42);
        guiGraphics.blit(GUI_TEXTURE, x + (slots - 1) * 18, y + 12, 97, 12, 25, 42);

        for (int l = 1; l < slots - 1; l++) {
            guiGraphics.blit(GUI_TEXTURE, x + l * 18, y + 12, 79, 12, 18, 42);
        }

        toolSlotBgs.forEach(slot -> slot.render(menu, guiGraphics, f, this.leftPos, this.topPos));
        bundleSlotsBg.forEach(slot -> slot.render(menu, guiGraphics, f, this.leftPos, this.topPos));
    }

    public void containerTick() {
        super.containerTick();
        this.toolSlotBgs.forEach(slot -> slot.tick(EMPTY_SLOT_TOOLS));
        this.bundleSlotsBg.forEach(slot -> slot.tick(EMPTY_SLOT_BUNDLES));
    }

    private List<CyclingSlotBackground> createBundleSlotBackgrounds() {
        var slots = new ArrayList<CyclingSlotBackground>(menu.bundle_slots);

        for (int i = menu.tool_slots; i < menu.tool_slots + menu.bundle_slots; i++) {
            slots.add(new CyclingSlotBackground(i));
        }

        return slots;
    }

    private List<CyclingSlotBackground> createToolSlotBackgrounds() {
        var slots = new ArrayList<CyclingSlotBackground>(menu.tool_slots);

        for (int i = 0; i < menu.tool_slots; i++) {
            slots.add(new CyclingSlotBackground(i));
        }

        return slots;
    }

    static {
        EMPTY_SLOT_TOOLS = List.of(
                ResourceLocation.withDefaultNamespace("item/empty_slot_axe"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_hoe"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_pickaxe"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_shovel"),
                ResourceLocation.withDefaultNamespace("item/empty_slot_sword")
        );
        EMPTY_SLOT_BUNDLES = List.of(
                ResourceLocation.fromNamespaceAndPath(Belty.MOD_ID, "item/empty_slot_bundle")
        );
    }
}
