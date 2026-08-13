package dev.anye.mc.srsl.reg.enchant.zero.item.deathproclamation;

import dev.anye.mc.cores.helper.component.ComponentStyle;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.zero.item.ZeroItemE;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.jetbrains.annotations.NotNull;

public class DeathProclamation extends ZeroItemE {
	private static final String DEAD_TIP = "cn.srsl.death_pro";
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("quota",100F);
	});
	private final float quota;
	public record Data(float quota) {
		public static final Data DEFAULT = new Data(100);
	}
	public DeathProclamation(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		quota = config.asFloat("quota");
	}

	@Override
	public void doPostAttack(@NotNull LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
		if (pAttacker instanceof ServerPlayer && pTarget instanceof Mob livingEntity) {
			if (livingEntity.deathTime == 0) {
				livingEntity.deathTime = (int) (livingEntity.getHealth() * -quota);
			}
		}
		super.doPostAttack(pAttacker, pTarget, pLevel);
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onTick);
		modEventBus.addListener(this::onDead);
	}

	public void onTick(EntityTickEvent.Pre event) {
		if (event != null && event.getEntity() instanceof Mob mob) {
			int deathTime = mob.deathTime;
			if (deathTime == -1) {
				if (mob.getLastDamageSource() == null) {
					mob.die(mob.damageSources().fellOutOfWorld());
				} else {
					mob.die(mob.getLastDamageSource());
				}
				event.getEntity().remove(Entity.RemovalReason.KILLED);
			} else if (deathTime < -1) {
				//System.out.println(mob.deathTime);
				mob.deathTime++;
				MutableComponent str = Component.translatable(DEAD_TIP).append(String.valueOf(mob.deathTime));
				mob.setCustomName(ComponentStyle.Flash(str.getString()));
			}
		}
	}

	public void onDead(LivingDeathEvent event) {
		if (event.getEntity() instanceof Mob mob && mob.deathTime < 0) {
			mob.setHealth(mob.getMaxHealth());
			event.setCanceled(true);
		}
	}
}
