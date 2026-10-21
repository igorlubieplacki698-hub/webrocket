package com.example.webrocket;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FireworksComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WebRocket implements ModInitializer {
	/** Master switch, toggled from the in-game menu and saved to config/webrocket.properties. */
	public static volatile boolean enabled = true;

	/** Player UUID -> world time (ticks) when their firework boost ends. */
	private static final Map<UUID, Long> BOOST_END = new ConcurrentHashMap<>();

	private static Path configPath() {
		return FabricLoader.getInstance().getConfigDir().resolve("webrocket.properties");
	}

	public static void loadConfig() {
		Path p = configPath();
		if (!Files.exists(p)) return;
		try (InputStream in = Files.newInputStream(p)) {
			Properties props = new Properties();
			props.load(in);
			enabled = Boolean.parseBoolean(props.getProperty("enabled", "true"));
		} catch (IOException ignored) {}
	}

	public static void saveConfig() {
		try (OutputStream out = Files.newOutputStream(configPath())) {
			Properties props = new Properties();
			props.setProperty("enabled", Boolean.toString(enabled));
			props.store(out, "Web Rocket");
		} catch (IOException ignored) {}
	}

	@Override
	public void onInitialize() {
		loadConfig();
		UseItemCallback.EVENT.register((player, world, hand) -> {
			ItemStack stack = player.getStackInHand(hand);
			if (stack.isOf(Items.FIREWORK_ROCKET) && player.isFallFlying()) {
				FireworksComponent fw = stack.get(DataComponentTypes.FIREWORKS);
				int flight = fw != null ? fw.flightDuration() : 1;
				// Rocket lifetime is 10 * (flight + 1) + up to ~12 random ticks
				BOOST_END.put(player.getUuid(), world.getTime() + 10L * (flight + 1) + 13L);
			}
			return ActionResult.PASS;
		});
	}

	public static boolean isBoosted(PlayerEntity player) {
		if (!enabled) return false;
		Long end = BOOST_END.get(player.getUuid());
		if (end == null) return false;
		if (player.getWorld().getTime() > end || !player.isFallFlying()) {
			BOOST_END.remove(player.getUuid());
			return false;
		}
		return true;
	}
}
