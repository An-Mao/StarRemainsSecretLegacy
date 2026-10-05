package dev.anye.mc.srsl.reg.enchant.neko.armor.nekoninja;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.neko.armor.NekoEA;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class NekoNinja extends NekoEA {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("maxLevel",3);
	});
	private final int maxLevel;


	public record Data(int maxLevel) {
		public static final Data DEFAULT = new Data(3);
	}
	public NekoNinja(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		maxLevel = config.asInt("maxLevel");
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
