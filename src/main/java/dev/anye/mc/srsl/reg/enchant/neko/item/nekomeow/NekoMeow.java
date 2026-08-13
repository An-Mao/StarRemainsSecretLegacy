package dev.anye.mc.srsl.reg.enchant.neko.item.nekomeow;

import dev.anye.mc.srsl.reg.enchant.neko.item.NekoEI;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class NekoMeow extends NekoEI {
	public NekoMeow() {
		super();
	}

	@Override
	public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (!pAttacker.level().isClientSide()) {
			if (pAttacker instanceof ServerPlayer serverPlayer) {
				serverPlayer.level().playSound(serverPlayer, serverPlayer.getOnPos(), SoundEvents.CAT_HISS_BABY.value(), SoundSource.PLAYERS);
			}
		}
	}
}