package net.awesomesplitt.belty.neoforge.util;

import net.awesomesplitt.belty.util.IContainerProvider;
import net.awesomesplitt.belty.util.PlayerInventoryProvider;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.List;
import java.util.Objects;
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
        return optional.map(slotResult -> slotResult.slotContext().index()).orElse(-1);
    }

    @Override
    public NonNullList<PlayerInventoryProvider.SlotResult> getItemByIdentifier(Player player, String identifier) {
        var curios = CuriosApi.getCuriosInventory(player);
        if (curios.isEmpty()) {
            return NonNullList.create();
        }

        List<SlotResult> slots = curios.get().findCurios(identifier);
        int index = 0;
        for (ICurioStacksHandler stacks : curios.get().getCurios().values()) {
            if (stacks.getIdentifier().equals(identifier)) {
                break;
            }
            index += stacks.getSlots();
        }

        final int i = index;
        NonNullList<PlayerInventoryProvider.SlotResult> list = NonNullList.createWithCapacity(slots.size());
        slots.forEach(slot -> list.add(new PlayerInventoryProvider.SlotResult(slot.stack(), slot.slotContext().index() + i, this.id())));
        return list;
    }

    @Override
    public boolean isSlotVisible(Player player, int index) {
        var curios = CuriosApi.getCuriosInventory(player);
        if (curios.isEmpty()) {
            return false;
        }

        for (ICurioStacksHandler stacks : curios.get().getCurios().values()) {
            if (index < stacks.getSlots()) {
                return curios.get().findCurio(stacks.getIdentifier(), index).map(
                        slot -> slot.slotContext().visible()
                ).orElse(false);
            }
            index -= stacks.getSlots();
        }

        return false;
    }
}
