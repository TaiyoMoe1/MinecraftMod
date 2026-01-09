package miprimermod;

import net.fabricmc.api.ModInitializer;
import miprimermod.network.SortPacket;

public class PruebaMod implements ModInitializer {

	@Override
	public void onInitialize() {
		SortPacket.register();
	}
}
