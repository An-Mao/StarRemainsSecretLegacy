package dev.anye.mc.srsl.reg.enchant.zero.item.confusion;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.zero.item.ZeroItemE;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Confusion extends ZeroItemE {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("radius",30F);
	});
	private final float radius;
	public record Data(float radius) {
		public static final Data DEFAULT = new Data(30);
	}
	public Confusion(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		radius = config.asFloat("radius");
	}

	@Override
	public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (pAttacker instanceof ServerPlayer && pTarget instanceof Mob mob) {
			List<Entity> e = mob.level().getEntities(mob, mob.getBoundingBox().inflate(radius));
			Mob tar = null;
			double d = -1, td;
			for (Entity te : e) {
				if (te instanceof Mob m && m != mob) {
					td = m.getEyePosition().distanceTo(pAttacker.getEyePosition());
					if (d == -1) {
						d = td;
					} else if (td < d) {
						d = td;
						tar = m;
					}
				}
			}
			mob.setTarget(tar);
		}
		super.doPostAttack(pAttacker, pTarget, pLevel);
	}
}
