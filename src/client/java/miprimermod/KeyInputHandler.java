package miprimermod;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    public static KeyBinding SORT_KEY;

    public static void register() {
        SORT_KEY = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "Send items to nearby chest",
                        GLFW.GLFW_KEY_K,
                        "Auto Chest Fill"
                )
        );
    }
}