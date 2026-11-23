package net.awesomesplitt.belty.item;

import dev.architectury.registry.menu.MenuRegistry;
import net.awesomesplitt.belty.registry.EnchantmentRegistries;
import net.awesomesplitt.belty.screen.ToolBeltMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ToolBeltItem extends Item implements Equipable {
    public ToolBeltItem(Properties properties) {
        super(properties);
    }

    @NotNull
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.LEGS;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack item = player.getItemInHand(interactionHand);

        if (player.isCrouching()) {
            return this.swapWithEquipmentSlot(this, level, player, interactionHand);
        }

        if (!level.isClientSide) {
            MenuRegistry.openExtendedMenu((ServerPlayer) player,
                    new SimpleMenuProvider(
                            (i, inv, p) -> new ToolBeltMenu(i, inv, inv.findSlotMatchingItem(item)),
                            item.getHoverName()
                    ),
                    buf -> buf.writeVarInt(player.getInventory().findSlotMatchingItem(item))
            );
        }

        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    public boolean canFitInsideContainerItems() {
        return false;
    }

    public boolean isEnchantable(ItemStack item) {
        return true;
    }

    public int getEnchantmentValue() {
        return 1;
    }

    public static int getCargoLevel(ItemStack item, Level level) {
        var enchantment = level.holderLookup(Registries.ENCHANTMENT).getOrThrow(EnchantmentRegistries.CARGO);
        return item.getEnchantments().getLevel(enchantment);
    }

    public static int getToolSlots(ItemStack item, Level level) {
        return switch (getCargoLevel(item, level)) {
            case 0 -> 2;
            case 1, 2 -> 3;
            default -> 4;
        };
    }
    public static int getBundleSlots(ItemStack item, Level level) {
        return switch (getCargoLevel(item, level)) {
            case 0, 1 -> 3;
            default -> 5;
        };
    }
}
