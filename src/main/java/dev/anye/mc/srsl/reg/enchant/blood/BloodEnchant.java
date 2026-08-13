package dev.anye.mc.srsl.reg.enchant.blood;

import dev.anye.mc.srsl.config.enchants.EnchantConfigData;
import dev.anye.mc.srsl.reg.enchant.Enchant;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;

public abstract class BloodEnchant extends Enchant {
	protected BloodEnchant(EnchantConfigData configData,EnchantConfigData defaultData) {
		super(configData,defaultData,ChatFormatting.DARK_RED);
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		if (isWeapon(stack)) {
			return true;
		}
		return super.canEnchant(stack);
	}
}
