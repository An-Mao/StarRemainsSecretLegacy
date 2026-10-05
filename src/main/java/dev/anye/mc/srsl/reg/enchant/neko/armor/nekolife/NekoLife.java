package dev.anye.mc.srsl.reg.enchant.neko.armor.nekolife;

import dev.anye.core.math._Math;
import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.neko.armor.NekoEA;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class NekoLife extends NekoEA {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.create(data -> {
		data.add("maxLevel",3);
	});
	private final int maxLevel;

	public record Data(int maxLevel) {
		public static final Data DEFAULT = new Data(3);
	}

	public NekoLife(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
		maxLevel = config.asInt("maxLevel");
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}

	@Override
	public boolean canEnchant(ItemStack pStack) {
		return isArmor(pStack);
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onHurt);
	}

	public void onHurt(LivingDamageEvent.Pre event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			Iterable<ItemStack> armorItemStacks = EnchantHelper.getArmorItemStacks(player);
			for (ItemStack slot : armorItemStacks) {
				int lvl = EnchantHelper.getEnchantLevel(slot, Enchants.na_life);
				if (lvl > 0 && lvl * 15 > _Math.RD.getIntRandomNumber(1, 100)) {
						double minDamage = player.getMaxHealth() * 0.2;
						if (event.getNewDamage() > minDamage && event.getNewDamage() < player.getMaxHealth() * 1.5) {
							event.setNewDamage((float) (minDamage));
						}
					}
			}
		}
	}
}
