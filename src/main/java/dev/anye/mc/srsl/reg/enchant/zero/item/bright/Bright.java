package dev.anye.mc.srsl.reg.enchant.zero.item.bright;

import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.zero.item.ZeroItemE;

public class Bright extends ZeroItemE {
	public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.Z_BRIGHT);

	public Bright() {
		super();
	}
}
