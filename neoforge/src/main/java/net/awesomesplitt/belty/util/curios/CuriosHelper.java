package net.awesomesplitt.belty.util.curios;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CuriosHelper implements ICuriosHelper {
    private final LivingEntity entity;

    public CuriosHelper(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public ItemStack getItem(int index) {
        var curios = CuriosApi.getCuriosInventory(this.entity);
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
    public boolean contains(ItemStack itemStack) {
        var curios = CuriosApi.getCuriosInventory(this.entity);
        if (curios.isEmpty()) {
            return false;
        }

        return curios.get().findFirstCurio(item -> !item.isEmpty() && ItemStack.isSameItemSameComponents(item, itemStack)).isPresent();
    }

    @Override
    public int findSlotMatching(Predicate<ItemStack> filter) {
        var curios = CuriosApi.getCuriosInventory(this.entity);
        if (curios.isEmpty()) {
            return -1;
        }

        var slot = curios.get().findFirstCurio(filter);
        if (slot.isEmpty()) {
            return -1;
        }

        var context = slot.get().slotContext();
        return getIndex(context.identifier(), context.index());
    }

    @Override
    public int getIndex(String identifier, int i) {
        var curios = CuriosApi.getCuriosInventory(this.entity);
        if (curios.isEmpty()) {
            return -1;
        }

        int index = 0;
        for (String ident : curios.get().getCurios().keySet()) {
            if (ident == identifier) {
                return index + i;
            }
            index += curios.get().getStacksHandler(ident).get().getSlots();
        }

        return -1;
    }

    @Override
    public List<ItemStack> getItemByIdentifier(String identifier) {
        var curios = CuriosApi.getCuriosInventory(this.entity);
        if (curios.isEmpty()) {
            return List.of();
        }

        var stacks = curios.get().getStacksHandler(identifier);
        if (stacks.isEmpty()) {
            return List.of();
        }

        List<ItemStack> list = new ArrayList<>(stacks.get().getSlots());
        for (int i = 0; i < stacks.get().getSlots(); i++) {
            list.add(stacks.get().getStacks().getStackInSlot(i));
        }

        return list;
    }


}
