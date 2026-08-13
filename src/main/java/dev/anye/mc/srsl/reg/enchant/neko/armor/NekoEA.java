package dev.anye.mc.srsl.reg.enchant.neko.armor;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.neko.NekoEC;
import net.minecraft.world.item.ItemStack;

public abstract class NekoEA extends NekoEC {
	protected NekoEA(EnchantConfigData enchantConfigData,EnchantConfigData defaultData) {
		super(enchantConfigData,defaultData);
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		if (isArmor(stack)) {
			return true;
		}
		return super.canEnchant(stack);
	}
}
