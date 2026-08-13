package dev.anye.mc.srsl.reg.enchant.neko.armor.nekomirror;

import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.neko.armor.NekoEA;
import net.minecraft.world.item.ItemStack;

public class NekoMirror extends NekoEA {
	public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.NEKO_MIRROR);
	private final int maxLevel = EnchantsConfig.INSTANCE.getMaxLevel(EnchantReg.NEKO_MIRROR);

	public NekoMirror() {
		super();
	}

	@Override
	public boolean canEnchant(ItemStack pStack) {
		return isArmor(pStack);
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}
}
