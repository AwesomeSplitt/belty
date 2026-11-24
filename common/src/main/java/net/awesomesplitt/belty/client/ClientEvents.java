package net.awesomesplitt.belty.client;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.injectables.annotations.PlatformOnly;
import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.menu.MenuRegistry;
import net.awesomesplitt.belty.registry.ItemRegistries;
import net.awesomesplitt.belty.registry.KeyMappingRegistries;
import net.awesomesplitt.belty.network.OpenGuiPayload;
import net.awesomesplitt.belty.registry.MenuRegistries;
import net.awesomesplitt.belty.screen.ToolBeltMenu;
import net.awesomesplitt.belty.screen.ToolBeltScreen;
import net.awesomesplitt.belty.util.PlayerInventoryProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;

public class ClientEvents {
    public static void register() {
        ClientLifecycleEvent.CLIENT_SETUP.register(ClientEvents::registerScreens);
        ClientTickEvent.CLIENT_POST.register(ClientEvents::checkKeybinds);
    }

    private static void registerScreens(Minecraft minecraft) {
        MenuRegistry.registerScreenFactory(MenuRegistries.TOOL_BELT_MENU.get(), ToolBeltScreen::new);
    }

    private static void checkKeybinds(Minecraft minecraft) {
        if (KeyMappingRegistries.OPEN_TOOL_BELT.consumeClick() && minecraft.player != null) {
            tryOpenToolBelt(minecraft);
        }
    }

    private static void tryOpenToolBelt(Minecraft minecraft) {
        Inventory inv = minecraft.player.getInventory();

        if (minecraft.player.containerMenu instanceof ToolBeltMenu) {
            return;
        }

        int slot = PlayerInventoryProvider.findSlotMatching(minecraft.player, item -> item.is(ItemRegistries.TOOL_BELT.get()));
        if (slot != -1) {
            NetworkManager.sendToServer(new OpenGuiPayload(slot));
        }
    }
}
