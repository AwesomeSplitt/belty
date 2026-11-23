package net.awesomesplitt.belty.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public final class ToolBeltContainer extends SimpleContainer {
    private final ItemStack belt_item;
    private final int tool_slots;
    private final int bundle_slots;

    public ToolBeltContainer(ItemStack item, int tool_slots, int bundle_slots) {
        super(tool_slots + bundle_slots);
        this.belt_item = item;
        this.tool_slots = tool_slots;
        this.bundle_slots = bundle_slots;
        ItemContainerContents contents = item.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        contents.copyInto(this.getItems());
    }

    public int getContainerSize() {
        return this.tool_slots + this.bundle_slots;
    }

    public int getToolSlots() {
        return this.tool_slots;
    }

    public int getBundle_slots() {
        return this.bundle_slots;
    }

    public void setChanged() {
        super.setChanged();
        this.belt_item.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(this.getItems()));
    }
}
