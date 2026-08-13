package dev.anye.mc.srsl.reg.enchant.zero.tool;

import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.zero.ZeroEnchant;
import net.minecraft.world.item.ItemStack;

public class ChainHarvesting extends ZeroEnchant {
	public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.ChainHarvesting);
	public static final int radius = (int) EnchantsConfig.INSTANCE.getValue(EnchantReg.ChainHarvesting, "radius");
	public static final int depths = (int) EnchantsConfig.INSTANCE.getValue(EnchantReg.ChainHarvesting, "depths");

	public ChainHarvesting() {
		super();
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		if (isDigger(stack)) {
			return true;
		}
		return super.canEnchant(stack);
	}
}
