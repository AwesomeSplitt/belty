package net.awesomesplitt.belty.util.fabric;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Predicate;

public final class PlayerInventoryProviderImpl {
    public static ItemStack getItem(Player player, int index) {
        return player.getInventory().getItem(index);
    }

    public static boolean contains(Player player, ItemStack itemStack) {
        return player.getInventory().contains(itemStack);
    }

    public static int findSlotMatching(Player player, Predicate<ItemStack> filter) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (filter.test(itemStack)) {
                return i;
            }
        }

        return -1;
    }

    public static List<ItemStack> getItemByIdentifier(Player player, String identifier) {
        return switch (identifier) {
            case "feet" -> List.of(player.getItemBySlot(EquipmentSlot.FEET));
            case "legs" -> List.of(player.getItemBySlot(EquipmentSlot.LEGS));
            case "chest" -> List.of(player.getItemBySlot(EquipmentSlot.CHEST));
            case "head" -> List.of(player.getItemBySlot(EquipmentSlot.HEAD));
            default -> List.of();
        };
    }
}
