package dev.anye.mc.srsl.reg.enchant.curse;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.Enchant;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;

public abstract class CurseEnchant extends Enchant {
	protected CurseEnchant(EnchantConfigData enchantConfigData,EnchantConfigData defaultData) {
		super(enchantConfigData,defaultData,ChatFormatting.RED);
	}

	@Override
	public boolean canEnchant(ItemStack pStack) {
		return true;
	}
}
