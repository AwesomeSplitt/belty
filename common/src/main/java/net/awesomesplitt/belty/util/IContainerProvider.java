package net.awesomesplitt.belty.util;

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
}
