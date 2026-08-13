package dev.anye.mc.srsl.reg.enchant.neko.item;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.neko.NekoEC;
import net.minecraft.world.item.ItemStack;

public abstract class NekoEI extends NekoEC {
	protected NekoEI(EnchantConfigData enchantConfigData,EnchantConfigData defaultData) {
		super(enchantConfigData,defaultData);
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		if (isWeapon(stack)) {
			return true;
		}
		return super.canEnchant(stack);
	}
}
