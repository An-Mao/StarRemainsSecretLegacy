package dev.anye.mc.srsl.enchant.spirit.armor.stance;

import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.enchant.spirit.armor.SAE;

public class Stance extends SAE {
    public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.E_STANCE);
    public Stance() {
        super();
    }
}
