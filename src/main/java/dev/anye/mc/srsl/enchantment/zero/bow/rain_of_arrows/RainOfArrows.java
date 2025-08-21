package dev.anye.mc.srsl.enchantment.zero.bow.rain_of_arrows;

import dev.anye.mc.srsl.config.enchantments$config.EnchantmentsConfig;
import dev.anye.mc.srsl.enchantment.EnchantmentRegister;
import dev.anye.mc.srsl.enchantment.zero.bow.ZeroBowEnchantmentCore;

public class RainOfArrows extends ZeroBowEnchantmentCore {
    public static final boolean ENABLE = EnchantmentsConfig.INSTANCE.isEnable(EnchantmentRegister.Z_UNBREAKABLE);
    public RainOfArrows(){}
}
