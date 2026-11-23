package net.awesomesplitt.belty.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;

public class KeyMappingRegistries {
    public static final String CATEGORY_MAIN = "key.categories.belty.main";

    public static final KeyMapping OPEN_TOOL_BELT = new KeyMapping(
            "key.belty.open_tool_belt",          // translation key for the binding's name
            InputConstants.Type.KEYSYM,  // input device
            InputConstants.KEY_B,          // default key
            CATEGORY_MAIN
    );

    public static void register() {
        KeyMappingRegistry.register(OPEN_TOOL_BELT);
    }
}
