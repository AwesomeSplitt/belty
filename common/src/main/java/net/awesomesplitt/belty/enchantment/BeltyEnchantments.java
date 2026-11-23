package net.awesomesplitt.belty.enchantment;

import net.awesomesplitt.belty.Belty;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

public class BeltyEnchantments {
    public static final ResourceKey<Enchantment> CARGO = key("cargo");

    public static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Belty.MOD_ID, name));
    }
}
