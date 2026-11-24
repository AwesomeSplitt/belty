package net.awesomesplitt.belty.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Predicate;

public final class PlayerInventoryProvider {

    @ExpectPlatform
    public static ItemStack getItem(Player player, int index) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean contains(Player player, ItemStack itemStack) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int findSlotMatching(Player player, Predicate<ItemStack> filter) {
        throw new AssertionError();
    }

    public static int findSlotMatchingItem(Player player, ItemStack itemStack) {
        return findSlotMatching(player,
                itemStack2 -> !itemStack2.isEmpty() && ItemStack.isSameItemSameComponents(itemStack, itemStack2)
        );
    }

    @ExpectPlatform
    public static List<ItemStack> getItemByIdentifier(Player player, String identifier) {
        throw new AssertionError();
    }
}
