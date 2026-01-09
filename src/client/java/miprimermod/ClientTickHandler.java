package miprimermod;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import miprimermod.network.SortPacket;

public class ClientTickHandler {

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (KeyInputHandler.SORT_KEY.wasPressed()) {
                if (MinecraftClient.getInstance().player != null) {
                    SortPacket.send();
                }
            }
        });
    }
}
