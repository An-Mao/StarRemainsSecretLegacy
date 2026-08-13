package dev.anye.mc.srsl.reg.enchant.neko.item.nekochop;

import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.neko.item.NekoEI;

public class NekoChop extends NekoEI {
	public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.NEKO_CHOP);
	private final int maxLevel = EnchantsConfig.INSTANCE.getMaxLevel(EnchantReg.NEKO_CHOP);

	public NekoChop() {
		super();
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}
}
