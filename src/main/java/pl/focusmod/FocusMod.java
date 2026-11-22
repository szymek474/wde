package pl.focusmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class FocusMod implements ClientModInitializer {
	public static PlayerEntity target = null;
	private static KeyBinding exitKey;

	@Override
	public void onInitializeClient() {
		// Prawy klik na gracza = wybierz go
		UseEntityCallback.EVENT.register((player, world, hand, entity, hit) -> {
			if (world.isClient && hand == Hand.MAIN_HAND && target == null
					&& entity instanceof PlayerEntity p && p != player) {
				target = p;
				player.sendMessage(Text.literal("Tryb 1v1 z " + p.getName().getString() + " (R = wyjscie)"), true);
				return ActionResult.SUCCESS;
			}
			return ActionResult.PASS;
		});

		// Blokuj bicie kogokolwiek innego niz wybrany
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hit) -> {
			if (target != null && entity != target) {
				return ActionResult.FAIL;
			}
			return ActionResult.PASS;
		});

		// Klawisz R = wyjdz z trybu
		exitKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.focus.exit", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "category.focus"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (exitKey.wasPressed()) {
				if (target != null && client.player != null) {
					client.player.sendMessage(Text.literal("Wyszedles z trybu 1v1"), true);
				}
				target = null;
			}
			if (target != null && (target.isRemoved() || client.world == null)) {
				target = null;
			}
		});
	}
}
