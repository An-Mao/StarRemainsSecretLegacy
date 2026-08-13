package dev.anye.mc.srsl.reg.enchant.neko.item.nekoday;

import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.neko.item.NekoEI;

public class NekoDay extends NekoEI {
	public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.NEKO_DAY);
	private final int maxLevel = EnchantsConfig.INSTANCE.getMaxLevel(EnchantReg.NEKO_DAY);

	public NekoDay() {
		super();
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}

}
