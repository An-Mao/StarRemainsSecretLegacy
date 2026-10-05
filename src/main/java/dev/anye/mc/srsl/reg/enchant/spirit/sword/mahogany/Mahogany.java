package dev.anye.mc.srsl.reg.enchant.spirit.sword.mahogany;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.spirit.sword.SSE;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.zombie.Zombie;
import org.jetbrains.annotations.NotNull;

public class Mahogany extends SSE {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("damage",9F);
	});
	private final float damage;
	public record Data(float damage) {
		public static final Data DEFAULT = new Data(9);
	}
	public Mahogany(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		damage = config.asFloat("damage");
	}

	@Override
	public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (pAttacker.level() instanceof ServerLevel serverLevel) {
			if (pTarget instanceof Zombie zombie) {
				zombie.hurtServer(serverLevel,pAttacker.damageSources().fellOutOfWorld(), damage);
			}
		}
		super.doPostAttack(pAttacker, pTarget, pLevel);
	}
}
