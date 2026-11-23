package net.awesomesplitt.belty.event;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.registry.menu.MenuRegistry;
import net.awesomesplitt.belty.client.KeyMappingRegistries;
import net.awesomesplitt.belty.client.model.geom.BeltyModelLayers;
import net.awesomesplitt.belty.item.ToolBeltItem;
import net.awesomesplitt.belty.network.OpenGuiPayload;
import net.awesomesplitt.belty.screen.MenuRegistries;
import net.awesomesplitt.belty.screen.ToolBeltMenu;
import net.awesomesplitt.belty.screen.ToolBeltScreen;
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

        for (int i = 0; i < inv.armor.size(); i++) {
            if (inv.armor.get(i).getItem() instanceof ToolBeltItem) {
                NetworkManager.sendToServer(new OpenGuiPayload(i + inv.items.size()));
                return;
            }
        }

        for (int i = 0; i < inv.offhand.size(); i++) {
            if (inv.offhand.get(i).getItem() instanceof ToolBeltItem) {
                NetworkManager.sendToServer(new OpenGuiPayload(i + inv.items.size() + inv.armor.size()));
                return;
            }
        }

        for (int i = 0; i < inv.items.size(); i++) {
            if (inv.items.get(i).getItem() instanceof ToolBeltItem) {
                NetworkManager.sendToServer(new OpenGuiPayload(i));
                return;
            }
        }
    }
}
