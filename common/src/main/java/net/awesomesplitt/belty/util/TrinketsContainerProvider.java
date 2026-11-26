package net.awesomesplitt.belty.util;

import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class TrinketsContainerProvider implements IContainerProvider {
    @Override
    public @NotNull String id() {
        return "trinkets";
    }

    @Override
    public int getSize(Player player) {
        var component = TrinketsApi.getTrinketComponent(player);
        if (component.isEmpty()) {
            return 0;
        }

        AtomicInteger i = new AtomicInteger();
        component.get().forEach((a,b) -> i.getAndIncrement());
        return i.get();
    }

    @Override
    public ItemStack getItem(Player player, int index) {
        var component = TrinketsApi.getTrinketComponent(player);
        if (component.isEmpty()) {
            return ItemStack.EMPTY;
        }

        AtomicReference<ItemStack> result = new AtomicReference<>(ItemStack.EMPTY);
        AtomicInteger i = new AtomicInteger(index);

        component.get().forEach((slot, stack) -> {
            if (i.get() == 0) {
                result.set(stack);
            }
            i.getAndDecrement();
        });

        return result.get();
    }

    @Override
    public boolean contains(Player player, ItemStack itemStack) {
        var component = TrinketsApi.getTrinketComponent(player);
        if (component.isEmpty()) {
            return false;
        }

        return component.get().isEquipped(itemStack2 -> !itemStack2.isEmpty() && ItemStack.isSameItemSameComponents(itemStack, itemStack2));
    }

    @Override
    public int findSlotMatching(Player player, Predicate<ItemStack> filter) {
        var component = TrinketsApi.getTrinketComponent(player);
        if (component.isEmpty()) {
            return -1;
        }

        AtomicInteger index = new AtomicInteger(-1);
        component.get().forEach((slot, stack) -> {
            if (filter.test(stack) && index.get() == -1) {
                index.set(slot.index());
            }
        });

        return index.get();
    }
}
