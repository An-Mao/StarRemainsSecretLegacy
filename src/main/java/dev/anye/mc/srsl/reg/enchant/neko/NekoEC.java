package dev.anye.mc.srsl.reg.enchant.neko;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.Enchant;
import net.minecraft.ChatFormatting;

public abstract class NekoEC extends Enchant {
	protected NekoEC(EnchantConfigData enchantConfigData,EnchantConfigData defaultData) {
		super(enchantConfigData,defaultData,ChatFormatting.LIGHT_PURPLE);
	}
}
