package dev.anye.mc.srsl.reg.enchant.spirit.sword.deathsickle;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.spirit.sword.SSE;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.villager.Villager;
import org.jetbrains.annotations.NotNull;

public class DeathSickle extends SSE {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("damage",99F);
	});
	private final float damage;
	public record Data(float damage) {
		public static final Data DEFAULT = new Data(99);
	}
	public DeathSickle(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		damage = config.asFloat("damage");
	}

	@Override
	public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (pAttacker.level() instanceof ServerLevel serverLevel) {
			if (pTarget instanceof Villager) {
				pTarget.hurtServer(serverLevel,pAttacker.damageSources().magic(), damage);
			}
		}
		super.doPostAttack(pAttacker, pTarget, pLevel);
	}
}
