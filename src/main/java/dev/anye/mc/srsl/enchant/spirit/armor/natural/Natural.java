package dev.anye.mc.srsl.enchant.spirit.armor.natural;

import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.enchant.spirit.armor.SAE;

public class Natural extends SAE {
    public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.E_NATURAL);

    public Natural() {
        super();
    }
}
