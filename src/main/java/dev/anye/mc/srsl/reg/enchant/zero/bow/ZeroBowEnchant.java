package dev.anye.mc.srsl.reg.enchant.zero.bow;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.zero.ZeroEnchant;
import net.minecraft.world.item.ItemStack;

public abstract class ZeroBowEnchant extends ZeroEnchant {
	protected ZeroBowEnchant(EnchantConfigData enchantConfigData,EnchantConfigData defaultData) {
		super(enchantConfigData,defaultData);
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		if (isBow(stack)) {
			return true;
		}
		return super.canEnchant(stack);
	}
}
