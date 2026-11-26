package net.awesomesplitt.belty.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public final class PlayerInventoryProvider {
    private static final NonNullList<IContainerProvider> CONTAINER_PROVIDERS = NonNullList.create();

    public static boolean register(IContainerProvider provider) {
        if (CONTAINER_PROVIDERS.stream().anyMatch(p -> p.id() == provider.id())) {
            return false;
        }

        return CONTAINER_PROVIDERS.add(provider);
    }

    public static IContainerProvider getProvider(String id) {
        for (IContainerProvider provider : CONTAINER_PROVIDERS) {
            if (provider.id() == id) {
                return provider;
            }
        }
        throw new NullPointerException("No IContainerProvider of the id `" + id + "` is registered.");
    }

    public static int getIndexOffset(String id, Player player) {
        int index = 0;
        for (IContainerProvider provider : CONTAINER_PROVIDERS) {
            if (provider.id() == id) {
                return index;
            }
            index += provider.getSize(player);
        }
        return -1;
    }

    public static ItemStack getItem(Player player, int index) {
        for (IContainerProvider provider : CONTAINER_PROVIDERS) {
            if (index < provider.getSize(player)) {
                return provider.getItem(player, index);
            }
            index -= provider.getSize(player);
        }
        return ItemStack.EMPTY;
    }

    public static boolean contains(Player player, ItemStack itemStack) {
        for (IContainerProvider provider : CONTAINER_PROVIDERS) {
            if (provider.contains(player, itemStack)) {
                return true;
            }
        }
        return false;
    }

    public static int findSlotMatching(Player player, Predicate<ItemStack> filter) {
        for (IContainerProvider provider : CONTAINER_PROVIDERS) {
            int slot = provider.findSlotMatching(player, filter);
            if (slot != -1) {
                return slot + getIndexOffset(provider.id(), player);
            }
        }
        return -1;
    }

    public static int findSlotMatchingItem(Player player, ItemStack itemStack) {
        return findSlotMatching(player,
                itemStack2 -> !itemStack2.isEmpty() && ItemStack.isSameItemSameComponents(itemStack, itemStack2)
        );
    }
}
