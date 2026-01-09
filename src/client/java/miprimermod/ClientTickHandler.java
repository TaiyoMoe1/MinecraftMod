package miprimermod;

import miprimermod.network.SortPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;

public class ClientTickHandler {

    public static void register() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (KeyInputHandler.SORT_KEY.consumeClick()) {

                if (client.player == null) return;
                // hola putita

                ClientPlayNetworking.send(new SortPacket());
            }
        });
    }
}
