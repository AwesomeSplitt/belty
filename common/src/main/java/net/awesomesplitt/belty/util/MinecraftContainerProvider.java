package net.awesomesplitt.belty.util;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class MinecraftContainerProvider implements IContainerProvider {
    @Override
    public @NotNull String id() {
        return "minecraft";
    }

    @Override
    public int getSize(Player player) {
        return player.getInventory().getContainerSize();
    }

    @Override
    public ItemStack getItem(Player player, int index) {
        return player.getInventory().getItem(index);
    }

    @Override
    public boolean contains(Player player, ItemStack itemStack) {
        return player.getInventory().contains(itemStack);
    }

    @Override
    public int findSlotMatching(Player player, Predicate<ItemStack> filter) {
        Inventory inv = player.getInventory();
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack itemStack = inv.getItem(i);
            if (filter.test(itemStack)) {
                return i;
            }
        }
        return -1;
    }
}
