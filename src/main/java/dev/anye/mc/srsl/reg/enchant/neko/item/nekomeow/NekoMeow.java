package dev.anye.mc.srsl.reg.enchant.neko.item.nekomeow;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.neko.item.NekoEI;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class NekoMeow extends NekoEI {

	private static final EnchantConfigData DEFAULT = EnchantConfigData.empty();
	public NekoMeow(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
	}

	@Override
	public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (pAttacker instanceof ServerPlayer serverPlayer) {
			serverPlayer.level().playSound(serverPlayer, serverPlayer.getOnPos(), SoundEvents.CAT_HISS_BABY.value(), SoundSource.PLAYERS);
		}
	}
}