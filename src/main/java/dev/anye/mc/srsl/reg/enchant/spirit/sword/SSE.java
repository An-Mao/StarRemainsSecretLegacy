package dev.anye.mc.srsl.reg.enchant.spirit.sword;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.spirit.SpiritE;
import net.minecraft.world.item.ItemStack;

public abstract class SSE extends SpiritE {
	protected SSE(EnchantConfigData enchantConfigData,EnchantConfigData defaultData) {
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
