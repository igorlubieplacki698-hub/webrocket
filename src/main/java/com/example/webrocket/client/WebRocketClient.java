package com.example.webrocket.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class WebRocketClient implements ClientModInitializer {
	private static KeyBinding menuKey;

	@Override
	public void onInitializeClient() {
		// Default key: = (equals). Can be rebound in Options > Controls.
		menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.webrocket.menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_EQUAL, "category.webrocket"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (menuKey.wasPressed()) {
				if (client.currentScreen == null) {
					client.setScreen(new WebRocketScreen());
				}
			}
		});
	}
}
