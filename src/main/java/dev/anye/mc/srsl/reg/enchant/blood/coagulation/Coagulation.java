package dev.anye.mc.srsl.reg.enchant.blood.coagulation;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.blood.BloodEnchant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class Coagulation extends BloodEnchant {
	private static final String KEY = "coagulation";
	private static final String KEY_HEALTH = "coagulationHealth";

	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("timeout",1200L);
	});

	private final long timeout;

	public Coagulation(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		timeout = config.asLong("timeout");
	}

	@Override
	public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
		super.doPostAttack(pAttacker, pTarget, pLevel);
		if (pTarget instanceof LivingEntity livingEntity && livingEntity.level() instanceof ServerLevel serverLevel) {
			CompoundTag dat = livingEntity.getPersistentData();
			long gt = serverLevel.getGameTime();
			dat.putLong(KEY, gt);
			dat.putFloat(KEY_HEALTH, livingEntity.getHealth());
		}
	}


	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onHeal);
		modEventBus.addListener(this::onTick);
	}

	public void onHeal(LivingHealEvent event) {
		if (event.getEntity() instanceof LivingEntity livingEntity && livingEntity.level() instanceof ServerLevel serverLevel){
			CompoundTag dat = livingEntity.getPersistentData();
			long saveTime = dat.getLong(KEY).orElse(0L);
			if (saveTime > 0 && serverLevel.getGameTime() - saveTime < timeout) {
				livingEntity.hurtServer(
						serverLevel,
						livingEntity.getLastDamageSource() != null ? livingEntity.getLastDamageSource(): livingEntity.damageSources().fellOutOfWorld(),
						event.getAmount());
				event.setCanceled(true);
			}
		}

	}

	public void onTick(EntityTickEvent.Pre event) {
		if (event.getEntity() instanceof LivingEntity livingEntity && livingEntity.level() instanceof ServerLevel serverLevel) {
			CompoundTag dat = livingEntity.getPersistentData();
			long saveTime = dat.getLong(KEY).orElse(0L);
			if (saveTime > 0 && serverLevel.getGameTime() - saveTime < timeout) {
				float oldHealth = dat.getFloat(KEY_HEALTH).orElse(0f);
				float a = livingEntity.getHealth() - oldHealth;
				if (a > 0) {
					a = oldHealth - a * 2;
					dat.putFloat(KEY_HEALTH, a);
					livingEntity.setHealth(Math.max(1, a));
				}
			}
		}
	}

	public record Data(long timeout){
		public static final Data DEFAULT = new Data(1200);

	}
}
