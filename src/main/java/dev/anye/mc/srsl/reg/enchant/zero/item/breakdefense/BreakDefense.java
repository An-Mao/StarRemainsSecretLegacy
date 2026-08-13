package dev.anye.mc.srsl.reg.enchant.zero.item.breakdefense;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.effect.Effects;
import dev.anye.mc.srsl.reg.enchant.zero.item.ZeroItemE;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class BreakDefense extends ZeroItemE {
	private final float probability = EnchantsConfig.INSTANCE.getValue(EnchantReg.Z_BREAK_DEFENSE, "probability");
	private static final MobEffectInstance BreakDefense = new MobEffectInstance(Effects.BREAK_DEFENSE.getDelegate(), 1, 200);

	public BreakDefense() {
		super();
	}

	@Override
	public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (pAttacker instanceof ServerPlayer) {
			if (pTarget instanceof LivingEntity livingEntity) {
				if (_Math.RD.getIntRandomNumber(1, 100) < pLevel * probability) {
					livingEntity.addEffect(BreakDefense);
				}
			}
		}
		super.doPostAttack(pAttacker, pTarget, pLevel);
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}
}
