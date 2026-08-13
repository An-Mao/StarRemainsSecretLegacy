package dev.anye.mc.srsl.reg.enchant.spirit.sword.zenstick;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.effect.Effects;
import dev.anye.mc.srsl.reg.enchant.spirit.sword.SSE;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import org.jetbrains.annotations.NotNull;

public class ZenStick extends SSE {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.empty();
	public ZenStick(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
	}
	@Override
	public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (!pAttacker.level().isClientSide() && pTarget instanceof Mob mob) {
			if (mob instanceof Monster) {
				int i = 1;
				for (; i < 18; i++) {
					String tag = "chaodulv" + i;
					if (!pTarget.entityTags().contains(tag)) {
						pTarget.addTag(tag);
						break;
					}
				}
				if (i == 17) {
					pTarget.kill((ServerLevel) pAttacker.level());
				}
			} else {
				mob.addEffect(new MobEffectInstance(Effects.YAN_LING.getDelegate(), 200, 1));
			}
		}
		super.doPostAttack(pAttacker, pTarget, pLevel);
	}
}
