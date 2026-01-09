package miprimermod.network;

import miprimermod.InventorySorter;
import miprimermod.PruebaMod;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record SortPacket() implements CustomPacketPayload {

    public static final Type<SortPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(
                    PruebaMod.MOD_ID, "sort_items"
            ));

    public static final StreamCodec<FriendlyByteBuf, SortPacket> CODEC =
            StreamCodec.unit(new SortPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void register() {

        PayloadTypeRegistry.playC2S().register(TYPE, CODEC);

        ServerPlayNetworking.registerGlobalReceiver(
                TYPE,
                (packet, context) -> {

                    ServerPlayer player = context.player();

                    // 🔥 FORMA CORRECTA EN 1.21.1
                    player.server.execute(() -> {
                        InventorySorter.sortNearbyChests(player);
                    });
                }
        );
    }
}
