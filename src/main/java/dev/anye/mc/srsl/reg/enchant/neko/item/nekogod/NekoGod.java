package dev.anye.mc.srsl.reg.enchant.neko.item.nekogod;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.neko.item.NekoEI;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class NekoGod extends NekoEI {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data
				.add("probability",2F)
				.add("level",500F);
	});
	private final float probability;
	private final float lvl;
	public record Data(float probability,float level) {
		public static final Data DEFAULT = new Data(2,500);
	}
	public NekoGod(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		probability = config.asFloat("probability");
		lvl = config.asFloat("level");
	}

	@Override
	public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (pAttacker instanceof ServerPlayer serverPlayer && pTarget instanceof LivingEntity mob && !(pTarget instanceof ServerPlayer)) {
			float p = pLevel * probability;
			//0-100
			p += _Math.log2Floor((int) serverPlayer.getLuck());
			if (p > _Math.RD.getIntRandomNumber(1, 100)) {
				//32767
				p = Math.max(0.0F, mob.getHealth() - mob.getMaxHealth() * (serverPlayer.experienceLevel / lvl) * pLevel);
				if (p == 0.0F) {
					mob.setHealth(0.00001F);
					mob.die(serverPlayer.damageSources().genericKill());
					mob.remove(Entity.RemovalReason.KILLED);
				} else {
					mob.setHealth(p);
				}
			}
		}

	}

	@Override
	public int getMaxLevel() {
		return 3;
	}
}
