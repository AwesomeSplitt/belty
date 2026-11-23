package net.awesomesplitt.belty.network;

import net.awesomesplitt.belty.Belty;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record OpenGuiPayload(int slot) implements CustomPacketPayload {
    public static final Type<OpenGuiPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Belty.MOD_ID, "open_gui"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenGuiPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, OpenGuiPayload::slot,
                    OpenGuiPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
