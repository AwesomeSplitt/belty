package net.awesomesplitt.belty.util;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class AccessoriesContainerProvider implements IContainerProvider {
    @Override
    public @NotNull String id() {
        return "accessories";
    }

    @Override
    public int getSize(Player player) {
        return AccessoriesCapability.getOptionally(player).map(c -> c.getContainers().size()).orElse(0);
    }

    @Override
    public ItemStack getItem(Player player, int index) {
        var accessories = AccessoriesCapability.get(player);
        if (accessories == null) {
            return ItemStack.EMPTY;
        }

        for (AccessoriesContainer container : accessories.getContainers().values()) {
            if (index < container.getSize()) {
                return container.getAccessories().getItem(index);
            }
            index -= container.getSize();
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean contains(Player player, ItemStack itemStack) {
        var accessories = AccessoriesCapability.get(player);
        if (accessories == null) {
            return false;
        }

        return accessories.isEquipped(itemStack2 -> !itemStack2.isEmpty() && ItemStack.isSameItemSameComponents(itemStack, itemStack2));
    }

    @Override
    public int findSlotMatching(Player player, Predicate<ItemStack> filter) {
        var accessories = AccessoriesCapability.get(player);
        if (accessories == null) {
            return -1;
        }

        var slot = accessories.getFirstEquipped(filter);
        if (slot == null) {
            return -1;
        }

        int index = 0;
        for (AccessoriesContainer container : accessories.getContainers().values()) {
            if (container == slot.reference().slotContainer()) {
                return index + slot.reference().slot();
            }
            index += container.getSize();
        }

        return -1;
    }
}
