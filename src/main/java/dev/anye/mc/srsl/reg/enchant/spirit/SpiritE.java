package dev.anye.mc.srsl.reg.enchant.spirit;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.Enchant;
import net.minecraft.ChatFormatting;

public abstract class SpiritE extends Enchant {
	protected SpiritE(EnchantConfigData enchantConfigData,EnchantConfigData defaultData) {
		super(enchantConfigData,defaultData,ChatFormatting.BLUE);
	}
}
