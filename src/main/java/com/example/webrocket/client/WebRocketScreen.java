package com.example.webrocket.client;

import com.example.webrocket.WebRocket;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class WebRocketScreen extends Screen {
	private ButtonWidget toggleButton;

	public WebRocketScreen() {
		super(Text.translatable("webrocket.screen.title"));
	}

	private Text toggleText() {
		return Text.translatable(WebRocket.enabled ? "webrocket.button.on" : "webrocket.button.off");
	}

	@Override
	protected void init() {
		int cx = this.width / 2;
		int cy = this.height / 2;

		toggleButton = ButtonWidget.builder(toggleText(), button -> {
			WebRocket.enabled = !WebRocket.enabled;
			WebRocket.saveConfig();
			button.setMessage(toggleText());
		}).dimensions(cx - 100, cy - 10, 200, 20).build();
		this.addDrawableChild(toggleButton);

		this.addDrawableChild(ButtonWidget.builder(Text.translatable("webrocket.button.done"), b -> this.close())
				.dimensions(cx - 100, cy + 18, 200, 20).build());
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);
		context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 36, 0xFFFFFF);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		// Press = again to close the menu
		if (keyCode == GLFW.GLFW_KEY_EQUAL) {
			this.close();
			return true;
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}
}
