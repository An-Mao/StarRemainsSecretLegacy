package dev.anye.mc.srsl.reg.enchant.neko.item.nekonight;

import dev.anye.mc.cores.helper.time.TimeHelper;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.neko.item.NekoEI;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class NekoNight extends NekoEI {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("maxLevel",5);
	});
	private final int maxLevel;
	public record Data(int maxLevel) {
		public static final Data DEFAULT = new Data(5);
	}
	public NekoNight(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		maxLevel = config.asInt("maxLevel");
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onAttack);
	}

	public void onAttack(LivingDamageEvent.Pre event) {
			if (event.getSource().getEntity() instanceof LivingEntity livingEntity && !livingEntity.level().isClientSide()) {
				int lvl = EnchantHelper.getEnchantLevel(livingEntity.getMainHandItem(), Enchants.ni_night);
				float damage = 1 + lvl * 0.5F;
				if (TimeHelper.isNight(TimeHelper.getDayTime(livingEntity))) {
					event.setNewDamage(event.getNewDamage() + damage);
				} else {
					event.setNewDamage(event.getNewDamage() - damage);
				}
			}

	}
}
