package com.example.webrocket.mixin;

import com.example.webrocket.WebRocket;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
	// Cobwebs (and similar) call this to slow the entity down.
	@Inject(method = "slowMovement", at = @At("HEAD"), cancellable = true)
	private void webrocket$ignoreWebWhileBoosted(BlockState state, Vec3d multiplier, CallbackInfo ci) {
		if ((Object) this instanceof PlayerEntity player && WebRocket.isBoosted(player)) {
			ci.cancel();
		}
	}
}
