package dev.anye.mc.srsl.reg.enchant.zero.item.deathproclamation;

import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.zero.item.ZeroItemE;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;

public class DeathProclamation extends ZeroItemE {
	public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.Z_DP);
	private final float quota = EnchantsConfig.INSTANCE.getValue(EnchantReg.Z_DP, "quota");

	public DeathProclamation() {
		super();
	}

	@Override
	public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (pAttacker instanceof ServerPlayer) {
			if (pTarget instanceof Mob livingEntity) {
				if (livingEntity.deathTime == 0) {
					livingEntity.deathTime = (int) (livingEntity.getHealth() * -quota);
				}
			}
		}
		super.doPostAttack(pAttacker, pTarget, pLevel);
	}
}
