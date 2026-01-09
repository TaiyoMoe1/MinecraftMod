package miprimermod;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    public static KeyMapping SORT_KEY;

    public static void register() {
        SORT_KEY = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.pruebamod.sort",
                        InputConstants.Type.KEYSYM, // ✅ CAMBIO CLAVE
                        GLFW.GLFW_KEY_K,
                        "category.pruebamod"
                )
        );
    }
}
