package dev.anye.mc.srsl.reg.enchant.neko.item.nekonight;

import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.neko.item.NekoEI;

public class NekoNight extends NekoEI {
	public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.NEKO_NIGHT);
	private final int maxLevel = EnchantsConfig.INSTANCE.getMaxLevel(EnchantReg.NEKO_NIGHT);

	public NekoNight() {
		super();
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}
}
