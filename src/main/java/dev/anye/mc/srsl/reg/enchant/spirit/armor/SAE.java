package dev.anye.mc.srsl.reg.enchant.spirit.armor;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.core.EnchantHelper;
import dev.anye.mc.srsl.reg.enchant.Enchant;
import dev.anye.mc.srsl.reg.enchant.spirit.SpiritE;
import net.minecraft.world.item.ItemStack;

public abstract class SAE extends SpiritE {
	protected SAE(EnchantConfigData enchantConfigData,EnchantConfigData defaultData) {
		super(enchantConfigData,defaultData);
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		if (isChest(stack) && !hasSpirit(stack)) {
			return true;
		}
		return super.canEnchant(stack);
	}

	public boolean hasSpirit(ItemStack stack) {
		for (Enchant enchant : EnchantHelper.getEnchants(stack).keySet()) {
			if (enchant instanceof SAE) return true;
		}
		return false;
	}
}
