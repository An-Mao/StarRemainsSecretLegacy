package dev.anye.mc.srsl.reg.enchant.spirit.sword.ganjiang;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.spirit.sword.SSE;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class GanJiang extends SSE {
	private final float probability = EnchantsConfig.INSTANCE.getValue(EnchantReg.E_GAN_JIANG, "probability");

	public GanJiang() {
		super();
	}

	@Override
	public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (pAttacker.level() instanceof ServerLevel serverLevel) {
			if (EnchantHelper.hasEnchant(pAttacker.getOffhandItem(), Enchants.oi_mo_ye)) {
				if (_Math.RD.getRandomNumber(1, 100) <= probability) {
					pTarget.kill(serverLevel);
				}
			}
		}
		super.doPostAttack(pAttacker, pTarget, pLevel);
	}
}
