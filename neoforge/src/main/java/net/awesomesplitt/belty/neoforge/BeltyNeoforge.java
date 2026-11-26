package net.awesomesplitt.belty.neoforge;

import dev.architectury.platform.Platform;
import net.awesomesplitt.belty.client.renderer.ToolBeltLayer;
import net.awesomesplitt.belty.neoforge.client.renderer.integration.curios.ToolBeltCuriosRenderer;
import net.awesomesplitt.belty.neoforge.util.CuriosContainerProvider;
import net.awesomesplitt.belty.registry.MenuRegistries;
import net.awesomesplitt.belty.screen.ToolBeltScreen;
import net.awesomesplitt.belty.util.PlayerInventoryProvider;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import net.awesomesplitt.belty.Belty;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(Belty.MOD_ID)
public final class BeltyNeoforge {
    public BeltyNeoforge() {
        // Run our common setup.
        Belty.init();
        registerCuriosProvider();
    }

    private static void registerCuriosProvider() {
        if (ModList.get().isLoaded("curios")) {
            PlayerInventoryProvider.register(new CuriosContainerProvider());
        }
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

            Belty.registerAccessories(event.getEntityModels());
            ToolBeltCuriosRenderer.createModel(event.getEntityModels());
        }

        @SubscribeEvent
        public static void registerCuriosRenderer(FMLClientSetupEvent event) {
            if (Platform.isModLoaded("curios")) {
                ToolBeltCuriosRenderer.register();
            }
        }
    }
}
