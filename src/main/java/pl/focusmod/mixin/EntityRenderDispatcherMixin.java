package pl.focusmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pl.focusmod.FocusMod;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
	@Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
	private <E extends Entity> void focus$hideOthers(E entity, Frustum frustum,
			double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
		if (FocusMod.target != null
				&& entity instanceof PlayerEntity
				&& entity != FocusMod.target
				&& entity != MinecraftClient.getInstance().player) {
			cir.setReturnValue(false);
		}
	}
}
