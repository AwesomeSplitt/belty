package net.awesomesplitt.belty.util.curios;

import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Predicate;

public class EmptyCuriosHelper implements ICuriosHelper {
    @Override
    public ItemStack getItem(int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean contains(ItemStack itemStack) {
        return false;
    }

    @Override
    public int findSlotMatching(Predicate<ItemStack> filter) {
        return -1;
    }

    @Override
    public int getIndex(String identifier, int i) {
        return -1;
    }

    @Override
    public List<ItemStack> getItemByIdentifier(String identifier) {
        return List.of();
    }
}
