package net.awesomesplitt.belty.screen;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.awesomesplitt.belty.Belty;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;

public class MenuRegistries {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Belty.MOD_ID, Registries.MENU);

    public static final RegistrySupplier<MenuType<ToolBeltMenu>> TOOL_BELT_MENU =
            MENUS.register("tool_belt_menu", () -> MenuRegistry.ofExtended(ToolBeltMenu::new));

    public static void register() {
        MENUS.register();
    }
}
