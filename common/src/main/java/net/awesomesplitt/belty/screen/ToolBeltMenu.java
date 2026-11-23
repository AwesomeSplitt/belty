package net.awesomesplitt.belty.screen;

import net.awesomesplitt.belty.item.ToolBeltContainer;
import net.awesomesplitt.belty.item.ToolBeltItem;
import net.awesomesplitt.belty.registry.MenuRegistries;
import net.awesomesplitt.belty.registry.TagRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ToolBeltMenu extends AbstractContainerMenu {
    final ItemStack belt_item;
    final int tool_slots;
    final int bundle_slots;

    public ToolBeltMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, extraData.readVarInt());
    }

    public ToolBeltMenu(int containerId, Inventory inv, int i) {
        super(MenuRegistries.TOOL_BELT_MENU.get(), containerId);
        this.belt_item = inv.getItem(i);
        this.tool_slots = ToolBeltItem.getToolSlots(this.belt_item, inv.player.level());
        this.bundle_slots = ToolBeltItem.getBundleSlots(this.belt_item, inv.player.level());

        ToolBeltContainer container = new ToolBeltContainer(this.belt_item, this.tool_slots, this.bundle_slots);
        addToolBeltInventory(container);

        addPlayerHotbar(inv);
        addPlayerInventory(inv);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (i < this.tool_slots + this.bundle_slots) {
                if (!this.moveItemStackTo(itemStack2, this.tool_slots + this.bundle_slots, this.slots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 0, this.tool_slots + this.bundle_slots, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getInventory().contains(this.belt_item) || this.belt_item == this.getCarried();
    }

    @Override
    public void setItem(int i, int j, ItemStack item) {
        super.setItem(i, j, item);
    }

    private void addPlayerInventory(Inventory inv) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inv) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inv, i, 8 + i * 18, 142));
        }
    }

    private void addToolBeltInventory(ToolBeltContainer container) {
        int slots = container.getContainerSize();
        int startX = 89 - slots * 9;

        for (int i = 0; i < slots; i++) {
            var slotType = i < container.getToolSlots() ?
                    ToolBeltSlot.Type.TOOL :
                    ToolBeltSlot.Type.BUNDLE;
            this.addSlot(new ToolBeltSlot(slotType, container, i, startX + i * 18, 30));
        }
    }

    public static class ToolBeltSlot extends Slot {
        final Type slotType;

        public ToolBeltSlot(Type type, Container container, int i, int j, int k) {
            super(container, i, j, k);
            this.slotType = type;
        }

        @Override
        public boolean mayPlace(ItemStack itemStack) {
            return switch (this.slotType) {
                case Type.TOOL -> itemStack.is(TagRegistries.Items.TOOLS_BELT_ACCEPTED);
                case Type.BUNDLE -> itemStack.is(TagRegistries.Items.BUNDLES_BELT_ACCEPTED);
            };
        }

        public enum Type {
            TOOL,
            BUNDLE
        }
    }
}
