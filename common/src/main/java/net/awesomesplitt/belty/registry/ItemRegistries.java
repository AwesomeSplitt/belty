package net.awesomesplitt.belty.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.awesomesplitt.belty.Belty;
import net.awesomesplitt.belty.item.ToolBeltItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public class ItemRegistries {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Belty.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<ToolBeltItem> TOOL_BELT = ITEMS.register("tool_belt", () -> {
        return new ToolBeltItem(new Item.Properties().stacksTo(1).arch$tab(CreativeModeTabs.TOOLS_AND_UTILITIES));
    });

    public static void register() {
        ITEMS.register();
    }

    public static RegistrySupplier<? extends Item> item(String id, Item.Properties properties) {
        return ITEMS.register(id, () -> {
            return new Item(properties);
        });
    }

    public static RegistrySupplier<? extends Item> item(String id) {
        return item(id, new Item.Properties());
    }
}
