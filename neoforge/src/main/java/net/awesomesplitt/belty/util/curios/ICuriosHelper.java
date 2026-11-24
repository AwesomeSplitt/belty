package net.awesomesplitt.belty.util.curios;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

import java.util.List;
import java.util.function.Predicate;

public interface ICuriosHelper {
    static ICuriosHelper get(LivingEntity entity) {
        return ModList.get().isLoaded("curios") ?
                new net.awesomesplitt.belty.util.curios.CuriosHelper(entity) :
                new net.awesomesplitt.belty.util.curios.EmptyCuriosHelper();
    }

    ItemStack getItem(int index);

    boolean contains(ItemStack itemStack);

    int findSlotMatching(Predicate<ItemStack> filter);

    int getIndex(String identifier, int i);

    List<ItemStack> getItemByIdentifier(String identifier);
}
