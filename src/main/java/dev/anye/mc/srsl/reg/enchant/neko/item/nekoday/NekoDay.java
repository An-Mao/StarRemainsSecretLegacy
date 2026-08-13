package dev.anye.mc.srsl.reg.enchant.neko.item.nekoday;

import dev.anye.mc.cores.helper.time.TimeHelper;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.neko.item.NekoEI;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class NekoDay extends NekoEI {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("maxLevel",5);
	});
	private final int maxLevel;
	public record Data(int maxLevel) {
		public static final Data DEFAULT = new Data(5);
	}
	public NekoDay(EnchantConfigData enchantConfigData) {
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

	public void onAttack(LivingIncomingDamageEvent event) {
			if (event.getSource().getEntity() instanceof LivingEntity livingEntity && !livingEntity.level().isClientSide()) {
				int lvl = EnchantHelper.getEnchantLevel(livingEntity.getMainHandItem(), Enchants.ni_day);
				float damage = 1 + lvl * 0.5F;
				if (TimeHelper.isDay(TimeHelper.getDayTime(livingEntity))) {
					event.setAmount(event.getAmount() + damage);
				} else {
					event.setAmount(event.getAmount() - damage);
				}
			}

	}
}
