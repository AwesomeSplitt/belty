package net.awesomesplitt.belty.tags;

import net.awesomesplitt.belty.Belty;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class BeltyItemTags {
    public static TagKey<Item> TOOLS_BELT_ACCEPTED = bind("belt_accepted/tools");
    public static TagKey<Item> BUNDLES_BELT_ACCEPTED = bind("belt_accepted/bundles");
    public static TagKey<Item> BELT_ENCHANTABLE = bind("enchantable/belt");

    public static TagKey<Item> bind(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Belty.MOD_ID, name));
    }
}
