package dev.anye.mc.srsl.reg.enchant.spirit.armor.natural;

import dev.anye.mc.srsl.config.enchants.EnchantsConfig;
import dev.anye.mc.srsl.reg.enchant.EnchantReg;
import dev.anye.mc.srsl.reg.enchant.spirit.armor.SAE;

public class Natural extends SAE {
	public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.E_NATURAL);

	public Natural() {
		super();
	}
}
