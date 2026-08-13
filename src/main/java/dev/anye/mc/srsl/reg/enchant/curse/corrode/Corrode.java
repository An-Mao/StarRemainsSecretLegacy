package dev.anye.mc.srsl.reg.enchant.curse.corrode;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.blood.vampirism.Vampirism;
import dev.anye.mc.srsl.reg.enchant.curse.CurseEnchant;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Corrode extends CurseEnchant {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("probability",10F);
	});
	private final float probability;

	public record Data(float probability) {
		public static final Corrode.Data DEFAULT = new Corrode.Data(10);
	}

	public Corrode(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		probability = config.asFloat("probability");
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
