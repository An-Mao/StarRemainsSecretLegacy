package dev.anye.mc.srsl.reg.enchant.neko.armor.nekolife;

import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.neko.armor.NekoEA;
import net.minecraft.world.item.ItemStack;

public class NekoLife extends NekoEA {
	public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.NEKO_LIFE);
	private final int maxLevel = EnchantsConfig.INSTANCE.getMaxLevel(EnchantReg.NEKO_LIFE);

	public NekoLife() {
		super();
	}


	@Override
	public int getMaxLevel() {
		return maxLevel;
	}

	@Override
	public boolean canEnchant(ItemStack pStack) {
		return isArmor(pStack);
	}
}
