package net.awesomesplitt.belty.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public interface IContainerProvider {
    @NotNull
    String id();

    int getSize(Player player);

    ItemStack getItem(Player player, int index);

    boolean contains(Player player, ItemStack itemStack);

    int findSlotMatching(Player player, Predicate<ItemStack> filter);

    NonNullList<PlayerInventoryProvider.SlotResult> getItemByIdentifier(Player player, String identifier);

    default boolean isSlotVisible(Player player, int index) {
        return true;
    }
}
