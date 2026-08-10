package dev.anye.mc.srsl.enchant.spirit.armor.adaptive;

import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.enchant.spirit.armor.SAE;

public class Adaptive extends SAE {
    public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.E_ADAPTIVE);
    public Adaptive() {
        super();
    }
}
