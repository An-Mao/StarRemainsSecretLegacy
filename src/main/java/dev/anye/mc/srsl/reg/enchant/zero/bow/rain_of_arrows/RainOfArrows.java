package dev.anye.mc.srsl.reg.enchant.zero.bow.rain_of_arrows;

import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.zero.bow.ZeroBowEnchant;

public class RainOfArrows extends ZeroBowEnchant {
	public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.RAIN_OF_ARROWS);

	public RainOfArrows() {
	}
}
