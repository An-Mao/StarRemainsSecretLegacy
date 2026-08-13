package dev.anye.mc.srsl.reg.enchant.zero.item.indestructible;

import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.zero.item.ZeroItemE;
import net.minecraft.world.item.ItemStack;

public class Indestructible extends ZeroItemE {
	public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.Z_INDESTRUCTIBLE);

	public Indestructible() {
		super();
	}

	@Override
	public boolean canEnchant(ItemStack pStack) {
		return true;
	}
}
