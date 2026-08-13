package dev.anye.mc.srsl.reg.enchant.neko.armor.nekoninja;

import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.neko.armor.NekoEA;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class NekoNinja extends NekoEA {
	private final int maxLevel = EnchantsConfig.INSTANCE.getMaxLevel(EnchantReg.NEKO_NINJA);

	public NekoNinja() {
		super();
	}

	@Override
	public void doPostHurt(LivingEntity pTarget, @NotNull Entity pAttacker, int pLevel) {
		if (!pTarget.level().isClientSide()) {
			pTarget.addEffect(new MobEffectInstance(MobEffects.STRENGTH, pLevel * 20, pLevel));
			pTarget.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, pLevel * 20, pLevel));

		}
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}
}
