package dev.anye.mc.srsl.enchant.zero.bow.rain_of_arrows;

import dev.anye.mc.srsl.config.enchants$config.EnchantsConfig;
import dev.anye.mc.srsl.core.EnchantReg;
import dev.anye.mc.srsl.enchant.zero.bow.ZeroBowEnchant;

public class RainOfArrows extends ZeroBowEnchant {
    public static final boolean ENABLE = EnchantsConfig.INSTANCE.isEnable(EnchantReg.RAIN_OF_ARROWS);
    public RainOfArrows(){}
}
