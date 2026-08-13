package dev.anye.mc.srsl.reg.enchant.curse.disease;

import dev.anye.mc.cores.helper.effect.EffectHelper;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.curse.CurseEnchant;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class CurseDisease extends CurseEnchant {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.empty();

	public CurseDisease(EnchantConfigData enchantConfigData){
		super(enchantConfigData,DEFAULT);
	}

	@Override
	public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		pAttacker.addEffect(EffectHelper.getDeBuffInstance(100, 3));
		super.doPostAttack(pAttacker, pTarget, pLevel);
	}

	@Override
	public void doPostHurt(LivingEntity pTarget, @NotNull Entity pAttacker, int pLevel) {
		pTarget.addEffect(EffectHelper.getDeBuffInstance(100, 3));
		super.doPostHurt(pTarget, pAttacker, pLevel);
	}

}
