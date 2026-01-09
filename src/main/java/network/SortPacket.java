package miprimermod.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import miprimermod.InventorySorter;

public record SortPacket() implements CustomPacketPayload {

    public static final Type<SortPacket> TYPE =
            new Type<>(new ResourceLocation("miprimermod", "sort"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SortPacket> CODEC =
            StreamCodec.unit(new SortPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    // CLIENTE → enviar
    public static void send() {
        ClientPlayNetworking.send(new SortPacket());
    }

    // SERVER → recibir
    public static void register() {
        PayloadTypeRegistry.playC2S().register(TYPE, CODEC);

        ServerPlayNetworking.registerGlobalReceiver(TYPE, (payload, context) -> {
            context.player().server.execute(() -> {
                InventorySorter.sort(context.player());
            });
        });
    }
}
