package dev.anye.mc.srsl.reg.enchant.zero;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.Enchant;
import net.minecraft.ChatFormatting;

public abstract class ZeroEnchant extends Enchant {
	protected ZeroEnchant(EnchantConfigData enchantConfigData,EnchantConfigData defaultData) {
		super(enchantConfigData,defaultData,ChatFormatting.DARK_PURPLE);
	}
}
