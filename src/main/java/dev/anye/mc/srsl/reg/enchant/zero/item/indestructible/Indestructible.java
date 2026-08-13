package dev.anye.mc.srsl.reg.enchant.zero.item.indestructible;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.Enchants;
import dev.anye.mc.srsl.reg.enchant.zero.item.ZeroItemE;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;

public class Indestructible extends ZeroItemE {
	private static final EnchantConfigData DEFAULT = EnchantConfigData.empty();
	public Indestructible(EnchantConfigData enchantConfigData) {
		super(enchantConfigData,DEFAULT);
	}

	@Override
	public boolean canEnchant(ItemStack pStack) {
		return true;
	}

	@Override
	protected void register(IEventBus modEventBus) {
		modEventBus.addListener(this::onItemExpire);
	}

	public void onItemExpire(ItemExpireEvent event) {
		ItemEntity itemEntity = event.getEntity();
		if (EnchantHelper.hasEnchant(itemEntity.getItem(), Enchants.zi_indestructible)) {
			//event.setExtraLife(max);
			//itemEntity.setExtendedLifetime();
			itemEntity.setUnlimitedLifetime();
		}

	}
}
