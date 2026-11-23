package net.awesomesplitt.belty;

import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.menu.MenuRegistry;
import net.awesomesplitt.belty.registry.KeyMappingRegistries;
import net.awesomesplitt.belty.registry.ModelLayerRegistries;
import net.awesomesplitt.belty.client.ClientEvents;
import net.awesomesplitt.belty.registry.ItemRegistries;
import net.awesomesplitt.belty.network.OpenGuiPayload;
import net.awesomesplitt.belty.registry.MenuRegistries;
import net.awesomesplitt.belty.screen.ToolBeltMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;

public final class Belty {
    public static final String MOD_ID = "belty";

    public static void init() {
        ItemRegistries.register();
        MenuRegistries.register();
        KeyMappingRegistries.register();
        ModelLayerRegistries.register();
        ClientEvents.register();

        NetworkManager.registerReceiver(NetworkManager.c2s(),
                OpenGuiPayload.TYPE, OpenGuiPayload.STREAM_CODEC,
                (payload, context) -> {
                    ServerPlayer player = (ServerPlayer) context.getPlayer();
                    context.queue(() -> {
                        MenuRegistry.openExtendedMenu(player, new SimpleMenuProvider(
                                        (i, inv, p) -> new ToolBeltMenu(i, inv, payload.slot()),
                                        player.getInventory().getItem(payload.slot()).getHoverName()
                            ),
            buf -> buf.writeVarInt(payload.slot())
                        );
                    });
                });
    }
}
