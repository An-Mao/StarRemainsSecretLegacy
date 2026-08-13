package dev.anye.mc.srsl.reg.enchant.phenomenon.theworld;

import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.phenomenon.PhenomenonEnchant;
import net.minecraft.world.item.ItemStack;

public class TheWorldEnchant extends PhenomenonEnchant {
	public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.THE_WORLD);
	public static final int time = 80;

	public TheWorldEnchant() {
		super();
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		if (isWeapon(stack) || isChest(stack)) {
			return true;
		}
		return super.canEnchant(stack);
	}
}
