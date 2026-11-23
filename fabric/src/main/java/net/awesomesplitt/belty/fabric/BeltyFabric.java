package net.awesomesplitt.belty.fabric;

import net.fabricmc.api.ModInitializer;

import net.awesomesplitt.belty.Belty;

public final class BeltyFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        Belty.init();
    }
}
