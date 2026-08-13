package dev.anye.mc.srsl.reg.enchant.curse.corrode;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.curse.CurseEnchant;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Corrode extends CurseEnchant {
	private final float probability = EnchantsConfig.INSTANCE.getValue(EnchantReg.CORRODE, "probability");

	public Corrode() {
	}

	@Override
	public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (pAttacker instanceof ServerPlayer) {
			if (_Math.RD.getIntRandomNumber(1, 100) < pLevel * probability) {
				ItemStack item = pAttacker.getMainHandItem();
				int damage = Math.max(1, (item.getMaxDamage() - item.getDamageValue()) / 2);
				damage += item.getDamageValue();
				item.setDamageValue(Math.min(damage, item.getMaxDamage()));

			}
		}
		super.doPostAttack(pAttacker, pTarget, pLevel);
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}
}
