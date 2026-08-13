package dev.anye.mc.srsl.reg.enchant.zero.item.breakdefense;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.effect.Effects;
import dev.anye.mc.srsl.reg.enchant.zero.item.ZeroItemE;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class BreakDefense extends ZeroItemE {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("probability",12F);
	});
	private final float probability;
	public record Data(float probability) {
		public static final Data DEFAULT = new Data(12);
	}
	public BreakDefense(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		probability = config.asFloat("probability");
	}

	@Override
	public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (pAttacker instanceof ServerPlayer) {
			if (pTarget instanceof LivingEntity livingEntity) {
				if (_Math.RD.getIntRandomNumber(1, 100) < pLevel * probability) {
					livingEntity.addEffect(new MobEffectInstance(Effects.BREAK_DEFENSE.getDelegate(), 200, 1));
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
