package net.awesomesplitt.belty.neoforge;

import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.menu.MenuRegistry;
import net.awesomesplitt.belty.client.renderer.ToolBeltLayer;
import net.awesomesplitt.belty.network.OpenGuiPayload;
import net.awesomesplitt.belty.registry.MenuRegistries;
import net.awesomesplitt.belty.screen.ToolBeltMenu;
import net.awesomesplitt.belty.screen.ToolBeltScreen;
import net.awesomesplitt.belty.util.PlayerInventoryProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import net.awesomesplitt.belty.Belty;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(Belty.MOD_ID)
public final class BeltyNeoforge {
    public BeltyNeoforge() {
        // Run our common setup.
        Belty.init();
    }

    @EventBusSubscriber(modid = Belty.MOD_ID, value = Dist.CLIENT)
    public static class ClientEvents {

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(MenuRegistries.TOOL_BELT_MENU.get(), ToolBeltScreen::new);
        }

        @SubscribeEvent
        public static void addRenderLayers(EntityRenderersEvent.AddLayers event) {
            for (PlayerSkin.Model model : event.getSkins()) {
                PlayerRenderer renderer = event.getSkin(model);

                if (renderer != null) {
                    renderer.addLayer(new ToolBeltLayer<>(renderer, event.getEntityModels()));
                }
            }
        }
    }
}
