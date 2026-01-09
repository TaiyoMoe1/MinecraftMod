package miprimermod;

import net.fabricmc.api.ClientModInitializer;

public class PruebaModClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		KeyInputHandler.register();
		ClientTickHandler.register();
	}
}
