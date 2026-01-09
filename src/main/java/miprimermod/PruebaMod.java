package miprimermod;

import miprimermod.network.SortPacket;
import net.fabricmc.api.ModInitializer;

public class PruebaMod implements ModInitializer {

	public static final String MOD_ID = "prueba-mod"; // 🔥 FALTABA ESTO

	@Override
	public void onInitialize() {
		SortPacket.register();
	}
}
