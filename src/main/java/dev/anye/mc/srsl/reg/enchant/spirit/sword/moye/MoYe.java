package dev.anye.mc.srsl.reg.enchant.spirit.sword.moye;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.spirit.sword.SSE;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class MoYe extends SSE {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("probability",10F);
	});
	private final float probability;
	public record Data(float probability) {
		public static final Data DEFAULT = new Data(10);
	}
	public MoYe(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		probability = config.asFloat("probability");
	}

	@Override
	public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (pAttacker.level() instanceof ServerLevel serverLevel) {
			if (EnchantHelper.hasEnchant(pAttacker.getOffhandItem(), Enchants.oi_gan_jiang)) {
				if (_Math.RD.getRandomNumber(1, 100) <= probability) {
					pTarget.kill(serverLevel);
				}
			}
		}
		super.doPostAttack(pAttacker, pTarget, pLevel);
	}
}
