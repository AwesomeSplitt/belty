package net.awesomesplitt.belty.neoforge.util;

import net.awesomesplitt.belty.util.IContainerProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;
import java.util.function.Predicate;

public class CuriosContainerProvider implements IContainerProvider {
    @Override
    public @NotNull String id() {
        return "curios";
    }

    @Override
    public int getSize(Player player) {
        var curios = CuriosApi.getCuriosInventory(player);
        return curios.map(ICuriosItemHandler::getSlots).orElse(0);
    }

    @Override
    public ItemStack getItem(Player player, int index) {
        var curios = CuriosApi.getCuriosInventory(player);
        if (curios.isEmpty()) {
            return ItemStack.EMPTY;
        }

        for (ICurioStacksHandler stacks : curios.get().getCurios().values()) {
            if (index < stacks.getSlots()) {
                return stacks.getStacks().getStackInSlot(index);
            }
            index -= stacks.getSlots();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean contains(Player player, ItemStack itemStack) {
        var curios = CuriosApi.getCuriosInventory(player);
        if (curios.isEmpty()) {
            return false;
        }

        return curios.get().isEquipped(itemStack2 -> !itemStack2.isEmpty() && ItemStack.isSameItemSameComponents(itemStack, itemStack2));
    }

    @Override
    public int findSlotMatching(Player player, Predicate<ItemStack> filter) {
        var curios = CuriosApi.getCuriosInventory(player);
        if (curios.isEmpty()) {
            return -1;
        }

        Optional<SlotResult> optional = curios.get().findFirstCurio(filter);
        if (optional.isEmpty()) {
            return -1;
        }

        SlotContext context = optional.get().slotContext();
        int i = 0;
        for (ICurioStacksHandler stacks : curios.get().getCurios().values()) {
            if (stacks.getIdentifier().equals(context.identifier())) {
                return context.index() + i;
            }
            i += stacks.getSlots();
        }
        return -1;
    }
}
